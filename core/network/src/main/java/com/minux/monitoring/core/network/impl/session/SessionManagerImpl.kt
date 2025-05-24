package com.minux.monitoring.core.network.impl.session

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.auth0.android.jwt.JWT
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.api.session.TokensDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.minus
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionManagerImpl(
    private val tokenApiService: TokenApiService,
    private val tokensDataStore: DataStore<TokensDto>
) : SessionManager {

    private val _tokens: StateFlow<TokensDto> by lazy {
        tokensDataStore.data
            .distinctUntilChanged()
            .catch { e ->
                if (e is IOException) emit(TokensDto()) else throw e
            }
            .stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.WhileSubscribed(),
                initialValue = TokensDto()
            )
    }

    private val _updateTokenMutex = Mutex()

    override suspend fun updateCredentials(credentials: TokensDto) {
        tokensDataStore.updateData { credentials }
    }

    override suspend fun invalidateCredentials(): Result<Unit> {
        val credentials = tokensDataStore.updateData {
            val invalidateRefreshTokenResult = tokenApiService.invalidateRefreshToken(
                token = RefreshTokenDto(refreshToken = it.refreshToken)
            ).first()

            return@updateData if (invalidateRefreshTokenResult.isSuccess)
                TokensDto()
            else
                it
        }

        return if (credentials.accessToken.isNullOrEmpty() && credentials.refreshToken.isNullOrEmpty())
            Result.success(Unit)
        else
            Result.failure(Throwable())
    }

    override fun observeExpirationStatus(): Flow<Boolean> = _tokens.mapLatest {
        val refreshExpiration = it.refreshExpiration ?: return@mapLatest true
        return@mapLatest isRefreshTokenExpired(dateTime = refreshExpiration)
    }

    fun getAccessToken(): Flow<String> = _tokens.mapLatest { tokens ->
        if (tokens.accessToken.isNullOrEmpty()) return@mapLatest ""
        
        if (!isAccessTokenExpired(accessToken = tokens.accessToken)) {
            return@mapLatest tokens.accessToken
        }

        _updateTokenMutex.withLock {
            val currentTokens = _tokens.value

            if (currentTokens.accessToken != null &&
                !isAccessTokenExpired(accessToken = currentTokens.accessToken)) return@withLock

            updateAccessToken(tokens = tokens)
        }

        return@mapLatest tokens.accessToken
    }

    private suspend fun updateAccessToken(tokens: TokensDto) {
        val token = tokens.refreshToken ?: throw TokensNotSetException()

        val newTokens = tokenApiService.refreshTokens(
            token = RefreshTokenDto(refreshToken = token)
        ).first()

        newTokens.onSuccess {
            if (it.accessToken != tokens.accessToken) {
                updateCredentials(credentials = it)
            }
        }.onFailure { error ->
            if (error is HttpException && (error.code() == 401 || error.code() == 400)) {
                invalidateCredentials()
            }
        }
    }

    private fun isAccessTokenExpired(accessToken: String): Boolean {
        val jwt = JWT(accessToken)
        return jwt.isExpired(2 * 60)
    }

    private fun isRefreshTokenExpired(dateTime: String): Boolean {
        val expiration = Instant.parse(dateTime)
        return Clock.System.now() >= expiration.minus(5, DateTimeUnit.MINUTE)
    }
}