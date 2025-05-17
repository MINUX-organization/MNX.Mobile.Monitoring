package com.minux.monitoring.feature.devices.impl.gpu.data.datasource

import com.minux.monitoring.feature.devices.impl.gpu.data.model.GpuDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

internal interface GpuApiService {

    @GET("devices/gpus")
    fun getAllGpus(): Flow<Result<List<GpuDto>>>

    @GET("devices/gpus/unique_names")
    fun getGpuNames(): Flow<Result<List<String>>>
}