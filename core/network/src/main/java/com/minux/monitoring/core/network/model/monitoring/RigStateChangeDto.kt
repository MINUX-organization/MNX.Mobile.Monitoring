package com.minux.monitoring.core.network.model.monitoring

data class RigStateChangeDto(
    val rigId: String,
    val type: String,
    val newData: Any
)