package com.minux.monitoring.feature.flightsheets.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.cryptos.api.CryptosProvider
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.compose.binder.BinderBaseApi

interface FlightSheetsDependencies : BaseDependencies {
    val binderBaseApi: BinderBaseApi
    val httpClient: HttpClient
    val cryptosProvider: CryptosProvider
}