package com.minux.monitoring.core.network.model

data class CpusCountDto(
    val total: Int,
    val amd: Int,
    val intel: Int
)