package com.minux.monitoring.feature.auth.impl.di

import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.feature.auth.api.di.AuthFeatureApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AuthDependencies::class],
    modules = [AuthModule::class, AuthViewModelModule::class]
)
@Singleton
internal interface AuthComponent : AuthFeatureApi {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: AuthDependencies): AuthComponent
    }

    companion object {
        fun get(dependencies: AuthDependencies): AuthComponent {
            return DaggerAuthComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}