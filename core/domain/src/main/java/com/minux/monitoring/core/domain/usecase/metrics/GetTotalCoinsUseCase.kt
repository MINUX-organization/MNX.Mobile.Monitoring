package com.minux.monitoring.core.domain.usecase.metrics

import com.minux.monitoring.core.domain.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.domain.repository.MetricsRepository
import kotlinx.coroutines.flow.Flow

class GetTotalCoinsUseCase(private val metricsRepository: MetricsRepository) {
    operator fun invoke(): Flow<Result<List<CoinStatisticsDetail>>> {
        return metricsRepository.getTotalCoins()
    }
}