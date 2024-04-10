package com.minux.monitoring.core.domain.usecase.pool

import com.minux.monitoring.core.domain.model.pool.Pool
import com.minux.monitoring.core.domain.repository.PoolRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPoolsUseCase @Inject constructor(private val poolRepository: PoolRepository) {
    operator fun invoke(): Flow<Result<List<Pool>>> {
        return poolRepository.getAllPools()
    }
}