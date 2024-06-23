package com.minux.monitoring.feature.monitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.data.model.rig.RigCommandParam
import com.minux.monitoring.core.data.repository.MetricsRepository
import com.minux.monitoring.core.data.repository.RigRepository
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
    metricsRepository: MetricsRepository,
    private val rigRepository: RigRepository
) : ViewModel() {

    private val metricsMonitoringState = combine(
        metricsRepository.getTotalPower(),
        metricsRepository.getTotalRigsCount(),
        metricsRepository.getTotalShares(),
        metricsRepository.getTotalCoins()
    ) { power, rigsCount, shares, coins ->
        MonitoringState(
            totalPower = power.getOrNull(),
            totalRigs = rigsCount.getOrNull(),
            totalShares = shares.getOrNull(),
            coinsStatistics = coins.getOrNull()
        )
    }

    private val rigsMonitoringState = combine(
        rigRepository.getRigsDynamicData(),
        rigRepository.getRigsInformation(),
        rigRepository.getRigsState()
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

        rigRepository.powerOffRig(param = param).onEach { result ->
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

        rigRepository.rebootRig(param = param).onEach { result ->
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

        rigRepository.startMiningOnRig(param = param).onEach { result ->
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

        rigRepository.stopMiningOnRig(param = param).onEach { result ->
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