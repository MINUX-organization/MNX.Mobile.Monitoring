package com.minux.monitoring.feature.presets.impl.presentation.ui.apply.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.minux.monitoring.core.designsystem.component.CheckBoxLabelPosition
import com.minux.monitoring.core.designsystem.component.MNXCheckBox
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.flipScale
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceGroupItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceItemModel

@Composable
internal fun RigDeviceGroupItem(
    model: DeviceGroupItemModel<DeviceGroupItemModel<DeviceItemModel>>,
    onAllDevicesCheckedChange: (checked: Boolean) -> Unit,
    onDeviceCheckedChange: (id: String, checked: Boolean) -> Unit,
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
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MNXCheckBox(
                    checked = true,
                    onCheckedChange = onAllDevicesCheckedChange,
                    label = {
                        Text(
                            text = model.name ?: "Rig",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MNXTypography.titleMedium
                        )
                    },
                    labelPosition = CheckBoxLabelPosition.End
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            model.elements.fastForEach { deviceGroup ->
                HorizontalDivider()

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = deviceGroup.name ?: "",
                        color = MaterialTheme.colorScheme.primary,
                        style = MNXTypography.titleMedium
                    )

                    val painter = when (deviceGroup.name) {
                        "CPU" -> painterResource(id = MNXIcons.Cpu)

                        "GPU" -> painterResource(id = MNXIcons.Gpu)

                        else -> null
                    }

                    painter?.let {
                        Icon(
                            painter = it,
                            contentDescription = deviceGroup.name,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(deviceGroup.elements) { device ->
                        DeviceItem(
                            model = device,
                            onCheckedChange = { onDeviceCheckedChange(device.id, it) }
                        )

                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}