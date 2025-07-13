package com.minux.monitoring.feature.presets.impl.data.model.device

import kotlinx.serialization.Serializable

@Serializable
internal class DeviceDto(
    val id: String,
    val pciBus: String?,
    val type: String?,
    val model: String?,
    val manufacturer: String?,
    val minerName: String?,
    val isOnline: Boolean,
    val rigName: String?,
    val presetName: String?,
    val flightSheetName: String?,
    val flightSheetConfirmationState: String
)