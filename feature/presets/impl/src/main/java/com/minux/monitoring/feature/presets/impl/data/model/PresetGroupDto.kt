package com.minux.monitoring.feature.presets.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class PresetGroupDto(
    val name: String?,
    val presets: List<PresetDto?>
)