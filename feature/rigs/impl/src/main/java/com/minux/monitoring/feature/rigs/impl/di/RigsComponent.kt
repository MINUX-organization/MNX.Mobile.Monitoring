package com.minux.monitoring.feature.rigs.impl.di

import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.feature.rigs.api.di.RigsFeatureApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [RigsDependencies::class],
    modules = [RigsModule::class, RigsViewModelModule::class]
)
@Singleton
internal interface RigsComponent : RigsFeatureApi {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: RigsDependencies): RigsComponent
    }

    companion object {
        fun get(dependencies: RigsDependencies): RigsComponent {
            return DaggerRigsComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}