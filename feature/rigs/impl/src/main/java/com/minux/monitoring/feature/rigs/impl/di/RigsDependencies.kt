package com.minux.monitoring.feature.rigs.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.compose.binder.BinderBaseApi

interface RigsDependencies : BaseDependencies {
    val binderBaseApi: BinderBaseApi
    val httpClient: HttpClient
}