package com.minux.monitoring.feature.cryptos.impl.pools.presentation.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

internal data class PoolInputModel(
    val domain: String = "",
    val port: String = "",
    val cryptocurrency: CryptocurrencyItemModel? = null,
    val isValidationShowed: Boolean = false,
    val isDomainAddressValid: Boolean = false,
    val isPortValid: Boolean = false,
    val isCoinValid: Boolean = false
)