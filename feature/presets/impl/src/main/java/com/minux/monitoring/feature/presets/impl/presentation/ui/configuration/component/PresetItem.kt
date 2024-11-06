package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUPresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.common.GPUParametersGrid
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.gpu.GPUPresetItemPreviewParameterProvider

@Composable
internal fun PresetItem(
    model: GPUPresetItemModel,
    modifier: Modifier = Modifier
) {
    MNXCard(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = model.presetName)

                    Text(
                        text = model.deviceName,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 14.sp
                    )
                }

                PresetItemButtons(
                    onEditPreset = {},
                    onDeletePreset = {}
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            GPUParametersGrid(
                model = model.parameters,
                modifier = Modifier.heightIn(max = 400.dp)
            )
        }
    }
}

@Composable
private fun PresetItemButtons(
    onEditPreset: () -> Unit,
    onDeletePreset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            IconButton(
                onClick = onEditPreset,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = MNXIcons.Edit),
                    contentDescription = "Edit preset"
                )
            }

            IconButton(
                onClick = onDeletePreset,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.Delete),
                    contentDescription = "Delete preset",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PresetItemPreview(
    @PreviewParameter(GPUPresetItemPreviewParameterProvider::class)
    gpuPresetItemModel: GPUPresetItemModel,
) {
    MNXTheme {
        PresetItem(model = gpuPresetItemModel)
    }
}