package com.minux.monitoring.feature.cryptos.impl.presentation.model

import com.minux.monitoring.feature.cryptos.impl.data.model.pool.PoolInputDto

internal class PoolInputModel(
    val domain: String,
    val port: Int,
    val cryptocurrencyId: String
)

internal fun PoolInputModel.toPoolInputDto(): PoolInputDto {
    return PoolInputDto(
        domain = domain,
        port = port,
        cryptocurrencyId = cryptocurrencyId
    )
}