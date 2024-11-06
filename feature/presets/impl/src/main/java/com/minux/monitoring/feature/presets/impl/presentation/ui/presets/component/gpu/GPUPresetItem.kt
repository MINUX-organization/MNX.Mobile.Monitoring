package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.gpu

import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUPresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.common.GPUParametersGrid

@Composable
internal fun GPUPresetItem(
    model: GPUPresetItemModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        HorizontalDivider()

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = model.presetName,
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = grillSansMtFamily
            )

            GPUPresetOptions(modifier = Modifier.padding(start = 16.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        GPUParametersGrid(
            model = model.parameters,
            modifier = Modifier.heightIn(max = 400.dp)
        )
    }
}

@Composable
private fun GPUPresetOptions(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        GPUPresetOptionButton(
            onClick = {},
            icon = painterResource(id = MNXIcons.Check),
            contentDescription = "Apply preset",
            modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiary)
        )

        GPUPresetOptionButton(
            onClick = {},
            icon = painterResource(id = MNXIcons.Edit),
            contentDescription = "Edit preset",
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
        )

        GPUPresetOptionButton(
            onClick = {},
            icon = rememberVectorPainter(image = Icons.Default.Delete),
            contentDescription = "Remove preset",
            modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
private fun GPUPresetOptionButton(
    onClick: () -> Unit,
    icon: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(32.dp)
                .then(modifier)
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun GPUPresetItemPreview(
    @PreviewParameter(GPUPresetItemPreviewParameterProvider::class)
    gpuPresetItemModel: GPUPresetItemModel
) {
    MNXTheme {
        GPUPresetItem(
            model = gpuPresetItemModel,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
        )
    }
}