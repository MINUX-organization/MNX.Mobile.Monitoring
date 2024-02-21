package com.minux.monitoring.core.network

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.TransportEnum

object MNXApiClient {
    private const val baseUrl = "https://something.com/api/"

    fun getApiClient(apiEndpoint: String): HubConnection {
        return HubConnectionBuilder.create(baseUrl + apiEndpoint)
            .withTransport(TransportEnum.WEBSOCKETS)
            .build()
    }
}