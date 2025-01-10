package com.minux.monitoring.feature.cryptos.impl.presentation.model

import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyDto

internal class CryptocurrencyItemModel(
    val id: String,
    val shortName: String,
    val fullName: String,
    val algorithm: AlgorithmItemModel
) {
    override fun toString(): String = shortName
}

internal fun CryptocurrencyDto.toCryptocurrencyItemModel(): CryptocurrencyItemModel {
    return CryptocurrencyItemModel(
        id = id,
        shortName = shortName,
        fullName = fullName,
        algorithm = algorithm.toAlgorithmItemModel()
    )
}