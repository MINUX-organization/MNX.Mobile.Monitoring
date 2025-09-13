package com.minux.monitoring.feature.rigs.impl.data.datasource

import com.minux.monitoring.feature.rigs.impl.data.model.RigDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

internal interface RigApiService {

    @GET("rigs")
    fun getAllRigs(): Flow<Result<List<RigDto>>>
}