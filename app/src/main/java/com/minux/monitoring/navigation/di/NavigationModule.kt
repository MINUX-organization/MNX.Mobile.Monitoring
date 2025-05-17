package com.minux.monitoring.navigation.di

import com.minux.monitoring.feature.auth.api.AuthFeatureMediator
import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.feature.devices.api.DevicesFeatureMediator
import com.minux.monitoring.feature.presets.api.PresetsFeatureMediator
import com.minux.monitoring.navigation.AuthFeatureMediatorProxy
import com.minux.monitoring.navigation.CryptosFeatureMediatorProxy
import com.minux.monitoring.navigation.DevicesFeatureMediatorProxy
import com.minux.monitoring.navigation.PresetsFeatureMediatorProxy
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface NavigationModule {

    @Binds
    @Singleton
    fun bindAuthFeatureMediator(
        authFeatureMediatorProxy: AuthFeatureMediatorProxy
    ): AuthFeatureMediator

    @Binds
    @Singleton
    fun bindDevicesFeatureMediator(
        devicesFeatureMediatorProxy: DevicesFeatureMediatorProxy
    ): DevicesFeatureMediator

    @Binds
    @Singleton
    fun bindCryptosFeatureMediator(
        cryptosFeatureMediatorProxy: CryptosFeatureMediatorProxy
    ): CryptosFeatureMediator

    @Binds
    @Singleton
    fun bindPresetsFeatureMediator(
        presetsFeatureMediatorProxy: PresetsFeatureMediatorProxy
    ): PresetsFeatureMediator
}