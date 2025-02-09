package com.minux.monitoring.feature.monitoring.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.compose.binder.BinderBaseApi

interface MonitoringDependencies : BaseDependencies {
    val binderBaseApi: BinderBaseApi
    val httpClient: HttpClient
}