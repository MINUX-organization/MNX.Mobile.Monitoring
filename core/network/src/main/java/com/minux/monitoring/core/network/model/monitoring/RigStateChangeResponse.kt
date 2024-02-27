package com.minux.monitoring.core.network.model.monitoring

data class RigStateChangeResponse(
    val rigId: String,
    val type: String,
    val newData: Any
)