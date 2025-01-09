package com.minux.monitoring.core.network.api.session

interface SessionManager {
    suspend fun setTokens(tokens: TokensDto)

    suspend fun isRefreshTokenExpired(): Boolean
}