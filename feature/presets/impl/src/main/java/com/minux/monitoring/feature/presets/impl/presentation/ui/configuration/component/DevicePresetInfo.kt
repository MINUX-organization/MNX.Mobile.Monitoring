package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetInfoModel

@Composable
internal fun DevicePresetInfo(
    model: DevicePresetInfoModel,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = model.presetName,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.titleMedium
        )

        Text(
            text = model.deviceName,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MNXTypography.bodyMedium
        )
    }
}