package com.minux.monitoring.feature.presets.impl.data.model

import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingDto
import kotlinx.serialization.Serializable

@Serializable
internal class PresetDto(
    val id: String,
    val name: String?,
    val deviceName: String?,
    val overclocking: DeviceOverclockingDto
)