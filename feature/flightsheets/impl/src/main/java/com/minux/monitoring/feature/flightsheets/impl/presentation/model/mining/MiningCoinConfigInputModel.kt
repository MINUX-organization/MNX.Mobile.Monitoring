package com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining

internal data class MiningCoinConfigInputModel(
    val selectedPool: MiningPoolModel? = null,
    val selectedWallet: MiningWalletModel? = null,
    val poolPassword: String? = null,
    val isPoolValid: Boolean = false,
    val isWalletValid: Boolean = false
)