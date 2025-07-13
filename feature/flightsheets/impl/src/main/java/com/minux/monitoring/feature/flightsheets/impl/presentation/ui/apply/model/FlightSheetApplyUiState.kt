package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.model

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceGroupItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceItemModel

internal data class FlightSheetApplyUiState(
    val flightSheetRigDevicesSupportedIsLoading: Boolean = true,
    val flightSheetRigDevicesSupported: List<DeviceGroupItemModel<DeviceGroupItemModel<DeviceItemModel>>>? = null,
    val flightSheetRigDevicesApplied: Set<String> = emptySet()
)