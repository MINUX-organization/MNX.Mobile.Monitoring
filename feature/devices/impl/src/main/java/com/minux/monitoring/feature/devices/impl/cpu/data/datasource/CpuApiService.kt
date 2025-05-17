package com.minux.monitoring.feature.devices.impl.cpu.data.datasource

import com.minux.monitoring.feature.devices.impl.cpu.data.model.CpuDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

internal interface CpuApiService {

    @GET("devices/cpus")
    fun getAllCpus(): Flow<Result<List<CpuDto>>>
}