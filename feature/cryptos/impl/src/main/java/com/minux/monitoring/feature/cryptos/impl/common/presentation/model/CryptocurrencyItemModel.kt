package com.minux.monitoring.feature.cryptos.impl.common.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
internal class CryptocurrencyItemModel(
    val id: String,
    val shortName: String,
    val fullName: String,
    val algorithm: AlgorithmItemModel
) {
    override fun toString(): String = shortName
}