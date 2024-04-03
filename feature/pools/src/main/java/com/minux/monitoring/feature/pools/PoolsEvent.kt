package com.minux.monitoring.feature.pools

import com.minux.monitoring.core.domain.model.pool.PoolInputParam
import com.minux.monitoring.core.domain.model.pool.PoolRemoveParam
import com.minux.monitoring.core.domain.model.pool.PoolUpdateParam

sealed interface PoolsEvent {
    data class AddPool(val poolInputParam: PoolInputParam) : PoolsEvent

    data class UpdatePool(val poolUpdateParam: PoolUpdateParam) : PoolsEvent

    data class RemovePool(val poolRemoveParam: PoolRemoveParam) : PoolsEvent
}