package com.minux.monitoring.feature.flightsheets.impl.presentation.model.device

internal data class DeviceGroupItemModel<GroupElement>(
    val name: String?,
    val elements: List<GroupElement>
)