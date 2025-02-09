package com.minux.monitoring.feature.monitoring.impl.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.monitoring.impl.data.RigRepository
import com.minux.monitoring.feature.monitoring.impl.presentation.mapper.mapToRigIdentificationDto
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigControlModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigIdentificationModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigMiningState
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigPowerState
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringAction
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringEvent
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringUiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class MonitoringViewModel @Inject constructor(
    private val rigRepository: RigRepository
) : BaseViewModel<MonitoringUiState, MonitoringAction, MonitoringEvent>(
    initialState = MonitoringUiState()
) {
    override fun onEvent(uiEvent: MonitoringEvent) {
        when (uiEvent) {
            MonitoringEvent.Refresh -> fetchMetricsAndRigs()

            is MonitoringEvent.PowerOffRig -> {
                powerOffRig(rigIdentification = uiEvent.rigIdentificationModel)
            }

            is MonitoringEvent.RebootRig -> {
                rebootRig(rigIdentification = uiEvent.rigIdentificationModel)
            }

            is MonitoringEvent.ControlMiningOnRig -> {
                controlMiningOnRig(rigIdentification = uiEvent.rigIdentificationModel)
            }

            is MonitoringEvent.RigFanSettings -> {
                uiState = uiState.copy(selectedRigFanConfiguration = uiEvent.fans)

                uiAction = MonitoringAction.OpenRigFanSettingsBottomSheet
            }
        }
    }

    private fun fetchMetricsAndRigs() {}

    private fun powerOffRig(rigIdentification: RigIdentificationModel) {
        val currentRig = uiState.rigs.find { it.identification == rigIdentification }!!

        rigRepository
            .powerOffRig(rigIdentification = rigIdentification.mapToRigIdentificationDto())
            .onStart {
                uiState = uiState.copy(
                    rigs = uiState.rigs.changeControlOnRig(
                        rig = currentRig,
                        control = currentRig.control.copy(powerState = RigPowerState.PoweringOff)
                    )
                )
            }
            .onEach { result ->
                result.onSuccess {
                    uiState = uiState.copy(
                        rigs = uiState.rigs.changeControlOnRig(
                            rig = currentRig,
                            control = currentRig.control.copy(
                                powerState = RigPowerState.PoweredOff,
                                miningState = RigMiningState.Stopped
                            )
                        )
                    )
                }.onFailure {
                    uiState = uiState.copy(
                        rigs = uiState.rigs.changeControlOnRig(
                            rig = currentRig,
                            control = currentRig.control.copy(
                                powerState = RigPowerState.PoweredOn
                            )
                        )
                    )

                    uiAction = MonitoringAction.ShowPowerOffRigFailedSnackBar()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun rebootRig(rigIdentification: RigIdentificationModel) {
        val currentRig = uiState.rigs.find { it.identification == rigIdentification }!!

        rigRepository
            .rebootRig(rigIdentification = rigIdentification.mapToRigIdentificationDto())
            .onStart {
                uiState = uiState.copy(
                    rigs = uiState.rigs.changeControlOnRig(
                        rig = currentRig,
                        control = currentRig.control.copy(powerState = RigPowerState.Rebooting)
                    )
                )
            }
            .onEach { result ->
                result.onSuccess {
                    uiState = uiState.copy(
                        rigs = uiState.rigs.changeControlOnRig(
                            rig = currentRig,
                            control = currentRig.control.copy(powerState = RigPowerState.PoweredOn)
                        )
                    )
                }.onFailure {
                    uiState = uiState.copy(
                        rigs = uiState.rigs.changeControlOnRig(
                            rig = currentRig,
                            control = currentRig.control.copy(powerState = RigPowerState.PoweredOn)
                        )
                    )

                    uiAction = MonitoringAction.ShowRebootRigFailedSnackBar()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun controlMiningOnRig(rigIdentification: RigIdentificationModel) {
        val rig = uiState.rigs.find { it.identification == rigIdentification }!!

        when (rig.control.miningState) {
            RigMiningState.Started -> stopMining(currentRig = rig)

            RigMiningState.Stopped -> startMining(currentRig = rig)

            else -> {}
        }
    }

    private fun startMining(currentRig: RigItemModel) {
        rigRepository
            .startMiningOnRig(rigIdentification = currentRig.identification.mapToRigIdentificationDto())
            .onStart {
                uiState = uiState.copy(
                    rigs = uiState.rigs.changeControlOnRig(
                        rig = currentRig,
                        control = currentRig.control.copy(miningState = RigMiningState.Starting)
                    )
                )
            }
            .onEach { result ->
                result.onSuccess {
                    uiState = uiState.copy(
                        rigs = uiState.rigs.changeControlOnRig(
                            rig = currentRig,
                            control = currentRig.control.copy(miningState = RigMiningState.Started)
                        )
                    )
                }.onFailure {
                    uiState = uiState.copy(
                        rigs = uiState.rigs.changeControlOnRig(
                            rig = currentRig,
                            control = currentRig.control.copy(miningState = RigMiningState.Stopped)
                        )
                    )

                    uiAction = MonitoringAction.ShowStartMiningOnRigFailedSnackBar()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun stopMining(currentRig: RigItemModel) {

        rigRepository
            .stopMiningOnRig(rigIdentification = currentRig.identification.mapToRigIdentificationDto())
            .onStart {
                uiState = uiState.copy(
                    rigs = uiState.rigs.changeControlOnRig(
                        rig = currentRig,
                        control = currentRig.control.copy(miningState = RigMiningState.Stopping)
                    )
                )
            }
            .onEach { result ->
                result.onSuccess {
                    uiState = uiState.copy(
                        rigs = uiState.rigs.changeControlOnRig(
                            rig = currentRig,
                            control = currentRig.control.copy(miningState = RigMiningState.Stopped)
                        )
                    )
                }.onFailure {
                    uiState = uiState.copy(
                        rigs = uiState.rigs.changeControlOnRig(
                            rig = currentRig,
                            control = currentRig.control.copy(miningState = RigMiningState.Started)
                        )
                    )

                    uiAction = MonitoringAction.ShowStopMiningOnRigFailedSnackBar()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun List<RigItemModel>.changeControlOnRig(
        rig: RigItemModel,
        control: RigControlModel
    ): List<RigItemModel> {
        val rigIndex = indexOf(rig)

        return apply {
            toMutableList()[rigIndex] = rig.copy(control = control)
        }
    }
}