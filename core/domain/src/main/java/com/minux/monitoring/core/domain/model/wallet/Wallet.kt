package com.minux.monitoring.core.domain.model.wallet

data class Wallet(
    val id: String,
    val name: String,
    val address: String,
    val cryptocurrency: String
)
