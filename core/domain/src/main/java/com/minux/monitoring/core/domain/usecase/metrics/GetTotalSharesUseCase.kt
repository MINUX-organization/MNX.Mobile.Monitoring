package com.minux.monitoring.core.domain.usecase.metrics

import com.minux.monitoring.core.domain.model.metrics.Shares
import com.minux.monitoring.core.domain.repository.MetricsRepository
import kotlinx.coroutines.flow.Flow

class GetTotalSharesUseCase(private val metricsRepository: MetricsRepository) {
    operator fun invoke(): Flow<Result<Shares>> {
        return metricsRepository.getTotalShares()
    }
}