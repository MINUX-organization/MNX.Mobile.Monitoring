package com.minux.monitoring.core.network.api.session

import kotlinx.coroutines.flow.Flow

interface SessionManager {
    suspend fun updateCredentials(credentials: TokensDto)

    suspend fun invalidateCredentials()

    fun observeExpirationStatus(): Flow<Boolean>
}