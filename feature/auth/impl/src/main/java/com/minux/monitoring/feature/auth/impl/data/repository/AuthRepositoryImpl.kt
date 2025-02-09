package com.minux.monitoring.feature.auth.impl.data.repository

import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.feature.auth.impl.data.datasource.AuthApiService
import com.minux.monitoring.feature.auth.impl.data.model.AuthInfoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val sessionManager: SessionManager
) : AuthRepository {
    override fun registerUser(authInfo: AuthInfoDto): Flow<Result<Unit>> {
        return authApiService.registerUser(authInfo = authInfo).map { result ->
            result.map { sessionManager.setTokens(tokens = it) }
        }
    }

    override fun authUser(authInfo: AuthInfoDto): Flow<Result<Unit>> {
        return authApiService.authUser(authInfo = authInfo).map { result ->
            result.map { sessionManager.setTokens(tokens = it) }
        }
    }
}