package com.minux.monitoring.feature.profile.impl.di

import com.minux.monitoring.feature.profile.api.di.ProfileFeatureApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object ProfileComponentHolder : ComponentHolder<ProfileFeatureApi, ProfileDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            ProfileFeatureApi,
            ProfileDependencies,
            ProfileComponent> { profileDependencies ->
                ProfileComponent.get(dependencies = profileDependencies)
            }

    override var dependencyProvider: (() -> ProfileDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): ProfileFeatureApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): ProfileComponent = componentHolderDelegate.fetchComponent()
}