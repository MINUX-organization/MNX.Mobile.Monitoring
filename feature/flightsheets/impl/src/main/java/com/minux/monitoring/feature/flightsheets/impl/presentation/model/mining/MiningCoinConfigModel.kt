package com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining

internal class MiningCoinConfigModel(
    val pool: MiningPoolModel? = null,
    val wallet: MiningWalletModel? = null,
    val poolPassword: String? = null
)