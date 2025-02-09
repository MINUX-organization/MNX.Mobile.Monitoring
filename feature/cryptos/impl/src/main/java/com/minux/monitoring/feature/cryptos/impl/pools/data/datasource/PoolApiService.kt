package com.minux.monitoring.feature.cryptos.impl.pools.data.datasource

import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolInputDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface PoolApiService {

    @GET("/pools")
    fun getAllPools(): Flow<Result<List<PoolDto>>>

    @POST("/pools")
    fun addPool(@Body input: PoolInputDto): Flow<Result<PoolDto>>

    @PUT("/pools/{id}")
    fun changePool(@Path("id") id: String, @Body input: PoolInputDto): Flow<Result<PoolDto>>

    @DELETE("/pools/{id}")
    fun removePool(@Path("id") id: String): Flow<Result<Unit>>
}