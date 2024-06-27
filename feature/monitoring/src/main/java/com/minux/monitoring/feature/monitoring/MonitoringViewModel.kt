package com.minux.monitoring.feature.monitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.data.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.data.model.metrics.Shares
import com.minux.monitoring.core.data.model.metrics.ValueUnit
import com.minux.monitoring.core.data.model.rig.RigCommandParam
import com.minux.monitoring.core.data.model.rig.RigDynamicData
import com.minux.monitoring.core.data.repository.MetricsRepository
import com.minux.monitoring.core.data.repository.RigRepository
import com.minux.monitoring.core.data.util.combine
import com.minux.monitoring.core.data.util.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MonitoringViewModel @Inject constructor(
    private val metricsRepository: MetricsRepository,
    private val rigRepository: RigRepository
) : ViewModel() {

    private val _monitoringState = MutableStateFlow(MonitoringViewModelState())
    val monitoringState: StateFlow<MonitoringUiState> = _monitoringState
        .map(MonitoringViewModelState::toUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MonitoringUiState.Loading
        )

    init {
        fetchMetricsAndRigs()
    }

    fun onEvent(monitoringEvent: MonitoringEvent) {
        when (monitoringEvent) {
            MonitoringEvent.Refresh -> fetchMetricsAndRigs()

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

    private fun fetchMetricsAndRigs() {
        combine(
            metricsRepository.getTotalPower(),
            metricsRepository.getTotalRigsCount(),
            metricsRepository.getTotalShares(),
            metricsRepository.getTotalCoins(),
            rigRepository.getRigsDynamicData(),
            rigRepository.getRigsInformation(),
            rigRepository.getRigsState()
        ) { power, rigsCount, shares, coins, rigsDynamicData, rigsInformation, rigsState ->
            MonitoringViewModelState(
                totalPower = power.getOrThrow(),
                totalRigs = rigsCount.getOrThrow(),
                totalShares = shares.getOrThrow(),
                coinsStatistics = coins.getOrThrow(),
                rigs = rigsDynamicData.getOrThrow(),
                rigNames = rigsInformation.getOrThrow().map { it.name },
                rigActiveStates = rigsState.getOrThrow().map { it.isActive }
            )
        }.onStart {
            _monitoringState.update { it.copy(isLoading = true) }
        }.onEach { result ->
            _monitoringState.update { result }
        }.catch {
            MonitoringViewModelState(
                isLoading = false,
                hasError = Pair(true, it.message)
            )
        }.launchIn(viewModelScope)
    }

    private fun powerOffRig(param: RigCommandParam) {
        rigRepository
            .powerOffRig(param = param)
            .onStart {
                _monitoringState.update {
                    it.copy(
                        rigPowerStates = it.rigPowerStates.copy(
                            index = param.rigIndex,
                            element = RigPowerState.PoweringOff
                        )
                    )
                }
            }
            .onEach { result ->
                result.onSuccess {
                    _monitoringState.update {
                        it.copy(
                            rigPowerStates = it.rigPowerStates.copy(
                                index = param.rigIndex,
                                element = RigPowerState.PoweredOff
                            ),
                            rigMiningStatuses = it.rigMiningStatuses.copy(
                                index = param.rigIndex,
                                element = RigMiningStatus.Stopped
                            )
                        )
                    }
                }.onFailure { exception ->
                    with(_monitoringState) {
                        update {
                            it.copy(
                                rigPowerStates = it.rigPowerStates.copy(
                                    index = param.rigIndex,
                                    element = RigPowerState.Error(message = exception.message)
                                )
                            )
                        }

                        delay(300)

                        update {
                            it.copy(
                                rigPowerStates = it.rigPowerStates.copy(
                                    index = param.rigIndex,
                                    element = RigPowerState.PoweredOn
                                )
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun rebootRig(param: RigCommandParam) {
        _monitoringState.update {
            it.copy(
                rigPowerStates = it.rigPowerStates.copy(
                    index = param.rigIndex,
                    element = RigPowerState.Rebooting
                )
            )
        }

        rigRepository
            .rebootRig(param = param)
            .onEach { result ->
                result.onSuccess {
                    _monitoringState.update {
                        it.copy(
                            rigPowerStates = it.rigPowerStates.copy(
                                index = param.rigIndex,
                                element = RigPowerState.PoweredOn
                            )
                        )
                    }
                }.onFailure { exception ->
                    with(_monitoringState) {
                        update {
                            it.copy(
                                rigPowerStates = it.rigPowerStates.copy(
                                    index = param.rigIndex,
                                    element = RigPowerState.Error(message = exception.message)
                                )
                            )
                        }

                        delay(300)

                        update {
                            it.copy(
                                rigPowerStates = it.rigPowerStates.copy(
                                    index = param.rigIndex,
                                    element = RigPowerState.PoweredOn
                                )
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun startMiningOnRig(param: RigCommandParam) {
        _monitoringState.update {
            it.copy(
                rigMiningStatuses = it.rigMiningStatuses.copy(
                    index = param.rigIndex,
                    element = RigMiningStatus.Starting
                )
            )
        }

        rigRepository
            .startMiningOnRig(param = param)
            .onEach { result ->
                result.onSuccess {
                    _monitoringState.update {
                        it.copy(
                            rigMiningStatuses = it.rigMiningStatuses.copy(
                                index = param.rigIndex,
                                element = RigMiningStatus.Started
                            )
                        )
                    }
                }.onFailure { exception ->
                    with(_monitoringState) {
                        update {
                            it.copy(
                                rigMiningStatuses = it.rigMiningStatuses.copy(
                                    index = param.rigIndex,
                                    element = RigMiningStatus.Error(message = exception.message)
                                )
                            )
                        }

                        delay(300)

                        update {
                            it.copy(
                                rigMiningStatuses = it.rigMiningStatuses.copy(
                                    index = param.rigIndex,
                                    element = RigMiningStatus.Stopped
                                )
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun stopMiningOnRig(param: RigCommandParam) {
        _monitoringState.update {
            it.copy(
                rigMiningStatuses = it.rigMiningStatuses.copy(
                    index = param.rigIndex,
                    element = RigMiningStatus.Stopping
                )
            )
        }

        rigRepository
            .stopMiningOnRig(param = param)
            .onEach { result ->
                result.onSuccess {
                    _monitoringState.update {
                        it.copy(
                            rigMiningStatuses = it.rigMiningStatuses.copy(
                                index = param.rigIndex,
                                element = RigMiningStatus.Stopped
                            )
                        )
                    }
                }.onFailure { exception ->
                    with(_monitoringState) {
                        update {
                            it.copy(
                                rigMiningStatuses = it.rigMiningStatuses.copy(
                                    index = param.rigIndex,
                                    element = RigMiningStatus.Error(message = exception.message)
                                )
                            )
                        }

                        delay(300)

                        update {
                            it.copy(
                                rigMiningStatuses = it.rigMiningStatuses.copy(
                                    index = param.rigIndex,
                                    element = RigMiningStatus.Started
                                )
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}

private data class MonitoringViewModelState(
    val isLoading: Boolean = false,
    val hasError: Pair<Boolean, String?> = Pair(false, null),
    val totalPower: ValueUnit = ValueUnit(0, ""),
    val totalRigs: Int = 0,
    val totalShares: Shares = Shares(0, 0),
    val coinsStatistics: List<CoinStatisticsDetail> = emptyList(),
    val rigs: List<RigDynamicData?> = emptyList(),
    val rigNames: List<String?> = emptyList(),
    val rigActiveStates: List<Boolean?> = emptyList(),
    val rigPowerStates: List<RigPowerState?> = emptyList(),
    val rigMiningStatuses: List<RigMiningStatus?> = emptyList()
) {
    fun toUiState(): MonitoringUiState {
        return when {
            isLoading -> return MonitoringUiState.Loading
            hasError.first -> return MonitoringUiState.Error(message = hasError.second)
            totalRigs == 0 -> return MonitoringUiState.NoRigs
            else -> MonitoringUiState.HasRigs(
                totalPower = totalPower,
                totalRigs = totalRigs,
                totalShares = totalShares,
                coinsStatistics = coinsStatistics,
                rigs = rigs,
                rigNames = rigNames,
                rigActiveStates = rigActiveStates,
                rigPowerStates = rigPowerStates,
                rigMiningStatuses = rigMiningStatuses
            )
        }
    }
}