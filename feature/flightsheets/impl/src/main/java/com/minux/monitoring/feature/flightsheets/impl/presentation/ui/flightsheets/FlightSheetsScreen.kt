package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXFloatingActionButton
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.SearchTextField
import com.minux.monitoring.feature.flightsheets.impl.presentation.navigation.FlightSheetsFlowRoute
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component.FlightSheets
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component.FlightSheetsError
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component.FlightSheetsShimmer
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component.FlightSheetsUiStatePreviewParameterProvider
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model.FlightSheetsAction
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model.FlightSheetsEvent
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.model.FlightSheetsUiState
import kotlinx.coroutines.delay

@Composable
internal fun FlightSheetsRoute(
    viewModel: FlightSheetsViewModel,
    onNavigate: (FlightSheetsFlowRoute) -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    FlightSheetsScreen(
        flightSheetsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    when (action) {
        is FlightSheetsAction.OpenFlightSheetApplyScreen -> {
            val flightSheetId = (action as FlightSheetsAction.OpenFlightSheetApplyScreen).flightSheetId
            val flightSheetName = (action as FlightSheetsAction.OpenFlightSheetApplyScreen).flightSheetName

            onNavigate(
                FlightSheetsFlowRoute.FlightSheetApply(
                    id = flightSheetId,
                    name = flightSheetName
                )
            )
        }

        is FlightSheetsAction.OpenFlightSheetConfigurationScreen -> {
            val configurationMode = (action as FlightSheetsAction.OpenFlightSheetConfigurationScreen).mode
            onNavigate(FlightSheetsFlowRoute.FlightSheetConfiguration(mode = configurationMode))
        }

        FlightSheetsAction.ShowRemoveFlightSheetFailedSnackBar -> {
            onShowSnackBar("Remove flight sheet failed")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlightSheetsScreen(
    flightSheetsUiState: FlightSheetsUiState,
    onEvent: (FlightSheetsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(FlightSheetsEvent.FetchFlightSheets)
    }

    Scaffold(
        floatingActionButton = {
            MNXFloatingActionButton(onClick = { onEvent(FlightSheetsEvent.CreateFlightSheet) }) {
                Icon(
                    painter = painterResource(id = MNXIcons.Add),
                    contentDescription = "Create flight sheet",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { scaffoldPadding ->
        Column(modifier = modifier.padding(scaffoldPadding)) {
            Text(
                text = "Flight Sheets",
                color = MaterialTheme.colorScheme.onBackground,
                style = MNXTypography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            SearchTextField(
                query = flightSheetsUiState.searchQuery,
                onQueryChange = { onEvent(FlightSheetsEvent.SearchQueryChanged(searchQuery = it)) },
                enabled = !flightSheetsUiState.flightSheetsIsLoading &&
                        !flightSheetsUiState.flightSheets.isNullOrEmpty(),
                placeholder = { Text(text = "Search") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            val isRefreshing = remember { mutableStateOf(false) }

            LaunchedEffect(isRefreshing.value) {
                if (isRefreshing.value) {
                    onEvent(FlightSheetsEvent.FetchFlightSheets)
                    delay(200)
                    isRefreshing.value = false
                }
            }

            PullToRefreshBox(
                isRefreshing = isRefreshing.value,
                onRefresh = { isRefreshing.value = true },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    when {
                        flightSheetsUiState.flightSheetsIsLoading -> FlightSheetsShimmer()

                        flightSheetsUiState.filteredFlightSheets == null -> FlightSheetsError(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        )

                        else -> FlightSheets(
                            flightSheets = flightSheetsUiState.filteredFlightSheets,
                            onApplyFlightSheetClick = { id, name ->
                                onEvent(
                                    FlightSheetsEvent.ApplyFlightSheet(
                                        flightSheetId = id,
                                        flightSheetName = name
                                    )
                                )
                            },
                            onEditFlightSheetClick = { id ->
                                onEvent(FlightSheetsEvent.ChangeFlightSheet(flightSheetId = id))
                            },
                            onRemoveFlightSheetClick = { id ->
                                onEvent(FlightSheetsEvent.RemoveFlightSheet(flightSheetId = id))
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FlightSheetsScreenPreview(
    @PreviewParameter(FlightSheetsUiStatePreviewParameterProvider::class)
    flightSheetsUiState: FlightSheetsUiState
) {
    MNXTheme {
        FlightSheetsScreen(
            flightSheetsUiState = flightSheetsUiState,
            onEvent = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}