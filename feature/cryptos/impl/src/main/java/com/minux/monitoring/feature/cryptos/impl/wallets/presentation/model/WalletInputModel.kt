package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

internal data class WalletInputModel(
    val id: String = "",
    val name: String? = null,
    val address: String? = null,
    val selectedCryptocurrency: CryptocurrencyItemModel? = null,
    val isNameValidationShowed: Boolean = false,
    val isNameValid: Boolean = false,
    val isAddressValidationShowed: Boolean = false,
    val isAddressValid: Boolean = false,
    val isCoinValid: Boolean = false
)