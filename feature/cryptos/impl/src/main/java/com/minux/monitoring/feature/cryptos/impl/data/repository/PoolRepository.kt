package com.minux.monitoring.feature.cryptos.impl.data.repository

import com.minux.monitoring.feature.cryptos.impl.data.model.pool.PoolChangeDto
import com.minux.monitoring.feature.cryptos.impl.data.model.pool.PoolDto
import com.minux.monitoring.feature.cryptos.impl.data.model.pool.PoolInputDto
import com.minux.monitoring.feature.cryptos.impl.data.model.pool.PoolRemoveDto
import kotlinx.coroutines.flow.Flow

internal interface PoolRepository {
    fun getAllPools(): Flow<Result<List<PoolDto>>>

    fun addPool(poolInput: PoolInputDto): Flow<Result<PoolDto>>

    fun changePool(poolChange: PoolChangeDto): Flow<Result<PoolDto>>

    fun removePool(poolRemove: PoolRemoveDto): Flow<Result<Unit>>
}