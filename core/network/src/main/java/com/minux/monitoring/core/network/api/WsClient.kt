package com.minux.monitoring.core.network.api

import com.microsoft.signalr.HubConnection
import com.minux.monitoring.core.network.api.hub.BackendHub

interface WsClient {
    fun createConnection(hub: BackendHub): HubConnection
}