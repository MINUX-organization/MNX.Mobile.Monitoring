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
    @GET("/rig/{id}/gpu")
    fun getRigGpusInformation(@Path("id") rigId: String): Flow<Result<List<GpuDto>>>

    @GET("/rig/{id}/cpu")
    fun getRigCpusInformation(@Path("id") rigId: String): Flow<Result<List<CpuDto>>>

    @GET("/rig/{id}/motherboard")
    fun getRigMotherboardInformation(@Path("id") rigId: String): Flow<Result<MotherboardDto>>

    @GET("/rig/{id}/hdd")
    fun getRigHddsInformation(@Path("id") rigId: String): Flow<Result<List<HddDto>>>

    @GET("/rig/{id}/internet")
    fun getRigInternetConnectionInformation(@Path("id") rigId: String): Flow<Result<InternetConnectionDto>>
}