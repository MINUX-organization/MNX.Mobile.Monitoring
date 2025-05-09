package com.minux.monitoring.feature.presets.impl.presentation.model

internal data class DeviceItemModel(
    val id: String,
    val pciBus: String?,
    val type: String?,
    val model: String?,
    val manufacturer: String?,
    val minerName: String?,
    val isOnline: Boolean,
    val rigName: String?,
    val presetName: String?,
    val isChecked: Boolean = false
)