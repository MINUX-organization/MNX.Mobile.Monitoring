package com.minux.monitoring.feature.presets.impl.data.model

import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingDto
import kotlinx.serialization.Serializable

@Serializable
internal class PresetChangeInputDto(
    val name: String?,
    val overclocking: DeviceOverclockingDto?
)