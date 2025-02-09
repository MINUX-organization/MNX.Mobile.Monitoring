package com.minux.monitoring.core.network.api

import com.microsoft.signalr.HubConnection
import kotlinx.coroutines.flow.Flow

abstract class WsClient {
    abstract fun createConnection(hubEndpoint: String): Flow<HubConnection>

    inline fun <reified T> onReceive(connection: HubConnection, method: String) =
        onReceive(connection, method, T::class.java)

    @PublishedApi
    internal abstract fun <T> onReceive(
        connection: HubConnection, method: String, param: Class<T>
    ): Flow<Result<T>>

    abstract fun <T> onSend(connection: HubConnection, method: String, data: T): Flow<Result<Unit>>
}