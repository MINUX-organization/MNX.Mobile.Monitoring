package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetItemModel

internal data class FlightSheetsUiState(
    val flightSheetsIsLoading: Boolean = true,
    val flightSheets: List<FlightSheetItemModel>? = null,
    val searchQuery: String = "",
    val filteredFlightSheets: List<FlightSheetItemModel>? = null
)