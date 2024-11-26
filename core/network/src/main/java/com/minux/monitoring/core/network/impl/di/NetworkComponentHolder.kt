package com.minux.monitoring.core.network.impl.di

import com.minux.monitoring.core.network.api.di.NetworkApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object NetworkComponentHolder : ComponentHolder<NetworkApi, NetworkDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            NetworkApi, NetworkDependencies, NetworkComponent> { networkDependencies ->
                NetworkComponent.get(dependencies = networkDependencies)
            }

    override val dependencyProvider: (() -> NetworkDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): NetworkApi = componentHolderDelegate.fetchApi()
}