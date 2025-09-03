package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetRemoveDto
import com.minux.monitoring.feature.flightsheets.impl.data.repository.FlightSheetRepository
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toFlightSheetItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model.FlightSheetsAction
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model.FlightSheetsEvent
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model.FlightSheetsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FlightSheetsViewModel @Inject constructor(
    private val flightSheetRepository: FlightSheetRepository
) : BaseViewModel<FlightSheetsUiState, FlightSheetsAction, FlightSheetsEvent>(
    initialState = FlightSheetsUiState()
) {
    override fun onEvent(uiEvent: FlightSheetsEvent) {
        when (uiEvent) {
            FlightSheetsEvent.FetchFlightSheets -> fetchFlightSheets()

            is FlightSheetsEvent.SearchQueryChanged -> searchQueryChanged(query = uiEvent.searchQuery)

            FlightSheetsEvent.CreateFlightSheet -> {
                uiAction = FlightSheetsAction
                    .OpenFlightSheetConfigurationScreen(mode = ConfigurationMode.Create)
            }

            is FlightSheetsEvent.ApplyFlightSheet -> {
                uiAction = FlightSheetsAction.OpenFlightSheetApplyScreen(
                    flightSheetId = uiEvent.flightSheetId,
                    flightSheetName = uiEvent.flightSheetName
                )
            }

            is FlightSheetsEvent.ChangeFlightSheet -> {
                uiAction = FlightSheetsAction.OpenFlightSheetConfigurationScreen(
                    mode = ConfigurationMode.Edit(flightSheetId = uiEvent.flightSheetId)
                )
            }

            is FlightSheetsEvent.RemoveFlightSheet -> {
                removeFlightSheet(flightSheetId = uiEvent.flightSheetId)
            }
        }
    }

    private fun fetchFlightSheets() {
        flightSheetRepository.getAllFlightSheets()
            .onStart { uiState = uiState.copy(flightSheetsIsLoading = true) }
            .onEach { result ->
                val flightSheets = result.getOrNull()?.map { it.toFlightSheetItemModel() }

                uiState = uiState.copy(
                    flightSheetsIsLoading = false,
                    flightSheets = flightSheets,
                    filteredFlightSheets = flightSheets
                )
            }
            .launchIn(viewModelScope)
    }

    private fun searchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                if (uiState.flightSheets != uiState.filteredFlightSheets)
                    uiState = uiState.copy(
                        searchQuery = query,
                        filteredFlightSheets = uiState.flightSheets
                    )

                return@launch
            }

            val filteredFlightSheets = uiState.flightSheets?.filter {
                it.name.contains(query, ignoreCase = true)
            }

            uiState = uiState.copy(
                searchQuery = query,
                filteredFlightSheets = filteredFlightSheets
            )
        }
    }

    private fun removeFlightSheet(flightSheetId: String) {
        val flightSheetRemoveDto = FlightSheetRemoveDto(id = flightSheetId)

        flightSheetRepository.removeFlightSheet(flightSheetRemove = flightSheetRemoveDto)
            .onEach { result ->
                result.onSuccess {
                    fetchFlightSheets()
                }.onFailure {
                    uiAction = FlightSheetsAction.ShowRemoveFlightSheetFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }
}