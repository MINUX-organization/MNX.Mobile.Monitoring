package com.minux.monitoring.feature.flightsheets.impl.data.model.mining

import kotlinx.serialization.Serializable

@Serializable
internal class MiningPoolDto(
    val id: String,
    val userId: String? = null,
    val tls: Boolean,
    val domain: String?,
    val port: Int,
    val cryptocurrencyId: String,
    val cryptocurrency: String?
)