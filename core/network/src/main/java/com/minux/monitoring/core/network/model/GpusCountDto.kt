package com.minux.monitoring.core.network.model

data class GpusCountDto(
    val total: Int,
    val amd: Int,
    val nvidia: Int,
    val intel: Int
)