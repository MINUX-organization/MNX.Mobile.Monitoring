package com.minux.monitoring.core.network

import com.minux.monitoring.core.network.api.Receive
import com.minux.monitoring.core.network.api.Send
import kotlinx.coroutines.flow.Flow

interface MNXApiService {
    @Receive("GetCryptoCoins")
    fun getAllCryptoCoins(): Flow<String>

    @Send("SendUserLogin")
    fun sendUserLogin(data: String)
}