package com.minux.monitoring.feature.monitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalCoinsUseCase
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalPowerUseCase
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalRigsCountUseCase
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalSharesUseCase
import com.minux.monitoring.core.domain.usecase.rig.GetRigsDynamicDataUseCase
import com.minux.monitoring.core.domain.usecase.rig.GetRigsInformationUseCase
import com.minux.monitoring.core.domain.usecase.rig.GetRigsStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MonitoringViewModel @Inject constructor(
    getTotalPowerUseCase: GetTotalPowerUseCase,
    getTotalRigsCountUseCase: GetTotalRigsCountUseCase,
    getTotalSharesUseCase: GetTotalSharesUseCase,
    getTotalCoinsUseCase: GetTotalCoinsUseCase,
    getRigsDynamicDataUseCase: GetRigsDynamicDataUseCase,
    getRigsInformationUseCase: GetRigsInformationUseCase,
    getRigsStateUseCase: GetRigsStateUseCase
) : ViewModel() {

    private val metricsMonitoringState = combine(
        getTotalPowerUseCase(),
        getTotalRigsCountUseCase(),
        getTotalSharesUseCase(),
        getTotalCoinsUseCase()
    ) { power, rigsCount, shares, coins ->
        MonitoringState(
            totalPower = power.getOrNull(),
            totalRigs = rigsCount.getOrNull(),
            totalShares = shares.getOrNull(),
            coinsStatistics = coins.getOrNull()
        )
    }

    private val rigsMonitoringState = combine(
        getRigsDynamicDataUseCase(),
        getRigsInformationUseCase(),
        getRigsStateUseCase()
    ) { rigsDynamicData, rigsInformation, rigsState ->
        MonitoringState(
            rigs = rigsDynamicData.getOrNull(),
            rigNames = rigsInformation.getOrNull()?.map {
                it.name
            },
            rigActiveStates = rigsState.getOrNull()?.map {
                it.isActive
            }
        )
    }

    private val monitoringStateMutable = MutableStateFlow(value = MonitoringState())
    val monitoringState: StateFlow<MonitoringState> = combine(
        monitoringStateMutable,
        metricsMonitoringState,
        rigsMonitoringState
    ) { state, metricsState, rigsState ->
        state.copy(
            totalPower = metricsState.totalPower,
            totalRigs = metricsState.totalRigs,
            totalShares = metricsState.totalShares,
            coinsStatistics = metricsState.coinsStatistics,
            rigs = rigsState.rigs,
            rigNames = rigsState.rigNames,
            rigActiveStates = rigsState.rigActiveStates
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        initialValue = MonitoringState()
    )
}