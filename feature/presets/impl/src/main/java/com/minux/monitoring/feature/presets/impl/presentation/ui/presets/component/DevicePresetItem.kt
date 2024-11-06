package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUPresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.gpu.GPUPresetItem

@Composable
internal fun DevicePresetItem(
    model: DevicePresetItemModel,
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
                    text = model.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = grillSansMtFamily
                )

                Icon(
                    painter = painterResource(id = MNXIcons.DropDown),
                    contentDescription = "",
                    modifier = Modifier.flipScale(state = isExpanded.value),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {
        DevicePresetItemExpandableContent(
            presets = model.presets,
            modifier = Modifier
                .heightIn(max = 400.dp)
                .padding(horizontal = 10.dp)
                .padding(
                    top = 6.dp,
                    bottom = 10.dp
                )
        )
    }
}

@Composable
private fun DevicePresetItemExpandableContent(
    presets: List<GPUPresetItemModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(presets, key = { it.deviceName }) {
            GPUPresetItem(
                model = it,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun DevicePresetCardItemPreview(
    @PreviewParameter(DevicePresetItemPreviewParameterProvider::class)
    devicePresetItemModel: DevicePresetItemModel
) {
    MNXTheme {
        DevicePresetItem(model = devicePresetItemModel)
    }
}