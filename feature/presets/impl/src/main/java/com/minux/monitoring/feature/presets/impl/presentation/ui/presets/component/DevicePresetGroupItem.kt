package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.flipScale
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetGroupItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.PresetItemModel

@Composable
internal fun DevicePresetGroupItem(
    model: DevicePresetGroupItemModel,
    onApplyPresetClick: (id: String, name: String) -> Unit,
    onEditPresetClick: (id: String, deviceName: String) -> Unit,
    onRemovePresetClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    MNXExpandableCard(
        expanded = isExpanded.value,
        onExpandedChange = { isExpanded.value = it },
        modifier = modifier,
        color = MaterialTheme.colorScheme.primaryContainer,
        borderWidth = 1.dp,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = model.name ?: "Unknown device",
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Icon(
                    painter = painterResource(id = MNXIcons.DropDown),
                    contentDescription = null,
                    modifier = Modifier.flipScale(state = isExpanded.value),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {
        DevicePresetItemExpandableContent(
            presets = model.presets,
            onApplyPresetClick = onApplyPresetClick,
            onEditPresetClick = { onEditPresetClick(it, model.name!!) },
            onRemovePresetClick = onRemovePresetClick,
            modifier = Modifier
                .heightIn(max = 400.dp)
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp),
            editPresetIsEnabled = model.name != null
        )
    }
}

@Composable
private fun DevicePresetItemExpandableContent(
    presets: List<PresetItemModel>,
    onApplyPresetClick: (id: String, name: String) -> Unit,
    onEditPresetClick: (id: String) -> Unit,
    onRemovePresetClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    editPresetIsEnabled: Boolean = true
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(presets, key = { _, item -> item.id }) { index, item ->
            if (index != 0) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }

            PresetItem(
                model = item,
                onApplyPresetClick = {
                    onApplyPresetClick(item.id, item.info.presetName.ifEmpty { "Preset" })
                },
                onEditPresetClick = { onEditPresetClick(item.id) },
                onRemovePresetClick = { onRemovePresetClick(item.id) },
                modifier = Modifier.fillMaxWidth(),
                editPresetIsEnabled = editPresetIsEnabled
            )
        }
    }
}

@Preview
@Composable
private fun DevicePresetGroupItemPreview(
    @PreviewParameter(DevicePresetItemPreviewParameterProvider::class)
    devicePresetGroupItemModel: DevicePresetGroupItemModel
) {
    MNXTheme {
        DevicePresetGroupItem(
            model = devicePresetGroupItemModel,
            onApplyPresetClick = { _, _ -> },
            onEditPresetClick = { _,_ -> },
            onRemovePresetClick = {}
        )
    }
}