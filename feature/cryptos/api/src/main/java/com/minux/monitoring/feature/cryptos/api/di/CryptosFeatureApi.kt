package com.minux.monitoring.feature.cryptos.api.di

import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface CryptosFeatureApi : BaseApi, BinderBaseApiToLifecycle {
    val cryptosFeatureMediator: CryptosFeatureMediator
}