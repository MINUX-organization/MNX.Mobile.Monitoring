package com.minux.monitoring.feature.cryptos.impl.common.data.datasource

import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyDto
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyInputDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface CryptocurrencyApiService {

    @GET("/cryptocurrencies")
    fun getAllCryptocurrencies(): Flow<Result<List<CryptocurrencyDto>>>

    @POST("/cryptocurrencies")
    fun addCryptocurrency(
        @Body cryptocurrencyInputDto: CryptocurrencyInputDto
    ): Flow<Result<CryptocurrencyDto>>

    @DELETE("/cryptocurrencies/{id}")
    fun removeCryptocurrency(@Path("id") id: String): Flow<Result<Unit>>
}