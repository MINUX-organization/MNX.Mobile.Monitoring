package com.minux.monitoring.feature.cryptos.impl.pools.presentation.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

internal data class PoolInputModel(
    val id: String = "",
    val tls: Boolean = false,
    val domain: String? = null,
    val port: String? = null,
    val selectedCryptocurrency: CryptocurrencyItemModel? = null,
    val isDomainAddressValidationShowed: Boolean = false,
    val isDomainAddressValid: Boolean = false,
    val isPortValidationShowed: Boolean = false,
    val isPortValid: Boolean = false,
    val isCoinValid: Boolean = false
)