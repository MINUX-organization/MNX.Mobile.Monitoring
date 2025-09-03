package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.ConfigurationMode

internal sealed interface FlightSheetsAction {
    class OpenFlightSheetApplyScreen(
        val flightSheetId: String,
        val flightSheetName: String
    ) : FlightSheetsAction

    class OpenFlightSheetConfigurationScreen(val mode: ConfigurationMode) : FlightSheetsAction

    data object ShowRemoveFlightSheetFailedSnackBar : FlightSheetsAction
}