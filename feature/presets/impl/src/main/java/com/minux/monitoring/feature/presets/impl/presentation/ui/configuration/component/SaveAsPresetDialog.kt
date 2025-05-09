package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.component.MNXDialog
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationEvent
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationUiState

@Composable
internal fun SaveAsPresetDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    presetConfigurationUiState: PresetConfigurationUiState,
    onEvent: (PresetConfigurationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    MNXDialog(
        showDialog = showDialog,
        onShowDialogChange = onShowDialogChange
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Save as preset",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.titleMedium
            )

            DevicePresetInfoInputFields(
                model = presetConfigurationUiState.currentPreset.info,
                isLoading = presetConfigurationUiState.devicesIsLoading,
                devices = presetConfigurationUiState.devices,
                onPresetNameChange = { onEvent(PresetConfigurationEvent.PresetNameChanged(it)) },
                onSelectedDeviceChange = { onEvent(PresetConfigurationEvent.SelectedDeviceChanged(it)) },
                devicesEnabled = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            SavePresetButtons(
                onSaveClick = { onEvent(PresetConfigurationEvent.SaveAsPresetConfirm) },
                onCancelClick = { onShowDialogChange(false) }
            )
        }
    }
}

@Composable
private fun SavePresetButtons(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        MNXBorderedButton(
            onClick = onCancelClick,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            color = MaterialTheme.colorScheme.secondary
        ) {
            Text(
                text = "Cancel",
                style = MNXTypography.bodyLarge
            )
        }

        MNXBorderedButton(
            onClick = onSaveClick,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            color = MaterialTheme.colorScheme.primary,
            contentPadding = PaddingValues(horizontal = 48.dp)
        ) {
            Text(
                text = "Save",
                style = MNXTypography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun SaveAsPresetDialogPreview(
    @PreviewParameter(PresetConfigurationUiStatePreviewParameterProvider::class)
    presetConfigurationUiState: PresetConfigurationUiState
) {
    MNXTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SaveAsPresetDialog(
                showDialog = true,
                onShowDialogChange = {},
                presetConfigurationUiState = presetConfigurationUiState,
                onEvent = {},
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
