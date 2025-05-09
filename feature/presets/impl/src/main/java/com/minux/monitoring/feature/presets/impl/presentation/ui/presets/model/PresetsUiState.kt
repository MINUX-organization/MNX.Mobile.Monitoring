package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model

import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetGroupItemModel

internal data class PresetsUiState(
    val presetGroupsIsLoading: Boolean = true,
    val presetGroups: List<DevicePresetGroupItemModel>? = null,
    val searchQuery: String = "",
    val filteredPresetGroups: List<DevicePresetGroupItemModel>? = null
)