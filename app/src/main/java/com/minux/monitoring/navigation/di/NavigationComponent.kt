package com.minux.monitoring.navigation.di

import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [NavigationDependencies::class],
    modules = [NavigationModule::class]
)
@Singleton
internal interface NavigationComponent : NavigationApi {

    @Component.Factory
    interface Factory {
        fun create(dependencies: NavigationDependencies): NavigationComponent
    }

    companion object {
        fun get(dependencies: NavigationDependencies): NavigationComponent {
            return DaggerNavigationComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}