package com.minux.monitoring.core.network.model.management.wallet

data class WalletDto(
    val id: String,
    val name: String,
    val address: String,
    val cryptocurrency: String
)
