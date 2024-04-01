package com.minux.monitoring.core.domain.model.cryptocurrency

data class Cryptocurrency(
    val id: String,
    val shortName: String,
    val fullName: String,
    val algorithm: String
)
