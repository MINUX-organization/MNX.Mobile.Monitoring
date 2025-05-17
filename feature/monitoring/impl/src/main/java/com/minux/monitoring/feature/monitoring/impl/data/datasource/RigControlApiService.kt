package com.minux.monitoring.feature.monitoring.impl.data.datasource

import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST
import retrofit2.http.Path

internal interface RigControlApiService {

    @POST("rigs/{rigId}/power_off")
    fun powerOffRig(@Path("rigId") id: String): Flow<Result<Unit>>

    @POST("rigs/{rigId}/reboot")
    fun rebootRig(@Path("rigId") id: String): Flow<Result<Unit>>

    @POST("rigs/{rigId}/mining/start")
    fun startMiningOnRig(@Path("rigId") id: String): Flow<Result<Unit>>

    @POST("rigs/{rigId}/mining/stop")
    fun stopMiningOnRig(@Path("rigId") id: String): Flow<Result<Unit>>
}