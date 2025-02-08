package com.minux.monitoring.navigation.di

import com.minux.monitoring.feature.auth.api.AuthFeatureMediator
import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.injector.BaseApi

interface NavigationApi : BaseApi {
    val authFeatureMediator: AuthFeatureMediator
    val cryptosFeatureMediator: CryptosFeatureMediator
}