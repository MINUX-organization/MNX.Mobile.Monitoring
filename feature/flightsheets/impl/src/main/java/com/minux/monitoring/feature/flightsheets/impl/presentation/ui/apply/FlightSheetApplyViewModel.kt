package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetAppliedDevicesGetDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetDevicesApplyDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetSupportedDevicesGetDto
import com.minux.monitoring.feature.flightsheets.impl.data.repository.FlightSheetRepository
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toDeviceGroupItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toDeviceIds
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model.FlightSheetApplyAction
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model.FlightSheetApplyEvent
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model.FlightSheetApplyUiState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class FlightSheetApplyViewModel @Inject constructor(
    private val flightSheetRepository: FlightSheetRepository
) : BaseViewModel<FlightSheetApplyUiState, FlightSheetApplyAction, FlightSheetApplyEvent>(
    initialState = FlightSheetApplyUiState()
) {
    override fun onEvent(uiEvent: FlightSheetApplyEvent) {
        when (uiEvent) {
            is FlightSheetApplyEvent.FetchSupportedDevices -> {
                fetchFlightSheetSupportedDevices(flightSheetId = uiEvent.flightSheetId)
            }

            is FlightSheetApplyEvent.CheckAllDevicesOnRigChanged -> {
                checkAllDevicesOnRigChanged(
                    rigIndex = uiEvent.rigIndex,
                    checked = uiEvent.checked
                )
            }

            is FlightSheetApplyEvent.CheckDeviceChanged -> {
                checkDeviceChanged(id = uiEvent.id, checked = uiEvent.checked)
            }

            FlightSheetApplyEvent.Back -> uiAction = FlightSheetApplyAction.OpenPreviousScreen

            is FlightSheetApplyEvent.Confirm -> applyDevices(flightSheetId = uiEvent.flightSheetId)
        }
    }

    private fun fetchFlightSheetSupportedDevices(flightSheetId: String) {
        combine(
            flightSheetRepository.getFlightSheetSupportedDevices(
                flightSheetSupportedDevicesGet = FlightSheetSupportedDevicesGetDto(
                    flightSheetId = flightSheetId
                )
            ),
            flightSheetRepository.getFlightSheetAppliedDevices(
                flightSheetAppliedDevicesGet = FlightSheetAppliedDevicesGetDto(
                    flightSheetId = flightSheetId
                )
            )
        ) { supportedDevices, appliedDevices ->
            val appliedIds = appliedDevices.getOrNull()?.flatMap { it.toDeviceIds() }
                ?.toSet() ?: emptySet()

            uiState = uiState.copy(
                flightSheetRigDevicesSupported = supportedDevices.getOrNull()?.map {
                    it.toDeviceGroupItemModel(devicesApplied = appliedIds)
                },
                flightSheetRigDevicesApplied = appliedIds
            )
        }.launchIn(viewModelScope)
    }

    private fun checkAllDevicesOnRigChanged(rigIndex: Int, checked: Boolean) {
        if (uiState.flightSheetRigDevicesSupported == null) return

        val newDevicesApplied = uiState.flightSheetRigDevicesApplied.toMutableSet()

        val rig = uiState.flightSheetRigDevicesSupported!![rigIndex]
        val newRig = rig.copy(
            elements = rig.elements.map { deviceGroup ->
                deviceGroup.copy(
                    elements = deviceGroup.elements.map { device ->
                        if (checked)
                            newDevicesApplied.add(device.id)
                        else
                            newDevicesApplied.remove(device.id)

                        device.copy(isChecked = checked)
                    }
                )
            }
        )

        val newSupportedDevices = uiState.flightSheetRigDevicesSupported!!
            .toMutableList()
            .apply { this[rigIndex] = newRig }

        uiState = uiState.copy(
            flightSheetRigDevicesSupported = newSupportedDevices,
            flightSheetRigDevicesApplied = newDevicesApplied
        )
    }

    private fun checkDeviceChanged(id: String, checked: Boolean) {
        if (uiState.flightSheetRigDevicesSupported == null) return

        val newDevicesApplied = uiState.flightSheetRigDevicesApplied.toMutableSet()

        if (checked)
            newDevicesApplied.add(id)
        else
            newDevicesApplied.remove(id)

        uiState = uiState.copy(
            flightSheetRigDevicesSupported = uiState.flightSheetRigDevicesSupported!!.map { rig ->
                rig.copy(
                    elements = rig.elements.map { deviceGroup ->
                        deviceGroup.copy(
                            elements = deviceGroup.elements.map { device ->
                                if (device.id == id)
                                    device.copy(isChecked = checked)
                                else
                                    device
                            }
                        )
                    }
                )
            },
            flightSheetRigDevicesApplied = newDevicesApplied
        )
    }

    private fun applyDevices(flightSheetId: String) {
        flightSheetRepository.applyDevicesForFlightSheet(
            flightSheetDevicesApply = FlightSheetDevicesApplyDto(
                flightSheetId = flightSheetId,
                devices = uiState.flightSheetRigDevicesApplied.toList()
            )
        ).onEach { result ->
            result.onSuccess {
                uiAction = FlightSheetApplyAction.OpenPreviousScreen
            }.onFailure {
                uiAction = FlightSheetApplyAction.ShowApplyDevicesFailedSnackBar
            }
        }.launchIn(viewModelScope)
    }
}