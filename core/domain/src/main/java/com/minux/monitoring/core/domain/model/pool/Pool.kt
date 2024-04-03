package com.minux.monitoring.core.domain.model.pool

data class Pool(
    val id: String,
    val domain: String,
    val port: Int,
    val cryptocurrency: String
)
