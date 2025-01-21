package com.minux.monitoring.feature.cryptos.impl.common.presentation.model

internal data class CryptocurrencyInputModel(
    val shortName: String = "",
    val fullName: String = "",
    val algorithm: AlgorithmItemModel? = null,
    val isValidationShowed: Boolean = false,
    val isShortNameValid: Boolean = false,
    val isFullNameValid: Boolean = false,
    val isAlgorithmValid: Boolean = false
)