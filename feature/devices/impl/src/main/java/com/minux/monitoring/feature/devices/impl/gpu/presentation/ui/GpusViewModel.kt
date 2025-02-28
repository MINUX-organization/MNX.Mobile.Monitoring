package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.devices.impl.gpu.data.repository.GpuRepository
import com.minux.monitoring.feature.devices.impl.gpu.presentation.mapper.toGpuItemModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusAction
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusEvent
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class GpusViewModel @Inject constructor(
    gpuRepository: GpuRepository
) : BaseViewModel<GpusUiState, GpusAction, GpusEvent>(initialState = GpusUiState()) {

    private val allGpus = gpuRepository.getAllGpus()
        .mapLatest { gpus ->
            gpus.getOrDefault(emptyList()).map { it.toGpuItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val gpusUiState: StateFlow<GpusUiState> = combine(
        uiStates(),
        allGpus
    ) { state, gpus ->
        state.copy(
            gpus = gpus
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = GpusUiState()
    )

    override fun onEvent(uiEvent: GpusEvent) {
        when (uiEvent) {
            GpusEvent.Filters -> uiAction = GpusAction.OpenFiltersBottomSheet
            GpusEvent.Settings -> uiAction = GpusAction.OpenGpuOverclockingScreen
        }
    }
}