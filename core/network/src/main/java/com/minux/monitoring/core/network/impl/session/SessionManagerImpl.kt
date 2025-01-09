package com.minux.monitoring.core.network.impl.session

import android.util.Base64
import androidx.datastore.core.DataStore
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.api.session.TokensDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

// TODO: Написать Unit тест для этого класса

internal class SessionManagerImpl(
    private val tokenApiService: TokenApiService,
    private val tokensDataStore: DataStore<TokensDto>
) : SessionManager {

    private val _tokens: Flow<TokensDto> by lazy { tokensDataStore.data }
    private val _updateTokenMutex = Mutex()

    override suspend fun setTokens(tokens: TokensDto) {
        tokensDataStore.updateData { tokens }
    }

    override suspend fun isRefreshTokenExpired(): Boolean {
        val refreshExpiration = _tokens.last().refreshExpiration ?: return true

        val refreshExpirationTime = getRefreshTokenExpiration(dateTime = refreshExpiration)
        return System.currentTimeMillis() >= refreshExpirationTime
    }

    suspend fun getAccessToken(): String {
        if (isAccessTokenExpired()) {
            _updateTokenMutex.withLock {
                if (!isAccessTokenExpired()) return@withLock

                updateAccessToken()
            }
        }

        return _tokens.last().accessToken ?: throw TokensNotSetException()
    }

    private suspend fun updateAccessToken() {
        val refreshToken = _tokens.last().refreshToken
            ?: throw TokensNotSetException()

        val newTokens = withContext(Dispatchers.IO) {
             tokenApiService.refreshTokens(
                token = RefreshTokensDto(refreshToken = refreshToken)
            ).last()
        }

        newTokens.onSuccess {
            setTokens(tokens = it)
        }
    }

    private suspend fun isAccessTokenExpired(): Boolean {
        val accessToken = _tokens.last().accessToken
            ?: throw TokensNotSetException()

        val accessExpirationTime = getAccessTokenExpiration(jwtToken = accessToken)
        return System.currentTimeMillis() >= accessExpirationTime
    }

    private fun getAccessTokenExpiration(jwtToken: String): Long {
        val parts = jwtToken.split(".")
        if (parts.size < 2) throw IllegalArgumentException("Invalid JWT token")

        val payload = String(bytes = Base64.decode(parts[1], Base64.URL_SAFE))
        val json = JSONObject(payload)

        return json.getLong("exp") * 1000
    }

    private fun getRefreshTokenExpiration(dateTime: String): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")

        val date = formatter.parse(dateTime)
        return date?.time ?: throw IllegalArgumentException("Invalid date format")
    }
}