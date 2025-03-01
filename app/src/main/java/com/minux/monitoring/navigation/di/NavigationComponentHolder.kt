package com.minux.monitoring.navigation.di

import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object NavigationComponentHolder : ComponentHolder<NavigationApi, NavigationDependencies> {

    private val componentHolderDelegate = ComponentHolderDelegate<
            NavigationApi,
            NavigationDependencies,
            NavigationComponent> { navigationDependencies ->
                NavigationComponent.get(dependencies = navigationDependencies)
            }

    override var dependencyProvider: (() -> NavigationDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): NavigationApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): NavigationComponent = componentHolderDelegate.fetchComponent()
}