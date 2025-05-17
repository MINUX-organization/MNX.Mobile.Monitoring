package com.minux.monitoring.navigation.di

import com.minux.monitoring.feature.auth.api.AuthFeatureMediator
import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.feature.devices.api.DevicesFeatureMediator
import com.minux.monitoring.feature.presets.api.PresetsFeatureMediator
import com.minux.monitoring.injector.BaseApi

interface NavigationApi : BaseApi {
    val authFeatureMediator: AuthFeatureMediator
    val devicesFeatureMediator: DevicesFeatureMediator
    val cryptosFeatureMediator: CryptosFeatureMediator
    val presetsFeatureMediator: PresetsFeatureMediator
}