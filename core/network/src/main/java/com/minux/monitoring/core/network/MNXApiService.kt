package com.minux.monitoring.core.network

import com.minux.monitoring.core.network.model.monitoring.hardware.cpu.CpuDto
import com.minux.monitoring.core.network.model.monitoring.hardware.gpu.GpuDto
import com.minux.monitoring.core.network.model.monitoring.hardware.hdd.HddDto
import com.minux.monitoring.core.network.model.monitoring.hardware.internet.InternetConnectionDto
import com.minux.monitoring.core.network.model.monitoring.hardware.motherboard.MotherboardDto
import com.minux.monitoring.core.network.model.management.cryptocurrency.CryptocurrencyDto
import com.minux.monitoring.core.network.model.management.cryptocurrency.CryptocurrencyInputDto
import com.minux.monitoring.core.network.model.management.flightsheet.FlightSheetDto
import com.minux.monitoring.core.network.model.management.flightsheet.FlightSheetInputDto
import com.minux.monitoring.core.network.model.management.pool.PoolDto
import com.minux.monitoring.core.network.model.management.pool.PoolInputDto
import com.minux.monitoring.core.network.model.management.preset.PresetDto
import com.minux.monitoring.core.network.model.management.preset.PresetInputDto
import com.minux.monitoring.core.network.model.management.preset.PresetSaveDto
import com.minux.monitoring.core.network.model.management.wallet.WalletDto
import com.minux.monitoring.core.network.model.management.wallet.WalletInputDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

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
  
    @GET("/algorithm/available")
    fun getAvailableAlgorithms(): Flow<Result<List<String>>>

    @GET("/cryptocurrency")
    fun getAllCryptocurrencies(): Flow<Result<List<CryptocurrencyDto>>>

    @POST("/cryptocurrency")
    fun addCryptocurrency(@Body cryptocurrencyInputDto: CryptocurrencyInputDto): Flow<Result<List<CryptocurrencyDto>>>

    @DELETE("/cryptocurrency/{fullName}")
    fun removeCryptocurrency(@Path("fullName") cryptocurrencyFullName: String): Flow<Result<Unit>>

    @GET("/flightSheet")
    fun getAllFlightSheets(): Flow<Result<List<FlightSheetDto>>>

    @POST("/flightSheet")
    fun addFlightSheet(@Body flightSheetInputDto: FlightSheetInputDto): Flow<Result<Unit>>

    @POST("/flightSheet/{id}/apply")
    fun applyFlightSheetToGpus(@Path("id") flightSheetId: String, @Body gpuIds: List<String>): Flow<Result<Unit>>

    @PUT("/flightSheet/{id}")
    fun changeFlightSheet(@Path("id") flightSheetId: String, @Body flightSheetInputDto: FlightSheetInputDto): Flow<Result<Unit>>

    @DELETE("/flightSheet/{id}")
    fun removeFlightSheet(@Path("id") flightSheetId: String): Flow<Result<Unit>>

    @GET("/miner/available")
    fun getAvailableMiners(): Flow<Result<List<String>>>

    @POST("/mining/run")
    fun startMining(@Body rigIds: List<String>): Flow<Result<Unit>>

    @POST("/mining/stop")
    fun stopMining(@Body rigIds: List<String>): Flow<Result<Unit>>

    @POST("/mining/powerOff")
    fun powerOffRig(@Body rigIds: List<String>): Flow<Result<Unit>>

    @POST("/mining/reboot")
    fun rebootRig(@Body rigIds: List<String>): Flow<Result<Unit>>

    @GET("/pool")
    fun getAllPools(): Flow<Result<List<PoolDto>>>

    @POST("/pool")
    fun addPool(@Body poolInputDto: PoolInputDto): Flow<Result<PoolDto>>

    @PUT("/pool/{id}")
    fun updatePool(@Path("id") poolId: String, @Body poolInputDto: PoolInputDto): Flow<Result<PoolDto>>

    @DELETE("/pool/{id}")
    fun removePool(@Path("id") poolId: String): Flow<Result<Unit>>

    @GET("/preset")
    fun getAllPresets(@Query("gpuName") gpuName: String): Flow<Result<List<PresetDto>>>

    @POST("/preset")
    fun savePreset(@Body presetSaveDto: PresetSaveDto): Flow<Result<String>>

    @POST("/preset/{id}/apply")
    fun applyPresetToGpus(@Path("id") presetId: String, @Body gpuIds: List<String>): Flow<Result<Unit>>

    @PUT("/preset/{id}")
    fun changePreset(@Path("id") presetId: String, @Body presetInputDto: PresetInputDto): Flow<Result<Unit>>

    @DELETE("/preset/{id}")
    fun removePreset(@Path("id") presetId: String): Flow<Result<Unit>>

    @GET("/wallet")
    fun getAllWallets(): Flow<Result<List<WalletDto>>>

    @POST("/wallet")
    fun addWallet(@Body walletInputDto: WalletInputDto): Flow<Result<WalletDto>>

    @PUT("/wallet/{id}")
    fun changeWallet(@Path("id") walletId: String, @Body walletInputDto: WalletInputDto): Flow<Result<WalletDto>>

    @DELETE("/wallet/{id}")
    fun removeWallet(@Path("id") walletId: String): Flow<Result<Unit>>
}