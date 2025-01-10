package com.minux.monitoring.feature.cryptos.impl.data.datasource

import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletDto
import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletInputDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface WalletApiService {

    @GET("/wallets")
    fun getAllWallets(): Flow<Result<List<WalletDto>>>

    @POST("/wallets")
    fun addWallet(@Body input: WalletInputDto): Flow<Result<WalletDto>>

    @PUT("/wallets/{id}")
    fun changeWallet(@Path("id") id: String, @Body input: WalletInputDto): Flow<Result<WalletDto>>

    @DELETE("/wallets/{id}")
    fun removeWallet(@Path("id") id: String): Flow<Result<Unit>>
}