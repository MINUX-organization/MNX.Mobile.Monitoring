package com.minux.monitoring.core.domain.usecase.pool

import com.minux.monitoring.core.domain.model.pool.PoolUpdateParam
import com.minux.monitoring.core.domain.repository.PoolRepository

class UpdatePoolUseCase(private val poolRepository: PoolRepository) {
    operator fun invoke(poolUpdateParam: PoolUpdateParam) =
        poolRepository.updatePool(param = poolUpdateParam)
}