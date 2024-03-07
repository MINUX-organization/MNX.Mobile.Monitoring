package com.minux.monitoring.core.network

import com.minux.monitoring.core.network.model.management.cryptocurrency.CryptocurrencyDto
import com.minux.monitoring.core.network.model.management.flightsheet.FlightSheetDto
import com.minux.monitoring.core.network.model.management.flightsheet.FlightSheetInputDto
import com.minux.monitoring.core.network.model.management.pool.PoolDto
import com.minux.monitoring.core.network.model.management.pool.PoolInputDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MNXApiService {
    @GET("/algorithm/available")
    fun getAvailableAlgorithms(): Flow<Result<List<String>>>

    @GET("/cryptocurrency")
    fun getAllCryptocurrencies(): Flow<Result<List<CryptocurrencyDto>>>

    @POST("/cryptocurrency")
    fun addCryptocurrency(@Body cryptocurrencyDto: CryptocurrencyDto): Flow<Result<List<CryptocurrencyDto>>>

    @DELETE("/cryptocurrency/{fullName}")
    fun removeCryptocurrency(@Path("fullName") fullName: String): Flow<Result<Unit>>

    @GET("/flightSheet")
    fun getAllFlightSheets(): Flow<Result<List<FlightSheetDto>>>

    @POST("/flightSheet")
    fun addFlightSheet(@Body flightSheetInputDto: FlightSheetInputDto): Flow<Result<Unit>>

    @POST("/flightSheet/{id}/apply")
    fun applyFlightSheetToGpus(@Path("id") flightSheetId: String, @Body list: List<String>): Flow<Result<Unit>>

    @PUT("/flightSheet/{id}")
    fun changeFlightSheet(@Path("id") flightSheetId: String, @Body flightSheetInputDto: FlightSheetInputDto): Flow<Result<Unit>>

    @DELETE("/flightSheet/{id}")
    fun removeFlightSheet(@Path("id") flightSheetId: String): Flow<Result<Unit>>

    @GET("/pool")
    fun getAllPools(): Flow<Result<List<PoolDto>>>

    @POST("/pool")
    fun addPool(@Body poolInputDto: PoolInputDto): Flow<Result<String>>

    @PUT("/pool/{id}")
    fun updatePool(@Path("id") poolId: String, @Body poolInputDto: PoolInputDto): Flow<Result<Unit>>

    @DELETE("/pool/{id}")
    fun removePool(@Path("id") poolId: String): Flow<Result<Unit>>
}