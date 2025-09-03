package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model

internal sealed interface FlightSheetConfigurationAction {
    data object OpenPreviousScreen : FlightSheetConfigurationAction

    data object ShowCreateFlightSheetFailedSnackBar : FlightSheetConfigurationAction

    data object ShowChangeFlightSheetFailedSnackBar : FlightSheetConfigurationAction
}