package com.minux.monitoring.feature.cryptos.impl.pools.presentation.mapper

import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolInputDto
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolInputModel

internal fun PoolInputModel.toPoolInputDto(): PoolInputDto {
    return PoolInputDto(
        tls = tls,
        domain = domain,
        port = port!!.toInt(),
        cryptocurrencyId = selectedCryptocurrency!!.id
    )
}