package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.presets.impl.data.model.PresetGetDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingGetDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingSetDto
import com.minux.monitoring.feature.presets.impl.data.model.device.GpuRestrictionsGetByIdDto
import com.minux.monitoring.feature.presets.impl.data.model.device.GpuRestrictionsGetByNameDto
import com.minux.monitoring.feature.presets.impl.data.repository.DeviceRepository
import com.minux.monitoring.feature.presets.impl.data.repository.PresetRepository
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toDeviceOverclockingDto
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toDeviceParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toPresetChangeDto
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toPresetInputDto
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toPresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetInfoModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuOtherParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuTuningParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationAction
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationEvent
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationUiState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class PresetConfigurationViewModel @Inject constructor(
    private val presetRepository: PresetRepository,
    private val deviceRepository: DeviceRepository
) : BaseViewModel<PresetConfigurationUiState, PresetConfigurationAction, PresetConfigurationEvent>(
    initialState = PresetConfigurationUiState()
) {
    override fun onEvent(uiEvent: PresetConfigurationEvent) {
        when (uiEvent) {
            is PresetConfigurationEvent.PresetNameChanged -> {
                presetNameChanged(presetName = uiEvent.presetName)
            }

            is PresetConfigurationEvent.FetchDeviceParameters -> {
                fetchDeviceParameters(mode = uiEvent.mode)
            }

            is PresetConfigurationEvent.SelectedDeviceChanged -> {
                selectedDeviceChanged(selectedDevice = uiEvent.selectedDevice)
            }

            is PresetConfigurationEvent.GpuCoreClockLockChanged -> {
                changeGpuPresetParameter(
                    clockingTransform = {
                        it.copy(coreLock = it.coreLock?.copy(value = uiEvent.coreClockLock))
                    }
                )
            }

            is PresetConfigurationEvent.GpuCoreClockOffsetChanged -> {
                changeGpuPresetParameter(
                    clockingTransform = {
                        it.copy(coreLock = it.coreOffset?.copy(value = uiEvent.coreClockOffset))
                    }
                )
            }

            is PresetConfigurationEvent.GpuMemoryClockLockChanged -> {
                changeGpuPresetParameter(
                    clockingTransform = {
                        it.copy(memoryLock = it.memoryLock?.copy(value = uiEvent.memoryClockLock))
                    }
                )
            }

            is PresetConfigurationEvent.GpuMemoryClockOffsetChanged -> {
                changeGpuPresetParameter(
                    clockingTransform = {
                        it.copy(memoryOffset = it.memoryOffset?.copy(value = uiEvent.memoryClockOffset))
                    }
                )
            }

            is PresetConfigurationEvent.GpuCoreVoltageLockChanged -> {
                changeGpuPresetParameter(
                    voltageTransform = {
                        it.copy(coreLock = it.coreLock?.copy(value = uiEvent.coreVoltageLock))
                    }
                )
            }

            is PresetConfigurationEvent.GpuCoreVoltageOffsetChanged -> {
                changeGpuPresetParameter(
                    voltageTransform = {
                        it.copy(coreOffset = it.coreOffset?.copy(value = uiEvent.coreVoltageOffset))
                    }
                )
            }

            is PresetConfigurationEvent.GpuMemoryVoltageLockChanged -> {
                changeGpuPresetParameter(
                    voltageTransform = {
                        it.copy(memoryLock = it.memoryLock?.copy(value = uiEvent.memoryVoltageLock))
                    }
                )
            }

            is PresetConfigurationEvent.GpuMemoryVoltageOffsetChanged -> {
                changeGpuPresetParameter(
                    voltageTransform = {
                        it.copy(memoryOffset = it.memoryOffset?.copy(value = uiEvent.memoryVoltageOffset))
                    }
                )
            }

            is PresetConfigurationEvent.GpuPowerLimitChanged -> {
                changeGpuPresetParameter(
                    otherTransform = {
                        it.copy(powerLimit = it.powerLimit?.copy(value = uiEvent.powerLimit))
                    }
                )
            }

            is PresetConfigurationEvent.GpuFanSpeedChanged -> {
                changeGpuPresetParameter(
                    otherTransform = {
                        it.copy(fanSpeed = it.fanSpeed?.copy(value = uiEvent.fanSpeed))
                    }
                )
            }

            is PresetConfigurationEvent.ApplyDeviceParameters -> {
                applyDeviceParameters(mode = uiEvent.mode)
            }

            PresetConfigurationEvent.SaveAsPreset -> {
                uiAction = PresetConfigurationAction.OpenSaveAsPresetDialog
            }

            PresetConfigurationEvent.SaveAsPresetConfirm -> saveAsPreset()

            PresetConfigurationEvent.Back -> {
                uiAction = PresetConfigurationAction.OpenPreviousScreen
            }
        }
    }

    private fun presetNameChanged(presetName: String) {
        val preset = uiState.currentPreset

        uiState = uiState.copy(
            currentPreset = preset.copy(
                info = preset.info.copy(presetName = presetName)
            )
        )
    }

    private fun fetchDeviceParameters(mode: ConfigurationMode) {
        when (mode) {
            ConfigurationMode.Create -> fetchDevices()

            is ConfigurationMode.Edit -> fetchDevicePreset(
                id = mode.presetId,
                deviceName = mode.deviceName
            )

            is ConfigurationMode.Overclock -> fetchDeviceOverclocking(
                id = mode.deviceId,
                name = mode.deviceName
            )
        }
    }

    private fun fetchDevices() {
        deviceRepository.getGpuNames()
            .onStart {
                uiState = uiState.copy(
                    devicesIsLoading = true,
                    currentPreset = uiState.currentPreset.copy(parametersIsLoading = true)
                )
            }
            .onEach { result ->
                uiState = uiState.copy(
                    devicesIsLoading = false,
                    devices = result.getOrDefault(emptyList())
                )

                result.onSuccess {
                    selectedDeviceChanged(selectedDevice = it.firstOrNull() ?: "")
                }.onFailure {
                    uiState = uiState.copy(
                        currentPreset = uiState.currentPreset.copy(parametersIsLoading = false)
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun selectedDeviceChanged(selectedDevice: String) {
        val preset = uiState.currentPreset

        uiState = uiState.copy(
            currentPreset = preset.copy(
                info = preset.info.copy(deviceName = selectedDevice)
            )
        )

        fetchDeviceRestrictions(deviceName = selectedDevice)
    }

    // For preset creating
    private fun fetchDeviceRestrictions(deviceName: String) {
        val preset = uiState.currentPreset

        if (deviceName.isEmpty()) {
            uiState = uiState.copy(currentPreset = preset.copy(parametersIsLoading = false))
            return
        }

        deviceRepository.getGpuRestrictionsByName(
            gpuRestrictionsGetByName = GpuRestrictionsGetByNameDto(gpuName = deviceName)
        ).onEach { result ->
            result.onSuccess {
                uiState = uiState.copy(
                    currentPreset = preset.copy(
                        parametersIsLoading = false,
                        parameters = DeviceOverclockingDto.GpuOverclockingDto()
                            .toDeviceParametersModel(restrictions = it)
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    // For preset editing
    private fun fetchDevicePreset(id: String, deviceName: String) {
        uiState = uiState.copy(
            currentPreset = uiState.currentPreset.copy(parametersIsLoading = true)
        )

        combine(
            deviceRepository.getGpuRestrictionsByName(
                gpuRestrictionsGetByName = GpuRestrictionsGetByNameDto(gpuName = deviceName)
            ),
            presetRepository.getPreset(presetGet = PresetGetDto(presetId = id))
        ) { restrictions, preset ->
            restrictions.onSuccess { deviceRestrictions ->
                preset.onSuccess {
                    uiState = uiState.copy(
                        currentPreset = it.toPresetItemModel(gpuRestrictions = deviceRestrictions)
                    )
                }
            }
        }
    }

    // For device overclocking
    private fun fetchDeviceOverclocking(id: String, name: String) {
        uiState = uiState.copy(
            currentPreset = uiState.currentPreset.copy(parametersIsLoading = true)
        )

        combine(
            deviceRepository.getGpuRestrictionsById(
                gpuRestrictionsGetById = GpuRestrictionsGetByIdDto(gpuId = id)
            ),
            deviceRepository.getDeviceOverclocking(
                deviceOverclockingGet = DeviceOverclockingGetDto(id = id)
            )
        ) { restrictions, overclocking ->
            restrictions.onSuccess { deviceRestrictions ->
                overclocking.onSuccess { deviceOverclocking ->
                    uiState = uiState.copy(
                        currentPreset = uiState.currentPreset.copy(
                            info = DevicePresetInfoModel(deviceName = name),
                            parametersIsLoading = false,
                            parameters = deviceOverclocking.toDeviceParametersModel(
                                restrictions = deviceRestrictions
                            )
                        )
                    )
                }
            }
        }
    }

    private fun changeGpuPresetParameter(
        clockingTransform: (GpuTuningParametersModel) -> GpuTuningParametersModel = { it },
        voltageTransform: (GpuTuningParametersModel) -> GpuTuningParametersModel = { it },
        otherTransform: (GpuOtherParametersModel) -> GpuOtherParametersModel = { it }
    ) {
        val preset = uiState.currentPreset
        val parameters = preset.parameters as DeviceParametersModel.GpuParametersModel

        uiState = uiState.copy(
            currentPreset = preset.copy(
                parameters = parameters.copy(
                    clocking = clockingTransform(parameters.clocking),
                    voltage = voltageTransform(parameters.voltage),
                    other = otherTransform(parameters.other)
                )
            )
        )
    }

    private fun applyDeviceParameters(mode: ConfigurationMode) {
        when (mode) {
            ConfigurationMode.Create -> createPreset()
            is ConfigurationMode.Edit -> editPreset()
            is ConfigurationMode.Overclock -> applyOverclocking(deviceId = mode.deviceId)
        }
    }

    private fun createPreset() {
        presetRepository.addPreset(presetInput = uiState.currentPreset.toPresetInputDto())
            .onEach { result ->
                result.onSuccess {
                    uiAction = PresetConfigurationAction.OpenPreviousScreen
                }.onFailure {
                    uiAction = PresetConfigurationAction.ShowCreatePresetFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }

    private fun editPreset() {
        presetRepository.changePreset(presetChange = uiState.currentPreset.toPresetChangeDto())
            .onEach { result ->
                result.onSuccess {
                    uiAction = PresetConfigurationAction.OpenPreviousScreen
                }.onFailure {
                    uiAction = PresetConfigurationAction.ShowChangePresetFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }

    private fun applyOverclocking(deviceId: String) {
        deviceRepository.setDeviceOverclocking(
            deviceOverclockingSet = DeviceOverclockingSetDto(
                id = deviceId,
                overclocking = uiState.currentPreset.parameters!!.toDeviceOverclockingDto()
            )
        ).onEach { result ->
            result.onSuccess {
                uiAction = PresetConfigurationAction.OpenPreviousScreen
            }.onFailure {
                uiAction = PresetConfigurationAction.ShowApplyOverclockingFailedSnackBar
            }
        }.launchIn(viewModelScope)
    }

    private fun saveAsPreset() {
        presetRepository.addPreset(presetInput = uiState.currentPreset.toPresetInputDto())
            .onEach { result ->
                result.onSuccess {
                    uiAction = PresetConfigurationAction.CloseSaveAsPresetDialog
                }.onFailure {
                    uiAction = PresetConfigurationAction.ShowSaveAsPresetFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }
}