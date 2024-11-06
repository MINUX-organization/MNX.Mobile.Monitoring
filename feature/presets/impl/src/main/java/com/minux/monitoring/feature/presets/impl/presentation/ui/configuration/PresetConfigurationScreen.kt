package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.ui.BackButton
import com.minux.monitoring.feature.presets.impl.presentation.model.SaveAsPresetModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component.PresetOptionsCard
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component.SaveAsPresetCard

@Composable
internal fun PresetConfigurationScreen(
    uiState: PresetConfigurationUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        PresetConfigurationHeader(
            onBackClick = {},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PresetOptionsCard(
            model = uiState.currentPreset,
            deviceInfo = {}
        )

        Spacer(modifier = Modifier.height(12.dp))

        // TODO: Move "SaveAsPresetCard" to Bottom sheet
        SaveAsPresetCard(
            model = SaveAsPresetModel(
                devices = uiState.devices,
                presets = uiState.presets
            )
        )
    }
}

@Composable
private fun PresetConfigurationHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(
            onClick = onBackClick,
            modifier = Modifier.size(
                width = 100.dp,
                height = 40.dp
            )
        )

        Text(
            text = "Add/Edit preset", // Will be changing from various screen arg
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 28.sp
        )
    }
}

@Preview
@Composable
private fun PresetConfigurationScreenPreview(
    @PreviewParameter(PresetConfigurationUiStatePreviewParameterProvider::class)
    presetConfigurationUiState: PresetConfigurationUiState
) {
    MNXTheme {
        PresetConfigurationScreen(uiState = presetConfigurationUiState)
    }
}