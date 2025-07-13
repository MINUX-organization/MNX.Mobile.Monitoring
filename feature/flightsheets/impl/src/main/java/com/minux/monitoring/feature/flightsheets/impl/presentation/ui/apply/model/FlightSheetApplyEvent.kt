package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model

internal sealed interface FlightSheetApplyEvent {
    class FetchSupportedDevices(val flightSheetId: String) : FlightSheetApplyEvent

    class CheckAllDevicesOnRigChanged(val rigIndex: Int, val checked: Boolean) : FlightSheetApplyEvent

    class CheckDeviceChanged(val id: String, val checked: Boolean) : FlightSheetApplyEvent

    data object Back : FlightSheetApplyEvent

    class Confirm(val flightSheetId: String) : FlightSheetApplyEvent
}