package com.minux.monitoring.feature.cryptos.impl.pools.data

import com.minux.monitoring.feature.cryptos.impl.pools.data.datasource.PoolApiService
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolChangeDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolInputDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolRemoveDto
import kotlinx.coroutines.flow.Flow

internal class PoolRepositoryImpl(private val poolApiService: PoolApiService) : PoolRepository {

    override fun getAllPools(): Flow<Result<List<PoolDto>>> {
        return poolApiService.getAllPools()
    }

    override fun addPool(poolInput: PoolInputDto): Flow<Result<PoolDto>> {
        return poolApiService.addPool(input = poolInput)
    }

    override fun changePool(poolChange: PoolChangeDto): Flow<Result<PoolDto>> {
        return poolApiService.changePool(
            id = poolChange.id,
            input = poolChange.pool
        )
    }

    override fun removePool(poolRemove: PoolRemoveDto): Flow<Result<Unit>> {
        return poolApiService.removePool(id = poolRemove.id)
    }
}