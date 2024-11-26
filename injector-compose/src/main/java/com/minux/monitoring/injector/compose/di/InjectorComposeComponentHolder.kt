package com.minux.monitoring.injector.compose.di

import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate
import com.minux.monitoring.injector.compose.api.InjectorComposeApi

object InjectorComposeComponentHolder : ComponentHolder<InjectorComposeApi, InjectorComposeDependencies> {

    private val componentHolderDelegate = ComponentHolderDelegate<
            InjectorComposeApi,
            InjectorComposeDependencies,
            InjectorComposeComponent> { injectorComposeDependencies ->
                InjectorComposeComponent.get(dependencies = injectorComposeDependencies)
            }

    override val dependencyProvider: (() -> InjectorComposeDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): InjectorComposeApi = componentHolderDelegate.fetchApi()
}