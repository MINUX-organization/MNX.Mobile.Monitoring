package com.minux.monitoring.feature.auth.impl.data.datasource

import com.minux.monitoring.core.network.api.request.WithoutAuth
import com.minux.monitoring.core.network.api.session.TokensDto
import com.minux.monitoring.feature.auth.impl.data.model.AuthInfoDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthApiService {

    @WithoutAuth
    @POST("auth/user/registration")
    fun registerUser(@Body authInfo: AuthInfoDto): Flow<Result<TokensDto>>

    @WithoutAuth
    @POST("auth/user/login")
    fun authUser(@Body authInfo: AuthInfoDto): Flow<Result<TokensDto>>
}