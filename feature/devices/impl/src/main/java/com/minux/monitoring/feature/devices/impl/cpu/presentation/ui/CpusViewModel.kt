package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.devices.impl.common.data.repository.DeviceRepository
import com.minux.monitoring.feature.devices.impl.common.presentation.model.toDeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.impl.cpu.data.model.CpuIndicatorsDto
import com.minux.monitoring.feature.devices.impl.cpu.data.repository.CpuRepository
import com.minux.monitoring.feature.devices.impl.cpu.presentation.mapper.toCpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.mapper.toCpuItemModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuItemModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusEvent
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class CpusViewModel @Inject constructor(
    deviceRepository: DeviceRepository,
    private val cpuRepository: CpuRepository
) : BaseViewModel<CpusUiState, Unit, CpusEvent>(initialState = CpusUiState()) {

    override fun onEvent(uiEvent: CpusEvent) {
        when (uiEvent) {
            CpusEvent.FetchCpus -> fetchCpus()

            is CpusEvent.SearchQueryChanged -> searchQueryChanged(query = uiEvent.searchQuery)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val cpusIndicators = deviceRepository.observeDeviceIndicators()
        .mapLatest { cpuIndicators ->
            cpuIndicators.getOrNull()
                ?.cpusIndicators
                ?.associateBy { it.deviceId }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val cpusUiState: StateFlow<CpusUiState> = combine(
        uiStates(),
        cpusIndicators
    ) { state, cpusIndicators ->
        val cpus = state.cpus?.map { it.applyIndicators(indicators = cpusIndicators) }

        state.copy(
            cpus = cpus,
            filteredCpus = cpus
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = CpusUiState()
    )

    private fun fetchCpus() {
        cpuRepository.getAllCpus()
            .onStart { uiState = uiState.copy(cpusIsLoading = true) }
            .onEach { result ->
                val currentIndicators = cpusIndicators.value
                val gpus = result.getOrNull()?.map {
                    it.toCpuItemModel().applyIndicators(indicators = currentIndicators)
                }

                uiState = uiState.copy(
                    cpusIsLoading = false,
                    cpus = gpus,
                    filteredCpus = gpus
                )
            }
            .launchIn(viewModelScope)
    }

    private fun CpuItemModel.applyIndicators(
        indicators: Map<String, CpuIndicatorsDto>?
    ): CpuItemModel {
        return indicators?.get(this.id)?.let { indicator ->
            this.copy(
                indicators = indicator.toCpuIndicatorsModel(),
                coins = indicator.flightSheet.coins.map {
                    it.toDeviceCoinStatisticsModel(power = indicator.power)
                }
            )
        } ?: this
    }

    private fun searchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                if (uiState.cpus != uiState.filteredCpus)
                    uiState = uiState.copy(
                        searchQuery = query,
                        filteredCpus = uiState.cpus
                    )

                return@launch
            }

            uiState = uiState.copy(
                searchQuery = query,
                filteredCpus = uiState.cpus?.filter { it.summary.name.deviceName == query }
            )
        }
    }
}