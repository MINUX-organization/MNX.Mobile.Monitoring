package com.minux.monitoring.feature.profile.impl.data.datasource

import com.minux.monitoring.feature.profile.impl.data.model.UserPasswordChangeDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.PUT

internal interface UserApiService {

    @PUT("auth/user/changePassword")
    fun changePassword(@Body userPasswordChange: UserPasswordChangeDto): Flow<Result<Unit>>
}