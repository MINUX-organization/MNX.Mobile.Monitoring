package com.minux.monitoring.feature.cryptos.impl.di

import com.minux.monitoring.core.network.api.BackendApi
import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.feature.cryptos.impl.common.data.datasource.AlgorithmApiService
import com.minux.monitoring.feature.cryptos.impl.common.data.datasource.CryptocurrencyApiService
import com.minux.monitoring.feature.cryptos.impl.pools.data.datasource.PoolApiService
import com.minux.monitoring.feature.cryptos.impl.wallets.data.datasource.WalletApiService
import com.minux.monitoring.feature.cryptos.impl.common.data.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.common.data.CryptocurrencyRepositoryImpl
import com.minux.monitoring.feature.cryptos.impl.pools.data.PoolRepository
import com.minux.monitoring.feature.cryptos.impl.pools.data.PoolRepositoryImpl
import com.minux.monitoring.feature.cryptos.impl.wallets.data.WalletRepository
import com.minux.monitoring.feature.cryptos.impl.wallets.data.WalletRepositoryImpl
import com.minux.monitoring.feature.cryptos.impl.CryptosFeatureMediatorImpl
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
        return with(httpClient.getApiClient(BackendApi.Monitoring)) {
            CryptocurrencyRepositoryImpl(
                algorithmApiService = create(AlgorithmApiService::class.java),
                cryptocurrencyApiService = create(CryptocurrencyApiService::class.java)
            )
        }
    }

    @Provides
    @Singleton
    fun provideWalletRepository(httpClient: HttpClient): WalletRepository {
        return with(httpClient.getApiClient(BackendApi.Monitoring)) {
            WalletRepositoryImpl(walletApiService = create(WalletApiService::class.java))
        }
    }

    @Provides
    @Singleton
    fun providePoolRepository(httpClient: HttpClient): PoolRepository {
        return with(httpClient.getApiClient(BackendApi.Monitoring)) {
            PoolRepositoryImpl(poolApiService = create(PoolApiService::class.java))
        }
    }
}