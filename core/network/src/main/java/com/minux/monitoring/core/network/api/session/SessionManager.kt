package com.minux.monitoring.core.network.api.session

import kotlinx.coroutines.flow.Flow

interface SessionManager {
    suspend fun setTokens(tokens: TokensDto)

    fun isRefreshTokenExpired(): Flow<Boolean>
}