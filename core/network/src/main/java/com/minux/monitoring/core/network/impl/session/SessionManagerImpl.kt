package com.minux.monitoring.core.network.impl.session

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.api.session.TokensDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.long
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionManagerImpl(
    private val tokenApiService: TokenApiService,
    private val tokensDataStore: DataStore<TokensDto>
) : SessionManager {

    private val _tokens: Flow<TokensDto> by lazy {
        tokensDataStore.data
            .distinctUntilChanged()
            .catch { e ->
                if (e is IOException) emit(TokensDto()) else throw e
            }
            .flowOn(Dispatchers.IO)
    }

    private val _updateTokenMutex = Mutex()

    override suspend fun setTokens(tokens: TokensDto) {
        tokensDataStore.updateData { tokens }
    }

    override fun isRefreshTokenExpired(): Flow<Boolean> = _tokens.mapLatest {
        val refreshExpiration = it.refreshExpiration ?: return@mapLatest true

        val refreshExpirationTime = getRefreshTokenExpiration(dateTime = refreshExpiration)
        return@mapLatest System.currentTimeMillis() >= refreshExpirationTime
    }

    fun getAccessToken(): Flow<String> = _tokens.mapLatest {
        if (isAccessTokenExpired(accessToken = it.accessToken)) {
            _updateTokenMutex.withLock {
                if (!isAccessTokenExpired(accessToken = it.accessToken)) return@withLock

                updateAccessToken(refreshToken = it.refreshToken)
            }
        }

        return@mapLatest it.accessToken ?: throw TokensNotSetException()
    }

    private suspend fun updateAccessToken(refreshToken: String?) {
        val token = refreshToken ?: throw TokensNotSetException()

        val newTokens = tokenApiService.refreshTokens(
            token = RefreshTokensDto(refreshToken = token)
        ).first()

        newTokens.onSuccess {
            setTokens(tokens = it)
        }
    }

    private fun isAccessTokenExpired(accessToken: String?): Boolean {
        val token = accessToken ?: throw TokensNotSetException()

        val accessExpirationTime = getAccessTokenExpiration(jwtToken = token)
        return System.currentTimeMillis() >= accessExpirationTime
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun getAccessTokenExpiration(jwtToken: String): Long {
        val parts = jwtToken.split(".")
        if (parts.size < 2) throw IllegalArgumentException("Invalid JWT token")

        val payload = String(bytes = Base64.decode(parts[1]))

        return try {
            val jsonObject = Json.parseToJsonElement(payload).jsonObject
            (jsonObject["exp"] as? JsonPrimitive)?.long
                ?: throw IllegalArgumentException("Missing 'exp' field in JWT payload")
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JWT payload", e)
        }
    }

    private fun getRefreshTokenExpiration(dateTime: String): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")

        val date = formatter.parse(dateTime)
        return date?.time ?: throw IllegalArgumentException("Invalid date format")
    }
}