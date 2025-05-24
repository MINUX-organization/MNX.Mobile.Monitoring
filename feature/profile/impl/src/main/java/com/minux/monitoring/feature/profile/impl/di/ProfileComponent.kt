package com.minux.monitoring.feature.profile.impl.di

import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.feature.profile.api.di.ProfileFeatureApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [ProfileDependencies::class],
    modules = [ProfileModule::class, ProfileViewModelModule::class]
)
@Singleton
internal interface ProfileComponent : ProfileFeatureApi {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: ProfileDependencies): ProfileComponent
    }

    companion object {
        fun get(dependencies: ProfileDependencies): ProfileComponent {
            return DaggerProfileComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}