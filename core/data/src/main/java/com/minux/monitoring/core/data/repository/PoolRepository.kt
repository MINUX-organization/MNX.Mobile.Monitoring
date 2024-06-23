package com.minux.monitoring.core.data.repository

import com.minux.monitoring.core.data.model.pool.Pool
import com.minux.monitoring.core.data.model.pool.PoolInputParam
import com.minux.monitoring.core.data.model.pool.PoolRemoveParam
import com.minux.monitoring.core.data.model.pool.PoolUpdateParam
import kotlinx.coroutines.flow.Flow

interface PoolRepository {
    fun getAllPools(): Flow<Result<List<Pool>>>

    fun addPool(param: PoolInputParam): Flow<Result<Pool>>

    fun updatePool(param: PoolUpdateParam): Flow<Result<Pool>>

    fun removePool(param: PoolRemoveParam): Flow<Result<Pool>>
}