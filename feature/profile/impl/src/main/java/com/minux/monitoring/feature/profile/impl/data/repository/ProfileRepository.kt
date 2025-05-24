package com.minux.monitoring.feature.profile.impl.data.repository

import com.minux.monitoring.feature.profile.impl.data.model.ProfileDto
import com.minux.monitoring.feature.profile.impl.data.model.ProfileNicknameChangeDto
import com.minux.monitoring.feature.profile.impl.data.model.UserPasswordChangeDto
import kotlinx.coroutines.flow.Flow

internal interface ProfileRepository {
    fun getProfile(): Flow<Result<ProfileDto>>

    fun changeProfileNickname(nicknameChange: ProfileNicknameChangeDto): Flow<Result<Unit>>

    fun changePassword(userPasswordChange: UserPasswordChangeDto): Flow<Result<Unit>>

    fun generateProfileRigKey(): Flow<Result<Unit>>
}