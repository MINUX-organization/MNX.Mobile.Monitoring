package com.minux.monitoring.feature.flightsheets.impl.presentation.navigation

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.ConfigurationMode
import kotlinx.serialization.Serializable

internal sealed interface FlightSheetsFlowRoute {
    @Serializable
    data object FlightSheets : FlightSheetsFlowRoute

    @Serializable
    class FlightSheetApply(val id: String, val name: String) : FlightSheetsFlowRoute

    @Serializable
    class FlightSheetConfiguration(val mode: ConfigurationMode) : FlightSheetsFlowRoute
}