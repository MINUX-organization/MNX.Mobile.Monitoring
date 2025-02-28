package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.devices.impl.cpu.data.repository.CpuRepository
import com.minux.monitoring.feature.devices.impl.cpu.presentation.mapper.toCpuItemModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusAction
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusEvent
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class CpusViewModel @Inject constructor(
    cpuRepository: CpuRepository
) : BaseViewModel<CpusUiState, CpusAction, CpusEvent>(initialState = CpusUiState()) {

    private val allCpus = cpuRepository.getAllCpus()
        .mapLatest { cpus ->
            cpus.getOrDefault(emptyList()).map { it.toCpuItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val cpusUiState: StateFlow<CpusUiState> = combine(
        uiStates(),
        allCpus
    ) { state, cpus ->
        state.copy(
            cpus = cpus
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = CpusUiState()
    )

    override fun onEvent(uiEvent: CpusEvent) {
        when (uiEvent) {
            CpusEvent.Filters -> uiAction = CpusAction.OpenFiltersBottomSheet
        }
    }
}