package com.minux.monitoring.feature.flightsheets.impl.presentation.model.device

internal data class DeviceItemModel(
    val id: String,
    val pciBus: String?,
    val type: String?,
    val model: String?,
    val manufacturer: String?,
    val minerName: String?,
    val isOnline: Boolean,
    val rigName: String?,
    val flightSheetName: String?,
    val isChecked: Boolean = false
)