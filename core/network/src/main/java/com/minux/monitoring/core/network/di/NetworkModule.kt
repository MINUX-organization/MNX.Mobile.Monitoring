package com.minux.monitoring.core.network.di

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.TransportEnum
import com.minux.monitoring.core.network.MNXApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private const val apiUrl = "https://something.com/api/"

    @Provides
    @Singleton
    fun provideHubConnection(): HubConnection {
        return HubConnectionBuilder.create(apiUrl + "GateWayHub")
            .withTransport(TransportEnum.WEBSOCKETS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMNXApiClient(hubConnection: HubConnection): MNXApiClient {
        return MNXApiClient(hubConnection = hubConnection)
    }
}