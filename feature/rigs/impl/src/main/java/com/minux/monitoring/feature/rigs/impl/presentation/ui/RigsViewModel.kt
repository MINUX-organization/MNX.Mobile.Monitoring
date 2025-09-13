package com.minux.monitoring.feature.rigs.impl.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.rigs.impl.data.RigRepository
import com.minux.monitoring.feature.rigs.impl.data.model.RigLifecycleChangeDto
import com.minux.monitoring.feature.rigs.impl.presentation.mapper.toRigItemModel
import com.minux.monitoring.feature.rigs.impl.presentation.ui.model.RigsAction
import com.minux.monitoring.feature.rigs.impl.presentation.ui.model.RigsEvent
import com.minux.monitoring.feature.rigs.impl.presentation.ui.model.RigsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RigsViewModel @Inject constructor(
    private val rigRepository: RigRepository
) : BaseViewModel<RigsUiState, RigsAction, RigsEvent>(initialState = RigsUiState()) {
    override fun onEvent(uiEvent: RigsEvent) {
        when (uiEvent) {
            RigsEvent.FetchRigs -> fetchRigs()

            is RigsEvent.SearchQueryChanged -> searchQueryChanged(query = uiEvent.searchQuery)

            is RigsEvent.PowerOff -> powerOff(rigId = uiEvent.id)

            is RigsEvent.Reboot -> reboot(rigId = uiEvent.id)

            is RigsEvent.StartMining -> startMining(rigId = uiEvent.id)

            is RigsEvent.StopMining -> stopMining(rigId = uiEvent.id)
        }
    }

    private fun fetchRigs() {
        rigRepository.getAllRigs()
            .onStart { uiState = uiState.copy(rigsIsLoading = true) }
            .onEach { result ->
                val rigs = result.getOrNull()?.map { it.toRigItemModel() }

                uiState = uiState.copy(
                    rigsIsLoading = false,
                    rigs = rigs,
                    filteredRigs = rigs
                )
            }
            .launchIn(viewModelScope)
    }

    private fun searchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                if (uiState.rigs != uiState.filteredRigs)
                    uiState = uiState.copy(
                        searchQuery = query,
                        filteredRigs = uiState.rigs
                    )

                return@launch
            }

            val filteredFlightSheets = uiState.rigs?.filter {
                it.name.contains(query, ignoreCase = true)
            }

            uiState = uiState.copy(
                searchQuery = query,
                filteredRigs = filteredFlightSheets
            )
        }
    }

    private fun powerOff(rigId: String) {
        rigRepository.powerOffRig(rigLifecycleChange = RigLifecycleChangeDto(id = rigId))
            .onEach { result ->
                result.onSuccess {
                    fetchRigs()
                }.onFailure {
                    uiAction = RigsAction.ShowPowerOffFailedSnackBar()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun reboot(rigId: String) {
        rigRepository.rebootRig(rigLifecycleChange = RigLifecycleChangeDto(id = rigId))
            .onEach { result ->
                result.onSuccess {
                    fetchRigs()
                }.onFailure {
                    uiAction = RigsAction.ShowRebootFailedSnackBar()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun startMining(rigId: String) {
        rigRepository.startMiningOnRig(rigLifecycleChange = RigLifecycleChangeDto(id = rigId))
            .onEach { result ->
                result.onSuccess {
                    fetchRigs()
                }.onFailure {
                    uiAction = RigsAction.ShowStartMiningFailedSnackBar()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun stopMining(rigId: String) {
        rigRepository.stopMiningOnRig(rigLifecycleChange = RigLifecycleChangeDto(id = rigId))
            .onEach { result ->
                result.onSuccess {
                    fetchRigs()
                }.onFailure {
                    uiAction = RigsAction.ShowStopMiningFailedSnackBar()
                }
            }
            .launchIn(viewModelScope)
    }
}