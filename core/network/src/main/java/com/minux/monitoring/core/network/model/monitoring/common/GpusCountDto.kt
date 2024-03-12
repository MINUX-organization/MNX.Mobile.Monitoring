package com.minux.monitoring.core.network.model.monitoring.common

data class GpusCountDto(
    val total: Int,
    val amd: Int,
    val nvidia: Int,
    val intel: Int
)