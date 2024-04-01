package com.minux.monitoring.core.domain.usecase.metrics

import com.minux.monitoring.core.domain.repository.MetricsRepository
import kotlinx.coroutines.flow.Flow

class GetTotalRigsCountUseCase(private val metricsRepository: MetricsRepository) {
    operator fun invoke(): Flow<Result<Int>> {
        return metricsRepository.getTotalRigsCount()
    }
}