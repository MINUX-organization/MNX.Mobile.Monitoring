package com.minux.monitoring.core.network

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.TransportEnum
import io.reactivex.rxjava3.core.Single

internal object MNXAsyncApiClient {
    private const val BASE_URL = "ws://something.com/hubs/"

    fun getApiClient(endpoint: String): HubConnection {
        return HubConnectionBuilder.create(BASE_URL + endpoint)
            .withAccessTokenProvider(Single.defer { Single.just("") })
            .withTransport(TransportEnum.WEBSOCKETS)
            .build()
    }
}