package com.minux.monitoring.feature.profile.impl.data.datasource

import com.minux.monitoring.feature.profile.impl.data.model.ProfileDto
import com.minux.monitoring.feature.profile.impl.data.model.ProfileNicknameChangeDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

internal interface ProfileApiService {

    @GET("profile")
    fun getProfile(): Flow<Result<ProfileDto>>

    @PATCH("profile/nickname")
    fun changeProfileNickname(@Body nicknameChange: ProfileNicknameChangeDto): Flow<Result<Unit>>

    @POST("profile/key/generate")
    fun generateProfileRigKey(): Flow<Result<Unit>>
}