package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceGroupItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model.FlightSheetApplyUiState

internal class FlightSheetApplyUiStatePreviewParameterProvider : PreviewParameterProvider<FlightSheetApplyUiState> {
    private val device = DeviceItemModel(
        id = "",
        pciBus = "001",
        type = "GPU",
        model = "RTX 4080 TI",
        manufacturer = "Company",
        minerName = "SuperMiner",
        isOnline = true,
        rigName = "Rig 1",
        flightSheetName = "SimpleFlightSheet"
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

    private val uiState = FlightSheetApplyUiState(
        flightSheetRigDevicesSupported = listOf(
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

    override val values: Sequence<FlightSheetApplyUiState> = sequenceOf(
        uiState.copy(flightSheetRigDevicesSupported = null),
        uiState.copy(flightSheetRigDevicesSupportedIsLoading = false),
        uiState.copy(
            flightSheetRigDevicesSupportedIsLoading = false,
            flightSheetRigDevicesSupported = null
        )
    )
}