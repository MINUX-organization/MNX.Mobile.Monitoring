package com.minux.monitoring.feature.cryptos.impl.pools.data.repository

import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolChangeDto
import com.minux.monitoring.feature.cryptos.api.model.PoolDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolInputDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolRemoveDto
import kotlinx.coroutines.flow.Flow

internal interface PoolRepository {
    fun getAllPools(): Flow<Result<List<PoolDto>>>

    fun addPool(poolInput: PoolInputDto): Flow<Result<PoolDto>>

    fun changePool(poolChange: PoolChangeDto): Flow<Result<PoolDto>>

    fun removePool(poolRemove: PoolRemoveDto): Flow<Result<Unit>>
}