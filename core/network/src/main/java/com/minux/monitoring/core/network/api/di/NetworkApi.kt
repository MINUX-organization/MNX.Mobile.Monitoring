package com.minux.monitoring.core.network.api.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.WsClient

interface NetworkApi {
    val httpClient: HttpClient
    val wsClient: WsClient
}