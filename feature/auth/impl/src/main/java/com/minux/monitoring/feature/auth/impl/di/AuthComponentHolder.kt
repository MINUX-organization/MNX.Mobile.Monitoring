package com.minux.monitoring.feature.auth.impl.di

import com.minux.monitoring.feature.auth.api.di.AuthFeatureApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object AuthComponentHolder : ComponentHolder<AuthFeatureApi, AuthDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            AuthFeatureApi, AuthDependencies, AuthComponent> { authDependencies ->
                AuthComponent.get(dependencies = authDependencies)
            }

    override var dependencyProvider: (() -> AuthDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): AuthFeatureApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): AuthComponent = componentHolderDelegate.fetchComponent()
}