package com.minux.monitoring.core.domain.usecase.pool

import com.minux.monitoring.core.domain.model.pool.PoolInputParam
import com.minux.monitoring.core.domain.repository.PoolRepository

class AddPoolUseCase(private val poolRepository: PoolRepository) {
    operator fun invoke(poolInputParam: PoolInputParam) =
        poolRepository.addPool(param = poolInputParam)
}