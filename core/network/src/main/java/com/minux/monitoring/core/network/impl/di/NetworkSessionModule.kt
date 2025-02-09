package com.minux.monitoring.core.network.impl.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.api.session.TokensDto
import com.minux.monitoring.core.network.impl.session.SessionManagerImpl
import com.minux.monitoring.core.network.impl.session.TokenApiService
import com.minux.monitoring.core.network.impl.session.TokensSerializer
import com.minux.monitoring.core.network.impl.session.security.CryptoManager
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
internal class NetworkSessionModule {

    @Provides
    @Singleton
    fun provideSessionManager(
        httpClient: HttpClient,
        tokensDataStore: DataStore<TokensDto>
    ): SessionManager {
        return with(httpClient.getApiClient()) {
            SessionManagerImpl(
                tokenApiService = create(TokenApiService::class.java),
                tokensDataStore = tokensDataStore
            )
        }
    }

    @Provides
    @Singleton
    fun provideTokensDataStore(
        context: Application,
        cryptoManager: CryptoManager
    ): DataStore<TokensDto> {
        return DataStoreFactory.create(
            produceFile = { File(context.filesDir, "tokens.pb") },
            serializer = TokensSerializer(cryptoManager = cryptoManager)
        )
    }

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager =
        CryptoManager()
}