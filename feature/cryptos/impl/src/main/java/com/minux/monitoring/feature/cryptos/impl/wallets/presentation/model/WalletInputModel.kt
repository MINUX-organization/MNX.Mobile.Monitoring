package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

internal data class WalletInputModel(
    val name: String = "",
    val address: String = "",
    val cryptocurrency: CryptocurrencyItemModel? = null,
    val isValidationShowed: Boolean = false,
    val isNameValid: Boolean = false,
    val isAddressValid: Boolean = false,
    val isCoinValid: Boolean = false
)