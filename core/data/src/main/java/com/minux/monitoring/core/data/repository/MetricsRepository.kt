package com.minux.monitoring.core.data.repository

import com.minux.monitoring.core.data.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.data.model.metrics.Shares
import com.minux.monitoring.core.data.model.metrics.ValueUnit
import kotlinx.coroutines.flow.Flow

interface MetricsRepository {
    fun getTotalShares(): Flow<Result<Shares>>

    fun getTotalPower(): Flow<Result<ValueUnit>>

    fun getTotalRigsCount(): Flow<Result<Int>>

    fun getTotalCoins(): Flow<Result<List<CoinStatisticsDetail>>>
}