package com.minux.monitoring.core.network.impl

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.TransportEnum
import com.minux.monitoring.core.network.BuildConfig
import com.minux.monitoring.core.network.api.WsClient
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.impl.session.SessionManagerImpl
import com.minux.monitoring.core.network.impl.signalr.onReceive
import com.minux.monitoring.core.network.impl.signalr.onSend
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

internal class WsClientImpl(sessionManager: SessionManager) : WsClient() {

    private val _accessToken = (sessionManager as SessionManagerImpl).getAccessToken()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun createConnection(hubEndpoint: String): Flow<HubConnection> = _accessToken.mapLatest {
        return@mapLatest HubConnectionBuilder.create(BuildConfig.API_BASE_URL + hubEndpoint)
            .withAccessTokenProvider(Single.defer { Single.just(it) })
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