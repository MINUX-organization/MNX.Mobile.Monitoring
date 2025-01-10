package com.minux.monitoring.feature.cryptos.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.feature.cryptos.impl.data.datasource.AlgorithmApiService
import com.minux.monitoring.feature.cryptos.impl.data.datasource.CryptocurrencyApiService
import com.minux.monitoring.feature.cryptos.impl.data.datasource.PoolApiService
import com.minux.monitoring.feature.cryptos.impl.data.datasource.WalletApiService
import com.minux.monitoring.feature.cryptos.impl.data.repository.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.data.repository.CryptocurrencyRepositoryImpl
import com.minux.monitoring.feature.cryptos.impl.data.repository.PoolRepository
import com.minux.monitoring.feature.cryptos.impl.data.repository.PoolRepositoryImpl
import com.minux.monitoring.feature.cryptos.impl.data.repository.WalletRepository
import com.minux.monitoring.feature.cryptos.impl.data.repository.WalletRepositoryImpl
import com.minux.monitoring.feature.cryptos.impl.presentation.navigation.CryptosFeatureMediatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class CryptosModule {

    @Provides
    @Singleton
    fun provideCryptosFeatureMediator(): CryptosFeatureMediator =
        CryptosFeatureMediatorImpl()

    @Provides
    @Singleton
    fun provideCryptocurrencyRepository(httpClient: HttpClient): CryptocurrencyRepository {
        return with(httpClient.getApiClient()) {
            CryptocurrencyRepositoryImpl(
                algorithmApiService = create(AlgorithmApiService::class.java),
                cryptocurrencyApiService = create(CryptocurrencyApiService::class.java)
            )
        }
    }

    @Provides
    @Singleton
    fun provideWalletRepository(httpClient: HttpClient): WalletRepository {
        return with(httpClient.getApiClient()) {
            WalletRepositoryImpl(walletApiService = create(WalletApiService::class.java))
        }
    }

    @Provides
    @Singleton
    fun providePoolRepository(httpClient: HttpClient): PoolRepository {
        return with(httpClient.getApiClient()) {
            PoolRepositoryImpl(poolApiService = create(PoolApiService::class.java))
        }
    }
}