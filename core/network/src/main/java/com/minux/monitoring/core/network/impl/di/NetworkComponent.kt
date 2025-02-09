package com.minux.monitoring.core.network.impl.di

import com.minux.monitoring.core.network.api.di.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [NetworkDependencies::class],
    modules = [NetworkModule::class, NetworkSessionModule::class]
)
@Singleton
internal interface NetworkComponent : NetworkApi {

    @Component.Factory
    interface Factory {
        fun create(dependencies: NetworkDependencies): NetworkComponent
    }

    companion object {
        fun get(dependencies: NetworkDependencies): NetworkComponent {
            return DaggerNetworkComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}