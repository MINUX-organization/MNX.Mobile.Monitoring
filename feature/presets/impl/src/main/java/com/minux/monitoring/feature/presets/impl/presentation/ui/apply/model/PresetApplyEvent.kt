package com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model

internal sealed interface PresetApplyEvent {
    class FetchSupportedDevices(val presetId: String) : PresetApplyEvent

    class CheckAllDevicesOnRigChanged(val rigIndex: Int, val checked: Boolean) : PresetApplyEvent

    class CheckDeviceChanged(val id: String, val checked: Boolean) : PresetApplyEvent

    data object Back : PresetApplyEvent

    class Confirm(val presetId: String) : PresetApplyEvent
}