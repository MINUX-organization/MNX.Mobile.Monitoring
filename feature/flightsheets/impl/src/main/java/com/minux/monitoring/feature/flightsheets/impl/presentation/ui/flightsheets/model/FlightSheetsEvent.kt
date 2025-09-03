package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model

internal sealed interface FlightSheetsEvent {
    data object FetchFlightSheets : FlightSheetsEvent

    class SearchQueryChanged(val searchQuery: String) : FlightSheetsEvent

    data object CreateFlightSheet : FlightSheetsEvent

    class ApplyFlightSheet(
        val flightSheetId: String,
        val flightSheetName: String
    ) : FlightSheetsEvent

    class ChangeFlightSheet(val flightSheetId: String) : FlightSheetsEvent

    class RemoveFlightSheet(val flightSheetId: String) : FlightSheetsEvent
}