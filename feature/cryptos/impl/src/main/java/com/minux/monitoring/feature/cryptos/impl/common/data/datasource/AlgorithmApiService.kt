package com.minux.monitoring.feature.cryptos.impl.common.data.datasource

import com.minux.monitoring.feature.cryptos.impl.common.data.model.AlgorithmDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

internal interface AlgorithmApiService {

    @GET("/algorithms/available")
    fun getAvailableAlgorithms(): Flow<Result<List<AlgorithmDto>>>
}