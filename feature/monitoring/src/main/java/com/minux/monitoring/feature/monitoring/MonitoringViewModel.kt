package com.minux.monitoring.feature.monitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.domain.model.rig.RigCommandParam
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalCoinsUseCase
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalPowerUseCase
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalRigsCountUseCase
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalSharesUseCase
import com.minux.monitoring.core.domain.usecase.rig.GetRigsDynamicDataUseCase
import com.minux.monitoring.core.domain.usecase.rig.GetRigsInformationUseCase
import com.minux.monitoring.core.domain.usecase.rig.GetRigsStateUseCase
import com.minux.monitoring.core.domain.usecase.rig.PowerOffRigUseCase
import com.minux.monitoring.core.domain.usecase.rig.RebootRigUseCase
import com.minux.monitoring.core.domain.usecase.rig.StartMiningOnRigUseCase
import com.minux.monitoring.core.domain.usecase.rig.StopMiningOnRigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MonitoringViewModel @Inject constructor(
    getTotalPowerUseCase: GetTotalPowerUseCase,
    getTotalRigsCountUseCase: GetTotalRigsCountUseCase,
    getTotalSharesUseCase: GetTotalSharesUseCase,
    getTotalCoinsUseCase: GetTotalCoinsUseCase,
    getRigsDynamicDataUseCase: GetRigsDynamicDataUseCase,
    getRigsInformationUseCase: GetRigsInformationUseCase,
    getRigsStateUseCase: GetRigsStateUseCase,
    private val powerOffRigUseCase: PowerOffRigUseCase,
    private val rebootRigUseCase: RebootRigUseCase,
    private val startMiningOnRigUseCase: StartMiningOnRigUseCase,
    private val stopMiningOnRigUseCase: StopMiningOnRigUseCase
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
        SharingStarted.WhileSubscribed(5000L),
        initialValue = MonitoringState()
    )

    fun onEvent(monitoringEvent: MonitoringEvent) {
        when (monitoringEvent) {
            is MonitoringEvent.PowerOffRig -> {
                powerOffRig(param = monitoringEvent.rigCommandParam)
            }

            is MonitoringEvent.RebootRig -> {
                rebootRig(param = monitoringEvent.rigCommandParam)
            }

            is MonitoringEvent.StartMiningOnRig -> {
                startMiningOnRig(param = monitoringEvent.rigCommandParam)
            }

            is MonitoringEvent.StopMiningOnRig -> {
                stopMiningOnRig(param = monitoringEvent.rigCommandParam)
            }
        }
    }

    private fun powerOffRig(param: RigCommandParam) {
        monitoringStateMutable.update {
            it.copy(powerStatus = PowerStatus.PoweringOff)
        }

        powerOffRigUseCase(rigCommandParam = param).onEach { result ->
            result.onSuccess {
                monitoringStateMutable.update {
                    it.copy(powerStatus = PowerStatus.PoweredOff)
                }
            }.onFailure {
                with(monitoringStateMutable) {
                    update {
                        it.copy(powerStatus = PowerStatus.PoweringOffFailure)
                    }
                    update {
                        it.copy(powerStatus = PowerStatus.PoweredOn)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun rebootRig(param: RigCommandParam) {
        monitoringStateMutable.update {
            it.copy(rebootStatus = RebootStatus.Rebooting)
        }

        rebootRigUseCase(rigCommandParam = param).onEach { result ->
            result.onSuccess {
                monitoringStateMutable.update {
                    it.copy(rebootStatus = RebootStatus.Rebooted)
                }
            }.onFailure {
                monitoringStateMutable.update {
                    it.copy(rebootStatus = RebootStatus.RebootingFailure)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun startMiningOnRig(param: RigCommandParam) {
        monitoringStateMutable.update {
            it.copy(miningStatus = MiningStatus.Starting)
        }

        startMiningOnRigUseCase(rigCommandParam = param).onEach { result ->
            result.onSuccess {
                monitoringStateMutable.update {
                    it.copy(miningStatus = MiningStatus.Started)
                }
            }.onFailure {
                with(monitoringStateMutable) {
                    update {
                        it.copy(miningStatus = MiningStatus.StartingFailure)
                    }
                    update {
                        it.copy(miningStatus = MiningStatus.Stopped)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun stopMiningOnRig(param: RigCommandParam) {
        monitoringStateMutable.update {
            it.copy(miningStatus = MiningStatus.Stopping)
        }

        stopMiningOnRigUseCase(rigCommandParam = param).onEach { result ->
            result.onSuccess {
                monitoringStateMutable.update {
                    it.copy(miningStatus = MiningStatus.Stopped)
                }
            }.onFailure {
                with(monitoringStateMutable) {
                    update {
                        it.copy(miningStatus = MiningStatus.StoppingFailure)
                    }
                    update {
                        it.copy(miningStatus = MiningStatus.Started)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}