package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.BackButton
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.component.FlightSheetApplyUiStatePreviewParameterProvider
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.component.FlightSheetDeviceSelectionCard
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model.FlightSheetApplyAction
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model.FlightSheetApplyEvent
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model.FlightSheetApplyUiState

@Composable
internal fun FlightSheetApplyRoute(
    viewModel: FlightSheetApplyViewModel,
    flightSheetId: String,
    flightSheetName: String,
    onNavigateUp: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    FlightSheetApplyScreen(
        flightSheetApplyUiState = state,
        flightSheetId = flightSheetId,
        flightSheetName = flightSheetName,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    when (action) {
        FlightSheetApplyAction.OpenPreviousScreen -> onNavigateUp()

        FlightSheetApplyAction.ShowApplyDevicesFailedSnackBar -> {
            onShowSnackBar("Failed to apply selected devices")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun FlightSheetApplyScreen(
    flightSheetApplyUiState: FlightSheetApplyUiState,
    flightSheetId: String,
    flightSheetName: String,
    onEvent: (FlightSheetApplyEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(FlightSheetApplyEvent.FetchSupportedDevices(flightSheetId = flightSheetId))
    }

    Column(modifier = modifier) {
        FlightSheetApplyHeader(
            onBackClick = { onEvent(FlightSheetApplyEvent.Back) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlightSheetDeviceSelectionCard(
            flightSheetName = flightSheetName,
            supportedDevicesIsLoading = flightSheetApplyUiState.flightSheetRigDevicesSupportedIsLoading,
            supportedDevices = flightSheetApplyUiState.flightSheetRigDevicesSupported,
            onRefresh = { onEvent(FlightSheetApplyEvent.FetchSupportedDevices(flightSheetId = flightSheetId)) },
            onAllDevicesOnRigCheckedChange = { rigIndex, checked ->
                onEvent(
                    FlightSheetApplyEvent.CheckAllDevicesOnRigChanged(
                        rigIndex = rigIndex,
                        checked = checked
                    )
                )
            },
            onDeviceCheckedChange = { id, checked ->
                onEvent(FlightSheetApplyEvent.CheckDeviceChanged(id = id, checked = checked))
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        MNXBorderedButton(
            onClick = { onEvent(FlightSheetApplyEvent.Confirm(flightSheetId = flightSheetId)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            enabled = flightSheetApplyUiState.flightSheetRigDevicesSupported != null,
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Text(
                text = "Confirm",
                style = MNXTypography.bodyLarge
            )
        }
    }
}

@Composable
private fun FlightSheetApplyHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onClick = onBackClick)

        Text(
            text = "Apply flight sheet",
            color = MaterialTheme.colorScheme.onBackground,
            style = MNXTypography.headlineMedium
        )
    }
}

@Preview
@Composable
private fun FlightSheetApplyScreenPreview(
    @PreviewParameter(FlightSheetApplyUiStatePreviewParameterProvider::class)
    flightSheetApplyUiState: FlightSheetApplyUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            FlightSheetApplyScreen(
                flightSheetApplyUiState = flightSheetApplyUiState,
                flightSheetId = "",
                flightSheetName = "Flight Sheet #1",
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}