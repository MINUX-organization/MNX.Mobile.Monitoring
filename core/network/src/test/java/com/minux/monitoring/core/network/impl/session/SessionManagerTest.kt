package com.minux.monitoring.core.network.impl.session

import androidx.datastore.core.DataStore
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.minux.monitoring.core.network.api.session.TokensDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

internal class SessionManagerTest {

    @get:Rule
    val mockkRule = MockKRule(testSubject = this)

    private lateinit var tokenApiService: TokenApiService

    private lateinit var tokensDataStore: DataStore<TokensDto>

    private lateinit var sessionManager: SessionManagerImpl

    @Before
    fun `set up`() {
        tokenApiService = mockk<TokenApiService>()
        tokensDataStore = mockk<DataStore<TokensDto>>()

        sessionManager = SessionManagerImpl(
            tokenApiService = tokenApiService,
            tokensDataStore = tokensDataStore
        )
    }

    @Test
    fun `setTokens updates the tokens in the DataStore`() = runTest {
        val tokens = TokensDto(accessToken = "access", refreshToken = "refresh")
        coEvery { tokensDataStore.updateData(any()) } returns tokens

        sessionManager.setTokens(tokens)

        coVerify { tokensDataStore.updateData(any()) }
    }

    @Test
    fun `isRefreshTokenExpired, if the refresh token has expired, returns true`() = runTest {
        val expiredDate = "2020-01-01T00:00:00.000Z"
        val tokens = TokensDto(refreshExpiration = expiredDate)
        every { tokensDataStore.data } returns flowOf(tokens)

        sessionManager.isRefreshTokenExpired().test {
            assertThat(awaitItem()).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `isRefreshTokenExpired, if the refresh token hasn't expired, returns false`() = runTest {
        val tokens = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        ).run {
            timeZone = TimeZone.getTimeZone("UTC")
            val futureDate = format(Date(System.currentTimeMillis() + 1000000))
            TokensDto(refreshExpiration = futureDate)
        }
        every { tokensDataStore.data } returns flowOf(tokens)

        sessionManager.isRefreshTokenExpired().test {
            assertThat(awaitItem()).isFalse()
            awaitComplete()
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `getAccessToken, if the access token hasn't expired, returns it`() = runTest {
        val exp = "{\"exp\":${System.currentTimeMillis() + 1000000}}"
        val tokens = TokensDto(
            accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                    "${Base64.encode(exp.encodeToByteArray())}." +
                    "dummySignature",
            refreshToken = "valid_refresh_token"
        )
        every { tokensDataStore.data } returns flowOf(tokens)
        every { tokenApiService.refreshTokens(any()) } returns flowOf(Result.success(tokens))
        coEvery { tokensDataStore.updateData(any()) } returns tokens

        sessionManager.getAccessToken().test {
            assertThat(awaitItem()).isEqualTo(tokens.accessToken)
            awaitComplete()
        }

        verify(inverse = true) { tokenApiService.refreshTokens(any()) }
        coVerify(inverse = true) { tokensDataStore.updateData(any()) }
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `getAccessToken, if the access token has expired, updates it`() = runTest {
        val exp = "{\"exp\":${System.currentTimeMillis() - 1000000}}"
        val tokens = TokensDto(
            accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                    "${Base64.encode(exp.encodeToByteArray())}." +
                    "dummySignature",
            refreshToken = "valid_refresh_token"
        )
        every { tokensDataStore.data } returns flowOf(tokens)
        every { tokenApiService.refreshTokens(any()) } returns flowOf(Result.success(tokens))
        coEvery { tokensDataStore.updateData(any()) } returns tokens

        sessionManager.getAccessToken().test {
            assertThat(awaitItem()).isEqualTo(tokens.accessToken)
            awaitComplete()
        }

        verify { tokenApiService.refreshTokens(any()) }
        coVerify { tokensDataStore.updateData(any()) }
    }

    @Test
    fun `getAccessToken, if tokens aren't set, throw TokensNotSetException`() = runTest {
        every { tokensDataStore.data } returns flowOf(TokensDto())

        sessionManager.getAccessToken().test {
            assertThat(awaitError()).isInstanceOf(TokensNotSetException::class.java)
        }
    }
}