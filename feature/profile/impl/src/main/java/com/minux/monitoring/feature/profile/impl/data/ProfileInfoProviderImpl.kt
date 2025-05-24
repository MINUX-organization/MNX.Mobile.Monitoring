package com.minux.monitoring.feature.profile.impl.data

import com.minux.monitoring.feature.profile.api.ProfileInfoProvider
import com.minux.monitoring.feature.profile.impl.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProfileInfoProviderImpl(
    private val profileRepository: ProfileRepository
) : ProfileInfoProvider {
    override fun getNickName(): Flow<Result<String>> {
        return profileRepository.getProfile().map { result ->
            result.map { it.nickname }
        }
    }
}