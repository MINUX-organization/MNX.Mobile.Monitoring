package com.minux.monitoring.feature.cryptos.impl.di

import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.feature.cryptos.api.di.CryptosFeatureApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [CryptosDependencies::class],
    modules = [CryptosModule::class, CryptosViewModelModule::class]
)
@Singleton
internal interface CryptosComponent : CryptosFeatureApi {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: CryptosDependencies): CryptosComponent
    }

    companion object {
        fun get(dependencies: CryptosDependencies): CryptosComponent {
            return DaggerCryptosComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}