package com.minux.monitoring.core.domain.usecase.pool

import com.minux.monitoring.core.domain.model.pool.PoolRemoveParam
import com.minux.monitoring.core.domain.repository.PoolRepository

class RemovePoolUseCase(private val poolRepository: PoolRepository) {
    operator fun invoke(poolRemoveParam: PoolRemoveParam) =
        poolRepository.removePool(param = poolRemoveParam)
}