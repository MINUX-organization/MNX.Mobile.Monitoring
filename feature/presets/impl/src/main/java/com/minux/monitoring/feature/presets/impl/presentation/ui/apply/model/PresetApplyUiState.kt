package com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model

import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceGroupItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceItemModel

internal data class PresetApplyUiState(
    val presetRigDevicesSupportedIsLoading: Boolean = true,
    val presetRigDevicesSupported: List<DeviceGroupItemModel<DeviceGroupItemModel<DeviceItemModel>>>? = null,
    val presetRigDevicesApplied: Set<String> = emptySet()
)