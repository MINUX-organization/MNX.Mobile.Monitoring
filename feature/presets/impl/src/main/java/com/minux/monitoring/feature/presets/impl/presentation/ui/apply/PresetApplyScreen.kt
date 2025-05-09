package com.minux.monitoring.feature.presets.impl.presentation.ui.apply

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
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.component.PresetApplyUiStatePreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.component.PresetDeviceSelectionCard
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model.PresetApplyAction
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model.PresetApplyEvent
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model.PresetApplyUiState

@Composable
internal fun PresetApplyRoute(
    viewModel: PresetApplyViewModel,
    presetId: String,
    presetName: String,
    onNavigateUp: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    PresetApplyScreen(
        presetApplyUiState = state,
        presetId = presetId,
        presetName = presetName,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    when (action) {
        PresetApplyAction.OpenPreviousScreen -> onNavigateUp()

        PresetApplyAction.ShowApplyDevicesFailedSnackBar -> {
            onShowSnackBar("Failed to apply selected devices")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun PresetApplyScreen(
    presetApplyUiState: PresetApplyUiState,
    presetId: String,
    presetName: String,
    onEvent: (PresetApplyEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(PresetApplyEvent.FetchSupportedDevices(presetId = presetId))
    }

    Column(modifier = modifier) {
        PresetApplyHeader(
            header = "Apply preset",
            onBackClick = { onEvent(PresetApplyEvent.Back) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        PresetDeviceSelectionCard(
            presetName = presetName,
            supportedDevicesIsLoading = presetApplyUiState.presetRigDevicesSupportedIsLoading,
            supportedDevices = presetApplyUiState.presetRigDevicesSupported,
            onRefresh = { onEvent(PresetApplyEvent.FetchSupportedDevices(presetId = presetId)) },
            onAllDevicesOnRigCheckedChange = { rigIndex, checked ->
                onEvent(
                    PresetApplyEvent.CheckAllDevicesOnRigChanged(
                        rigIndex = rigIndex,
                        checked = checked
                    )
                )
            },
            onDeviceCheckedChange = { id, checked ->
                onEvent(PresetApplyEvent.CheckDeviceChanged(id = id, checked = checked))
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        MNXBorderedButton(
            onClick = { onEvent(PresetApplyEvent.Confirm(presetId = presetId)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            enabled = presetApplyUiState.presetRigDevicesSupported != null,
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
private fun PresetApplyHeader(
    header: String,
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
            text = header,
            color = MaterialTheme.colorScheme.onBackground,
            style = MNXTypography.headlineMedium
        )
    }
}

@Preview
@Composable
private fun ApplyPresetScreenPreview(
    @PreviewParameter(PresetApplyUiStatePreviewParameterProvider::class)
    presetApplyUiState: PresetApplyUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PresetApplyScreen(
                presetApplyUiState = presetApplyUiState,
                presetId = "",
                presetName = "Preset #1",
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}