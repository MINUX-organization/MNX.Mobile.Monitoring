package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.devices.impl.common.data.repository.DeviceRepository
import com.minux.monitoring.feature.devices.impl.common.presentation.model.toDeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.impl.gpu.data.model.GpuIndicatorsDto
import com.minux.monitoring.feature.devices.impl.gpu.data.repository.GpuRepository
import com.minux.monitoring.feature.devices.impl.gpu.presentation.mapper.toGpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.mapper.toGpuItemModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuItemModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusAction
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusEvent
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusUiState
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

internal class GpusViewModel @Inject constructor(
    deviceRepository: DeviceRepository,
    private val gpuRepository: GpuRepository
) : BaseViewModel<GpusUiState, GpusAction, GpusEvent>(initialState = GpusUiState()) {

    override fun onEvent(uiEvent: GpusEvent) {
        when (uiEvent) {
            GpusEvent.FetchGpus -> fetchGpus()

            is GpusEvent.SearchQueryChanged -> searchQueryChanged(query = uiEvent.searchQuery)

            is GpusEvent.Settings -> {
                uiAction = GpusAction.OpenGpuSettingsScreen(id = uiEvent.gpuId, name = uiEvent.gpuName)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val gpusIndicators = deviceRepository.observeDeviceIndicators()
        .mapLatest { gpuIndicators ->
            gpuIndicators.getOrNull()
                ?.gpusIndicators
                ?.associateBy { it.deviceId }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val gpusUiState: StateFlow<GpusUiState> = combine(
        uiStates(),
        gpusIndicators
    ) { state, gpusIndicators ->
        val gpus = state.gpus?.map { it.applyIndicators(indicators = gpusIndicators) }

        state.copy(
            gpus = gpus,
            filteredGpus = gpus
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = GpusUiState()
    )

    private fun fetchGpus() {
        gpuRepository.getAllGpus()
            .onStart { uiState = uiState.copy(gpusIsLoading = true) }
            .onEach { result ->
                val currentIndicators = gpusIndicators.value
                val gpus = result.getOrNull()?.map {
                    it.toGpuItemModel().applyIndicators(indicators = currentIndicators)
                }

                uiState = uiState.copy(
                    gpusIsLoading = false,
                    gpus = gpus,
                    filteredGpus = gpus
                )
            }
            .launchIn(viewModelScope)
    }

    private fun GpuItemModel.applyIndicators(
        indicators: Map<String, GpuIndicatorsDto>?
    ): GpuItemModel {
        return indicators?.get(this.id)?.let { indicator ->
            this.copy(
                indicators = indicator.toGpuIndicatorsModel(),
                coins = indicator.flightSheet.coins.map {
                    it.toDeviceCoinStatisticsModel(power = indicator.power)
                }
            )
        } ?: this
    }

    private fun searchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                if (uiState.gpus != uiState.filteredGpus)
                    uiState = uiState.copy(
                        searchQuery = query,
                        filteredGpus = uiState.gpus
                    )

                return@launch
            }

            uiState = uiState.copy(
                searchQuery = query,
                filteredGpus = uiState.gpus?.filter {
                    it.summary.name.deviceName?.lowercase() == query.lowercase()
                }
            )
        }
    }
}