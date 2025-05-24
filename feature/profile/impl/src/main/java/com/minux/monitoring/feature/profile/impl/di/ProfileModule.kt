package com.minux.monitoring.feature.profile.impl.di

import com.minux.monitoring.core.network.api.BackendApi
import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.auth.api.PasswordValidator
import com.minux.monitoring.feature.profile.api.ProfileFeatureMediator
import com.minux.monitoring.feature.profile.api.ProfileInfoProvider
import com.minux.monitoring.feature.profile.impl.data.ProfileInfoProviderImpl
import com.minux.monitoring.feature.profile.impl.data.datasource.ProfileApiService
import com.minux.monitoring.feature.profile.impl.data.datasource.UserApiService
import com.minux.monitoring.feature.profile.impl.data.repository.ProfileRepository
import com.minux.monitoring.feature.profile.impl.data.repository.ProfileRepositoryImpl
import com.minux.monitoring.feature.profile.impl.domain.usecase.ValidateNewPasswordUseCase
import com.minux.monitoring.feature.profile.impl.domain.validator.ProfilePasswordValidator
import com.minux.monitoring.feature.profile.impl.presentation.navigation.ProfileFeatureMediatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class ProfileModule {

    @Provides
    @Singleton
    fun provideProfileFeatureMediator(): ProfileFeatureMediator =
        ProfileFeatureMediatorImpl()

    @Provides
    @Singleton
    fun provideProfileInfoProvider(profileRepository: ProfileRepository): ProfileInfoProvider {
        return ProfileInfoProviderImpl(profileRepository = profileRepository)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(httpClient: HttpClient): ProfileRepository {
        return with(httpClient.getApiClient(BackendApi.Security)) {
            ProfileRepositoryImpl(
                profileApiService = create(ProfileApiService::class.java),
                userApiService = create(UserApiService::class.java)
            )
        }
    }

    @Provides
    @Singleton
    fun provideProfilePasswordValidator(
        passwordValidator: PasswordValidator,
        validateNewPasswordUseCase: ValidateNewPasswordUseCase
    ): ProfilePasswordValidator {
        return ProfilePasswordValidator(
            passwordValidator = passwordValidator,
            validateNewPasswordUseCase = validateNewPasswordUseCase
        )
    }

    @Provides
    fun provideValidateNewPasswordUseCase(): ValidateNewPasswordUseCase =
        ValidateNewPasswordUseCase()
}