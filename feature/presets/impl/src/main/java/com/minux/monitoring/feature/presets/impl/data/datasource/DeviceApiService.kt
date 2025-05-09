package com.minux.monitoring.feature.presets.impl.data.datasource

import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceRestrictionsDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface DeviceApiService {

    @GET("/devices/gpus/unique_names")
    fun getGpuNames(): Flow<Result<List<String>>>

    @GET("/devices/gpus/{gpuId}/restrictions")
    fun getGpuRestrictionsById(@Path("gpuId") id: String): Flow<Result<DeviceRestrictionsDto.GpuRestrictionsDto>>

    @GET("/devices/gpus/{gpuName}/restrictions")
    fun getGpuRestrictionsByName(@Path("gpuName") name: String): Flow<Result<DeviceRestrictionsDto.GpuRestrictionsDto>>

    @GET("/devices/overclocking")
    fun getDeviceOverclocking(@Query("deviceId") id: String): Flow<Result<DeviceOverclockingDto>>

    @GET("/devices/overclocking")
    fun setDeviceOverclocking(@Field("deviceId") id: String): Flow<Result<List<String>>>
}