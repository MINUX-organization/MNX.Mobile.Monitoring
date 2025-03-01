package com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper

import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyInputDto
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyInputModel

internal fun CryptocurrencyInputModel.toCryptocurrencyInputDto(): CryptocurrencyInputDto {
    return CryptocurrencyInputDto(
        shortName = shortName,
        fullName = fullName,
        algorithmId = algorithm!!.id
    )
}