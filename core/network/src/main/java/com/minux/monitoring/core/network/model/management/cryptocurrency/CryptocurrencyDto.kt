package com.minux.monitoring.core.network.model.management.cryptocurrency

data class CryptocurrencyDto(
    val id: Int,
    val shortName: String,
    val fullName: String,
    val algorithm: String
)
