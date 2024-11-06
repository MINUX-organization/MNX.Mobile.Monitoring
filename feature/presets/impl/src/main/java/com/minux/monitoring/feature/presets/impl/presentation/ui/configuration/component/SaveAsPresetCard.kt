package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUPresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.SaveAsPresetModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.gpu.GPUPresetItemPreviewParameterProvider

@Composable
internal fun SaveAsPresetCard(
    model: SaveAsPresetModel,
    modifier: Modifier = Modifier
) {
    MNXCard(
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.onPrimary)) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Save as preset",
                    fontSize = 20.sp
                )

                DevicePresetOptions(devices = model.devices)

                Spacer(modifier = Modifier.height(24.dp))

                SaveAsPresetButtons(
                    onResetChanges = {},
                    onSaveChanges = {}
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDividerWithLabel(
                    label = { Text(text = "List of presets") }
                )

                LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                    items(model.presets) {
                        Spacer(modifier = Modifier.height(8.dp))

                        PresetItem(model = it)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun DevicePresetOptions(
    devices: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Name",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        val presetName = remember {
            mutableStateOf("")
        }

        MNXTextField(
            value = presetName.value,
            onValueChange = { presetName.value = it },
            modifier = Modifier.fillMaxWidth(),
            hint = "Preset #1"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Card",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        val selectedDevice = remember {
            mutableStateOf(devices.first())
        }

        MNXDropDownMenu(
            menuItems = devices,
            selectedMenuItem = selectedDevice.value,
            onSelectedMenuItemChange = { selectedDevice.value = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SaveAsPresetButtons(
    onResetChanges: () -> Unit,
    onSaveChanges: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        MNXBorderedButton(
            onClick = onResetChanges,
            modifier = Modifier.height(40.dp),
            color = MaterialTheme.colorScheme.secondary,
            contentPadding = PaddingValues(horizontal = 48.dp)
        ) {
            Text(
                text = "Reset",
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily
            )
        }

        MNXBorderedButton(
            onClick = onSaveChanges,
            modifier = Modifier.height(40.dp),
            color = MaterialTheme.colorScheme.primary,
            contentPadding = PaddingValues(horizontal = 48.dp)
        ) {
            Text(
                text = "Save",
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily
            )
        }
    }
}

@Preview
@Composable
private fun SaveAsPresetCardPreview(
    @PreviewParameter(GPUPresetItemPreviewParameterProvider::class)
    gpuPresetItemModel: GPUPresetItemModel
) {
    MNXTheme {
        SaveAsPresetCard(
            model = SaveAsPresetModel(
                devices = listOf(gpuPresetItemModel.deviceName),
                presets = listOf(gpuPresetItemModel)
            )
        )
    }
}
