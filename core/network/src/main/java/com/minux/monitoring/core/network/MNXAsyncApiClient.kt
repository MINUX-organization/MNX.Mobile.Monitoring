package com.minux.monitoring.core.network

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.TransportEnum
import io.reactivex.rxjava3.core.Single

object MNXAsyncApiClient {
    private const val baseUrl = "ws://something.com/api/"

    fun getApiClient(apiEndpoint: String): HubConnection {
        return HubConnectionBuilder.create(baseUrl + apiEndpoint)
            .withAccessTokenProvider(Single.defer { Single.just("") })
            .withTransport(TransportEnum.WEBSOCKETS)
            .build()
    }
}