package com.minux.monitoring.feature.cryptos.impl.di

import com.minux.monitoring.feature.cryptos.api.di.CryptosFeatureApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object CryptosComponentHolder : ComponentHolder<CryptosFeatureApi, CryptosDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            CryptosFeatureApi, CryptosDependencies, CryptosComponent> { cryptosDependencies ->
                CryptosComponent.get(dependencies = cryptosDependencies)
            }

    override var dependencyProvider: (() -> CryptosDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): CryptosFeatureApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): CryptosComponent = componentHolderDelegate.fetchComponent()
}