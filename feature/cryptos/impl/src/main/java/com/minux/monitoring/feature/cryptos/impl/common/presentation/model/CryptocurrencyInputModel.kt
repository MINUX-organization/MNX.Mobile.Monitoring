package com.minux.monitoring.feature.cryptos.impl.common.presentation.model

internal data class CryptocurrencyInputModel(
    val id: String = "",
    val shortName: String? = null,
    val fullName: String? = null,
    val selectedAlgorithm: AlgorithmItemModel? = null,
    val isShortNameValidationShowed: Boolean = false,
    val isShortNameValid: Boolean = false,
    val isFullNameValidationShowed: Boolean = false,
    val isFullNameValid: Boolean = false,
    val isAlgorithmValid: Boolean = false
)