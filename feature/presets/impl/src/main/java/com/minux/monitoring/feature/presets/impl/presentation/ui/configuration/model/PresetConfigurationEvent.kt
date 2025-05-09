package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model

import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode

internal sealed interface PresetConfigurationEvent {
    class PresetNameChanged(val presetName: String) : PresetConfigurationEvent

    class FetchDeviceParameters(val mode: ConfigurationMode) : PresetConfigurationEvent

    class SelectedDeviceChanged(val selectedDevice: String) : PresetConfigurationEvent

    class GpuCoreClockLockChanged(val coreClockLock: Float) : PresetConfigurationEvent

    class GpuCoreClockOffsetChanged(val coreClockOffset: Float) : PresetConfigurationEvent

    class GpuMemoryClockLockChanged(val memoryClockLock: Float) : PresetConfigurationEvent

    class GpuMemoryClockOffsetChanged(val memoryClockOffset: Float) : PresetConfigurationEvent

    class GpuCoreVoltageLockChanged(val coreVoltageLock: Float) : PresetConfigurationEvent

    class GpuCoreVoltageOffsetChanged(val coreVoltageOffset: Float) : PresetConfigurationEvent

    class GpuMemoryVoltageLockChanged(val memoryVoltageLock: Float) : PresetConfigurationEvent

    class GpuMemoryVoltageOffsetChanged(val memoryVoltageOffset: Float) : PresetConfigurationEvent

    class GpuPowerLimitChanged(val powerLimit: Float) : PresetConfigurationEvent

    class GpuFanSpeedChanged(val fanSpeed: Float) : PresetConfigurationEvent

    class ApplyDeviceParameters(val mode: ConfigurationMode) : PresetConfigurationEvent

    data object SaveAsPreset : PresetConfigurationEvent

    data object SaveAsPresetConfirm : PresetConfigurationEvent

    data object Back : PresetConfigurationEvent
}