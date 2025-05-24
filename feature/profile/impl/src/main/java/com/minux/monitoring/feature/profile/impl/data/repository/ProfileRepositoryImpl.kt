package com.minux.monitoring.feature.profile.impl.data.repository

import com.minux.monitoring.feature.profile.impl.data.datasource.ProfileApiService
import com.minux.monitoring.feature.profile.impl.data.datasource.UserApiService
import com.minux.monitoring.feature.profile.impl.data.model.ProfileDto
import com.minux.monitoring.feature.profile.impl.data.model.ProfileNicknameChangeDto
import com.minux.monitoring.feature.profile.impl.data.model.UserPasswordChangeDto
import kotlinx.coroutines.flow.Flow

internal class ProfileRepositoryImpl(
    private val profileApiService: ProfileApiService,
    private val userApiService: UserApiService
) : ProfileRepository {
    override fun getProfile(): Flow<Result<ProfileDto>> {
        return profileApiService.getProfile()
    }

    override fun changeProfileNickname(nicknameChange: ProfileNicknameChangeDto): Flow<Result<Unit>> {
        return profileApiService.changeProfileNickname(nicknameChange = nicknameChange)
    }

    override fun changePassword(userPasswordChange: UserPasswordChangeDto): Flow<Result<Unit>> {
        return userApiService.changePassword(userPasswordChange = userPasswordChange)
    }

    override fun generateProfileRigKey(): Flow<Result<Unit>> {
        return profileApiService.generateProfileRigKey()
    }
}