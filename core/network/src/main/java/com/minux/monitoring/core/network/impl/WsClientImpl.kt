package com.minux.monitoring.core.network.impl

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.TransportEnum
import com.minux.monitoring.core.network.BuildConfig
import com.minux.monitoring.core.network.api.WsClient
import com.minux.monitoring.core.network.api.hub.BackendHub
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.impl.session.SessionManagerImpl
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class WsClientImpl(sessionManager: SessionManager) : WsClient {

    private var _accessToken = ""

    init {
        CoroutineScope(Dispatchers.IO).launch {
            (sessionManager as SessionManagerImpl)
                .getAccessToken()
                .collect { _accessToken = it }
        }
    }

    override fun createConnection(hub: BackendHub): HubConnection {
        return HubConnectionBuilder.create(BuildConfig.BACKEND_URL + hub.value)
            .withAccessTokenProvider( Single.defer { Single.just(_accessToken) })
            .withTransport(TransportEnum.WEBSOCKETS)
            .build()
    }
}