package com.minux.monitoring.core.network.model

data class CpusCount(
    val total: Int,
    val amd: Int,
    val intel: Int
)