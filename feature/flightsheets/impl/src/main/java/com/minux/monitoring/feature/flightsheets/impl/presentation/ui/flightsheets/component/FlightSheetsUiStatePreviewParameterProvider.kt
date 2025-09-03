package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model.FlightSheetsUiState

internal class FlightSheetsUiStatePreviewParameterProvider : PreviewParameterProvider<FlightSheetsUiState> {
    private val flightSheetItems = FlightSheetItemPreviewParameterProvider().values.toList()

    private val uiState = FlightSheetsUiState(
        filteredFlightSheets = flightSheetItems
    )

    override val values: Sequence<FlightSheetsUiState> = sequenceOf(
        uiState.copy(flightSheetsIsLoading = false)
    )
}