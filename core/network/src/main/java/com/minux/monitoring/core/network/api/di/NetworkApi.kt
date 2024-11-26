package com.minux.monitoring.core.network.api.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.WsClient
import com.minux.monitoring.injector.BaseApi

interface NetworkApi : BaseApi {
    val httpClient: HttpClient
    val wsClient: WsClient
}