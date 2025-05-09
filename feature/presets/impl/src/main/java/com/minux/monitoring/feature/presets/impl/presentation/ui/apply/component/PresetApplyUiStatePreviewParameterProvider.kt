package com.minux.monitoring.feature.presets.impl.presentation.ui.apply.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceGroupItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceItemModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model.PresetApplyUiState

internal class PresetApplyUiStatePreviewParameterProvider : PreviewParameterProvider<PresetApplyUiState> {
    private val device = DeviceItemModel(
        id = "",
        pciBus = "001",
        type = "GPU",
        model = "RTX 4080 TI",
        manufacturer = "Company",
        minerName = "SuperMiner",
        isOnline = true,
        rigName = "Rig 1",
        presetName = "SimplePreset"
    )

    private val devices = listOf(
        device,
        device
    )

    private val deviceGroups = listOf(
        DeviceGroupItemModel(
            name = "GPU",
            elements = devices
        ),
        DeviceGroupItemModel(
            name = "CPU",
            elements = devices
        )
    )

    private val uiState = PresetApplyUiState(
        presetRigDevicesSupported = listOf(
            DeviceGroupItemModel(
                name = "Rig 1",
                elements = deviceGroups
            ),
            DeviceGroupItemModel(
                name = "Rig 2",
                elements = deviceGroups
            ),
            DeviceGroupItemModel(
                name = "Rig 3",
                elements = deviceGroups
            )
        )
    )

    override val values: Sequence<PresetApplyUiState> = sequenceOf(
        uiState.copy(presetRigDevicesSupported = null),
        uiState.copy(presetRigDevicesSupportedIsLoading = false),
        uiState.copy(
            presetRigDevicesSupportedIsLoading = false,
            presetRigDevicesSupported = null
        )
    )
}