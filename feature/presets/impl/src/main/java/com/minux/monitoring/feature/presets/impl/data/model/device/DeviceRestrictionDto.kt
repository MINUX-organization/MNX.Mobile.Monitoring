package com.minux.monitoring.feature.presets.impl.data.model.device

import kotlinx.serialization.Serializable

@Serializable
internal class DeviceRestrictionDto(
    val minimal: Int,
    val maximal: Int,
    val isWritable: Boolean,
    val default: Int?
)