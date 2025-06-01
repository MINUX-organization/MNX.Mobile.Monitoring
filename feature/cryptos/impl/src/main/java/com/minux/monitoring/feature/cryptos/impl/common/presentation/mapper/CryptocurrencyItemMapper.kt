package com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper

import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyDto
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

internal fun CryptocurrencyDto.toCryptocurrencyItemModel(): CryptocurrencyItemModel {
    return CryptocurrencyItemModel(
        id = id,
        shortName = shortName,
        fullName = fullName,
        algorithm = algorithm?.toAlgorithmItemModel()
    )
}