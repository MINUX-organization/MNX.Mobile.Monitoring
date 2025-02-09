package com.minux.monitoring.app.di

import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object AppComponentHolder : ComponentHolder<AppApi, AppDependencies> {

    private val componentHolderDelegate = ComponentHolderDelegate<
            AppApi, AppDependencies, AppComponent> { appDependencies ->
                AppComponent.get(dependencies = appDependencies)
            }

    override var dependencyProvider: (() -> AppDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): AppApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): AppComponent = componentHolderDelegate.fetchComponent()
}