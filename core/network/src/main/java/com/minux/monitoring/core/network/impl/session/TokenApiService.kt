package com.minux.monitoring.core.network.impl.session

import com.minux.monitoring.core.network.api.request.WithoutAuth
import com.minux.monitoring.core.network.api.session.TokensDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

internal interface TokenApiService {

    @WithoutAuth
    @POST("auth/user/refreshTokens")
    fun refreshTokens(@Body token: RefreshTokenDto): Flow<Result<TokensDto>>

    @WithoutAuth
    @PUT("auth/user/invalidateRefreshToken")
    fun invalidateRefreshToken(@Body token: RefreshTokenDto): Flow<Result<Unit>>
}