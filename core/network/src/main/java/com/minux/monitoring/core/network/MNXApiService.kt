package com.minux.monitoring.core.network

import com.minux.monitoring.core.network.model.monitoring.hardware.cpu.CpuDto
import com.minux.monitoring.core.network.model.monitoring.hardware.gpu.GpuDto
import com.minux.monitoring.core.network.model.monitoring.hardware.hdd.HddDto
import com.minux.monitoring.core.network.model.monitoring.hardware.internet.InternetConnectionDto
import com.minux.monitoring.core.network.model.monitoring.hardware.motherboard.MotherboardDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface MNXApiService {
    @GET("/rigs/{id}/gpu")
    fun getRigGpusInformation(@Path("id") rigId: String): Flow<Result<List<GpuDto>>>

    @GET("/rigs/{id}/cpu")
    fun getRigCpusInformation(@Path("id") rigId: String): Flow<Result<List<CpuDto>>>

    @GET("/rigs/{id}/motherboard")
    fun getRigMotherboardInformation(@Path("id") rigId: String): Flow<Result<MotherboardDto>>

    @GET("/rigs/{id}/hdd")
    fun getRigHardDrivesInformation(@Path("id") rigId: String): Flow<Result<List<HddDto>>>

    @GET("/rigs/{id}/internet")
    fun getRigInternetConnectionInformation(
        @Path("id") rigId: String
    ): Flow<Result<InternetConnectionDto>>
}