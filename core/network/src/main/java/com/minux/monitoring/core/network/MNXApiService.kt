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
  
    @GET("/algorithms/available")
    fun getAvailableAlgorithms(): Flow<Result<List<String>>>

    @GET("/cryptocurrencies")
    fun getAllCryptocurrencies(): Flow<Result<List<CryptocurrencyDto>>>

    @POST("/cryptocurrencies")
    fun addCryptocurrency(@Body cryptocurrencyInputDto: CryptocurrencyInputDto): Flow<Result<CryptocurrencyDto>>

    @DELETE("/cryptocurrencies/{fullName}")
    fun removeCryptocurrency(@Path("fullName") cryptocurrencyFullName: String): Flow<Result<Unit>>

    @GET("/flightSheets")
    fun getAllFlightSheets(): Flow<Result<List<FlightSheetDto>>>

    @POST("/flightSheets")
    fun addFlightSheet(@Body flightSheetInputDto: FlightSheetInputDto): Flow<Result<Unit>>

    @POST("/flightSheets/{id}/apply")
    fun applyFlightSheetToGpus(@Path("id") flightSheetId: String, @Body gpuIds: List<String>): Flow<Result<Unit>>

    @PUT("/flightSheets/{id}")
    fun changeFlightSheet(@Path("id") flightSheetId: String, @Body flightSheetInputDto: FlightSheetInputDto): Flow<Result<Unit>>

    @DELETE("/flightSheets/{id}")
    fun removeFlightSheet(@Path("id") flightSheetId: String): Flow<Result<Unit>>

    @GET("/miners/available")
    fun getAvailableMiners(): Flow<Result<List<String>>>

    @GET("/pools")
    fun getAllPools(): Flow<Result<List<PoolDto>>>

    @POST("/pools")
    fun addPool(@Body poolInputDto: PoolInputDto): Flow<Result<PoolDto>>

    @PUT("/pools/{id}")
    fun updatePool(@Path("id") poolId: String, @Body poolInputDto: PoolInputDto): Flow<Result<PoolDto>>

    @DELETE("/pools/{id}")
    fun removePool(@Path("id") poolId: String): Flow<Result<Unit>>

    @GET("/presets")
    fun getAllPresets(@Query("gpuName") gpuName: String): Flow<Result<List<PresetDto>>>

    @POST("/presets")
    fun savePreset(@Body presetSaveDto: PresetSaveDto): Flow<Result<String>>

    @POST("/presets/{id}/apply")
    fun applyPresetToGpus(@Path("id") presetId: String, @Body gpuIds: List<String>): Flow<Result<Unit>>

    @PUT("/presets/{id}")
    fun changePreset(@Path("id") presetId: String, @Body presetInputDto: PresetInputDto): Flow<Result<Unit>>

    @DELETE("/presets/{id}")
    fun removePreset(@Path("id") presetId: String): Flow<Result<Unit>>

    @GET("/wallets")
    fun getAllWallets(): Flow<Result<List<WalletDto>>>

    @POST("/wallets")
    fun addWallet(@Body walletInputDto: WalletInputDto): Flow<Result<WalletDto>>

    @PUT("/wallets/{id}")
    fun changeWallet(@Path("id") walletId: String, @Body walletInputDto: WalletInputDto): Flow<Result<WalletDto>>

    @DELETE("/wallets/{id}")
    fun removeWallet(@Path("id") walletId: String): Flow<Result<Unit>>
}