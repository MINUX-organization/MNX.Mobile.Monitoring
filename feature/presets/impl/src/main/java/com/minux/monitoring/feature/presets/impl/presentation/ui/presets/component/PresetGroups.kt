package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetGroupItemModel

@Composable
internal fun PresetGroups(
    presetGroups: List<DevicePresetGroupItemModel>,
    onApplyPresetClick: (id: String, name: String) -> Unit,
    onEditPresetClick: (id: String, deviceName: String) -> Unit,
    onRemovePresetClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(presetGroups) {
            DevicePresetGroupItem(
                model = it,
                onApplyPresetClick = onApplyPresetClick,
                onEditPresetClick = onEditPresetClick,
                onRemovePresetClick = onRemovePresetClick
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
internal fun PresetGroupsShimmer() {
    Column {
        repeat(8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
internal fun PresetGroupsError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.MinuxError),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Failed to load presets",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.titleSmall
        )
    }
}

@Preview
@Composable
private fun PresetGroupsPreview() {
    MNXTheme {
        PresetGroups(
            presetGroups = emptyList(),
            onApplyPresetClick = { _, _ -> },
            onEditPresetClick = { _, _ -> },
            onRemovePresetClick = {}
        )
    }
}

@Preview
@Composable
private fun PresetGroupsShimmerPreview() {
    MNXTheme {
        PresetGroupsShimmer()
    }
}

@Preview
@Composable
private fun PresetGroupsErrorPreview() {
    MNXTheme {
        PresetGroupsError()
    }
}