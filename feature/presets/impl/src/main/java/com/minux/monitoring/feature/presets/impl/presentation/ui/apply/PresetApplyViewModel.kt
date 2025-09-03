package com.minux.monitoring.feature.presets.impl.presentation.ui.apply

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.presets.impl.data.model.PresetAppliedDevicesGetDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetDevicesApplyDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetSupportedDevicesGetDto
import com.minux.monitoring.feature.presets.impl.data.repository.PresetRepository
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toDeviceGroupItemModel
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toDeviceIds
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model.PresetApplyAction
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model.PresetApplyEvent
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model.PresetApplyUiState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class PresetApplyViewModel @Inject constructor(
    private val presetRepository: PresetRepository
) : BaseViewModel<PresetApplyUiState, PresetApplyAction, PresetApplyEvent>(
    initialState = PresetApplyUiState()
) {
    override fun onEvent(uiEvent: PresetApplyEvent) {
        when (uiEvent) {
            is PresetApplyEvent.FetchSupportedDevices -> {
                fetchPresetSupportedDevices(presetId = uiEvent.presetId)
            }

            is PresetApplyEvent.CheckAllDevicesOnRigChanged -> {
                checkAllDevicesOnRigChanged(rigIndex = uiEvent.rigIndex, checked = uiEvent.checked)
            }

            is PresetApplyEvent.CheckDeviceChanged -> {
                checkDeviceChanged(id = uiEvent.id, checked = uiEvent.checked)
            }

            PresetApplyEvent.Back -> uiAction = PresetApplyAction.OpenPreviousScreen

            is PresetApplyEvent.Confirm -> applyDevices(presetId = uiEvent.presetId)
        }
    }

    private fun fetchPresetSupportedDevices(presetId: String) {
        combine(
            presetRepository.getPresetSupportedDevices(
                presetSupportedDevicesGet = PresetSupportedDevicesGetDto(presetId = presetId)
            ),
            presetRepository.getPresetAppliedDevices(
                presetAppliedDevicesGet = PresetAppliedDevicesGetDto(presetId = presetId)
            )
        ) { supportedDevices, appliedDevices ->
            val appliedIds = appliedDevices.getOrNull()?.flatMap { it.toDeviceIds() }
                ?.toSet() ?: emptySet()

            uiState = uiState.copy(
                presetRigDevicesSupported = supportedDevices.getOrNull()?.map {
                    it.toDeviceGroupItemModel(devicesApplied = appliedIds)
                },
                presetRigDevicesApplied = appliedIds
            )
        }.launchIn(viewModelScope)
    }

    private fun checkAllDevicesOnRigChanged(rigIndex: Int, checked: Boolean) {
        if (uiState.presetRigDevicesSupported == null) return

        val newDevicesApplied = uiState.presetRigDevicesApplied.toMutableSet()

        val rig = uiState.presetRigDevicesSupported!![rigIndex]
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

        val newSupportedDevices = uiState.presetRigDevicesSupported!!
            .toMutableList()
            .apply { this[rigIndex] = newRig }

        uiState = uiState.copy(
            presetRigDevicesSupported = newSupportedDevices,
            presetRigDevicesApplied = newDevicesApplied
        )
    }

    private fun checkDeviceChanged(id: String, checked: Boolean) {
        if (uiState.presetRigDevicesSupported == null) return

        val newDevicesApplied = uiState.presetRigDevicesApplied.toMutableSet()

        if (checked)
            newDevicesApplied.add(id)
        else
            newDevicesApplied.remove(id)

        uiState = uiState.copy(
            presetRigDevicesSupported = uiState.presetRigDevicesSupported!!.map { rig ->
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
            presetRigDevicesApplied = newDevicesApplied
        )
    }

    private fun applyDevices(presetId: String) {
        presetRepository.applyDevicesForPreset(
            presetDevicesApply = PresetDevicesApplyDto(
                presetId = presetId,
                devices = uiState.presetRigDevicesApplied.toList()
            )
        ).onEach { result ->
            result.onSuccess {
                uiAction = PresetApplyAction.OpenPreviousScreen
            }.onFailure {
                uiAction = PresetApplyAction.ShowApplyDevicesFailedSnackBar
            }
        }.launchIn(viewModelScope)
    }
}