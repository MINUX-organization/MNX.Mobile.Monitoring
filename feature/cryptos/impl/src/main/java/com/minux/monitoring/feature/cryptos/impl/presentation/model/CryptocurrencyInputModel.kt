package com.minux.monitoring.feature.cryptos.impl.presentation.model

import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyInputDto

internal class CryptocurrencyInputModel(
    val shortName: String,
    val fullName: String,
    val algorithmId: String
)

internal fun CryptocurrencyInputModel.toCryptocurrencyInputDto(): CryptocurrencyInputDto {
    return CryptocurrencyInputDto(
        shortName = shortName,
        fullName = fullName,
        algorithmId = algorithmId
    )
}