package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.PresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.common.GpuParametersGrid
import com.minux.monitoring.feature.presets.impl.presentation.ui.common.PresetOptionButton

@Composable
internal fun PresetItem(
    model: PresetItemModel,
    onApplyPresetClick: () -> Unit,
    onEditPresetClick: () -> Unit,
    onRemovePresetClick: () -> Unit,
    modifier: Modifier = Modifier,
    editPresetIsEnabled: Boolean = true
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = model.info.presetName.ifEmpty { "Untitled preset" },
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.titleLarge
            )

            PresetOptionButtons(
                onApplyPresetClick = onApplyPresetClick,
                onEditPresetClick = onEditPresetClick,
                onRemovePresetClick = onRemovePresetClick,
                modifier = Modifier.padding(start = 16.dp),
                editPresetIsEnabled = editPresetIsEnabled
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        when (model.parameters) {
            DeviceParametersModel.CpuParametersModel -> {}

            is DeviceParametersModel.GpuParametersModel -> {
                GpuParametersGrid(
                    model = model.parameters,
                    modifier = Modifier.heightIn(max = 400.dp)
                )
            }

            null -> {}
        }
    }
}

@Composable
private fun PresetOptionButtons(
    onApplyPresetClick: () -> Unit,
    onEditPresetClick: () -> Unit,
    onRemovePresetClick: () -> Unit,
    modifier: Modifier = Modifier,
    editPresetIsEnabled: Boolean = true
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PresetOptionButton(
            onClick = onApplyPresetClick,
            icon = painterResource(id = MNXIcons.Check),
            contentDescription = "Apply preset",
            color = MaterialTheme.colorScheme.tertiary
        )

        if (editPresetIsEnabled) {
            PresetOptionButton(
                onClick = onEditPresetClick,
                icon = painterResource(id = MNXIcons.Edit),
                contentDescription = "Edit preset"
            )
        }

        PresetOptionButton(
            onClick = onRemovePresetClick,
            icon = painterResource(id = MNXIcons.Trash),
            contentDescription = "Remove preset",
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun PresetItemPreview(
    @PreviewParameter(PresetItemPreviewParameterProvider::class)
    presetItemModel: PresetItemModel
) {
    MNXTheme {
        PresetItem(
            model = presetItemModel,
            onApplyPresetClick = {},
            onEditPresetClick = {},
            onRemovePresetClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
        )
    }
}