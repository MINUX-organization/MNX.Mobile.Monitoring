package com.minux.monitoring.feature.cryptos.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.compose.binder.BinderBaseApi

interface CryptosDependencies : BaseDependencies {
    val binderBaseApi: BinderBaseApi
    val httpClient: HttpClient
}