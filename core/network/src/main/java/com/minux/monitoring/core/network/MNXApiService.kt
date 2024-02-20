package com.minux.monitoring.core.network

import com.minux.monitoring.core.network.api.Receive
import com.minux.monitoring.core.network.api.Send
import kotlinx.coroutines.flow.Flow

interface MNXApiService {
    @Receive("GetCryptoCoins", String::class)
    fun getAllCryptoCoins(): Flow<Result<String>>

    @Send("SendUserLogin")
    fun sendUserLogin(data: String): Flow<Result<Unit>>
}