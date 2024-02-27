package com.minux.monitoring.core.network.model

data class GpusCount(
    val total: Int,
    val amd: Int,
    val nvidia: Int,
    val intel: Int
)