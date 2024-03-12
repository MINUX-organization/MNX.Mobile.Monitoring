package com.minux.monitoring.core.network.model.monitoring.common

data class CpusCountDto(
    val total: Int,
    val amd: Int,
    val intel: Int
)