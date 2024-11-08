package com.minux.monitoring.core.network.impl

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.TransportEnum
import com.minux.monitoring.core.network.api.WsClient
import com.minux.monitoring.core.network.impl.signalr.onReceive
import com.minux.monitoring.core.network.impl.signalr.onSend
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

internal class WsClientImpl : WsClient() {
    private val baseUrl = "ws://something.com/hubs/"

    override fun createConnection(hubEndpoint: String, accessToken: String): HubConnection {
        return HubConnectionBuilder.create(baseUrl + hubEndpoint)
            .withAccessTokenProvider(Single.defer { Single.just(accessToken) })
            .withTransport(TransportEnum.WEBSOCKETS)
            .build()
    }

    override fun <T> onReceive(connection: HubConnection, method: String, param: Class<T>): Flow<Result<T>> {
        return connection.onReceive(method, param)
    }

    override fun <T> onSend(connection: HubConnection, method: String, data: T): Flow<Result<Unit>> {
        return connection.onSend(method, data)
    }
}