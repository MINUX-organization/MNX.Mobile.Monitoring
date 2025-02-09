package com.minux.monitoring.core.network.impl.session

import com.minux.monitoring.core.network.api.request.WithoutAuth
import com.minux.monitoring.core.network.api.session.TokensDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

internal interface TokenApiService {

    @WithoutAuth
    @POST("/auth/user/refreshTokens")
    fun refreshTokens(@Body token: RefreshTokensDto): Flow<Result<TokensDto>>
}