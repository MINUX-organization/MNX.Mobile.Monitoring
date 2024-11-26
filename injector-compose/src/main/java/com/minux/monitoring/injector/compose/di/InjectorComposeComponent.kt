package com.minux.monitoring.injector.compose.di

import com.minux.monitoring.injector.compose.api.InjectorComposeApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [InjectorComposeDependencies::class],
    modules = [InjectorComposeModule::class]
)
@Singleton
interface InjectorComposeComponent : InjectorComposeApi {

    @Component.Factory
    interface Factory {
        fun create(dependencies: InjectorComposeDependencies): InjectorComposeComponent
    }

    companion object {
        fun get(dependencies: InjectorComposeDependencies): InjectorComposeComponent {
            return DaggerInjectorComposeComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}