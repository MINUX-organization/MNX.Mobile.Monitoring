package com.minux.monitoring.core.domain.usecase.metrics

import com.minux.monitoring.core.domain.model.metrics.ValueUnit
import com.minux.monitoring.core.domain.repository.MetricsRepository
import kotlinx.coroutines.flow.Flow

class GetTotalPowerUseCase(private val metricsRepository: MetricsRepository) {
    operator fun invoke(): Flow<Result<ValueUnit>> {
        return metricsRepository.getTotalPower()
    }
}