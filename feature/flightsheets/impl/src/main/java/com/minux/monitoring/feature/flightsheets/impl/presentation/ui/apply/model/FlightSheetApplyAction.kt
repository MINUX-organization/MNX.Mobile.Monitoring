package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model

internal sealed interface FlightSheetApplyAction {
    data object OpenPreviousScreen : FlightSheetApplyAction

    data object ShowApplyDevicesFailedSnackBar : FlightSheetApplyAction
}