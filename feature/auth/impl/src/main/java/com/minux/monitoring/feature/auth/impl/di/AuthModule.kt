package com.minux.monitoring.feature.auth.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.feature.auth.api.AuthFeatureMediator
import com.minux.monitoring.feature.auth.impl.data.datasource.AuthApiService
import com.minux.monitoring.feature.auth.impl.data.repository.AuthRepository
import com.minux.monitoring.feature.auth.impl.data.repository.AuthRepositoryImpl
import com.minux.monitoring.feature.auth.impl.domain.usecase.ValidatePasswordConfirmUseCase
import com.minux.monitoring.feature.auth.impl.domain.usecase.ValidatePasswordUseCase
import com.minux.monitoring.feature.auth.impl.presentation.navigation.AuthFeatureMediatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AuthModule {

    @Provides
    @Singleton
    fun provideAuthFeatureMediator(): AuthFeatureMediator =
        AuthFeatureMediatorImpl()

    @Provides
    @Singleton
    fun provideAuthRepository(
        httpClient: HttpClient,
        sessionManager: SessionManager
    ): AuthRepository {
        return with(httpClient.getApiClient()) {
            AuthRepositoryImpl(
                authApiService = create(AuthApiService::class.java),
                sessionManager = sessionManager
            )
        }
    }

    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase =
        ValidatePasswordUseCase()

    @Provides
    fun provideValidatePasswordConfirmUseCase(): ValidatePasswordConfirmUseCase =
        ValidatePasswordConfirmUseCase()
}