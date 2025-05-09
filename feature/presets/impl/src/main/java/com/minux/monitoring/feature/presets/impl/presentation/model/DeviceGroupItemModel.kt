package com.minux.monitoring.feature.presets.impl.presentation.model

internal data class DeviceGroupItemModel<GroupElement>(
    val name: String?,
    val elements: List<GroupElement>
)