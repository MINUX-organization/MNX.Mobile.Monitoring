package com.minux.monitoring.feature.flightsheets.impl.data.model.device

import kotlinx.serialization.Serializable

@Serializable
internal class DeviceGroupDto<Element>(
    val name: String?,
    val elements: List<Element?>
)