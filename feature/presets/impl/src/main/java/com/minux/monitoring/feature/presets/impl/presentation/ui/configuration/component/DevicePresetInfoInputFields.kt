package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetInfoModel

@Composable
internal fun DevicePresetInfoInputFields(
    model: DevicePresetInfoModel,
    isLoading: Boolean,
    devices: List<String>,
    onPresetNameChange: (String) -> Unit,
    onSelectedDeviceChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    presetNameEnabled: Boolean = true,
    devicesEnabled: Boolean = true,
) {
    Column(modifier = modifier) {
        MNXTextField(
            value = model.presetName,
            onValueChange = onPresetNameChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = presetNameEnabled,
            label = {
                Text(
                    text = "Name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )
            },
            placeholder = { Text("Enter preset name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Device name",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        } else {
            MNXDropDownMenu(
                menuItems = devices,
                selectedMenuItem = model.deviceName.ifEmpty { "N/A" },
                onSelectedMenuItemChange = onSelectedDeviceChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = devicesEnabled && devices.isNotEmpty()
            )
        }
    }
}

@Preview
@Composable
private fun DevicePresetInfoFieldsPreview() {
    MNXTheme {
        DevicePresetInfoInputFields(
            model = DevicePresetInfoModel(),
            isLoading = true,
            devices = emptyList(),
            devicesEnabled = false,
            onPresetNameChange = {},
            onSelectedDeviceChange = {}
        )
    }
}