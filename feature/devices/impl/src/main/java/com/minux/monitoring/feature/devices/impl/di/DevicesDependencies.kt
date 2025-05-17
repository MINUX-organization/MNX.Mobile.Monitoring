package com.minux.monitoring.feature.devices.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.WsClient
import com.minux.monitoring.feature.presets.api.PresetsFeatureMediator
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.compose.binder.BinderBaseApi

interface DevicesDependencies : BaseDependencies {
    val binderBaseApi: BinderBaseApi
    val httpClient: HttpClient
    val wsClient: WsClient
    val presetsFeatureMediator: PresetsFeatureMediator
}