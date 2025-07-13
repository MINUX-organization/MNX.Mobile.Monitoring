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
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

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
    fun `updateCredentials updates the session credentials in the DataStore`() = runTest {
        val tokens = TokensDto(accessToken = "access", refreshToken = "refresh")
        coEvery { tokensDataStore.updateData(any()) } returns tokens

        sessionManager.updateCredentials(tokens)

        coVerify { tokensDataStore.updateData(any()) }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `observeExpirationStatus, if the session has expired, returns true`() = runTest {
        val pastInstant = Clock.System.now() - 1000.minutes
        val tokens = TokensDto(refreshExpiration = pastInstant.toString())
        every { tokensDataStore.data } returns flowOf(tokens)

        sessionManager.observeExpirationStatus().test {
            assertThat(awaitItem()).isTrue()
            awaitComplete()
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `observeExpirationStatus, if the session hasn't expired, returns false`() = runTest {
        val futureInstant = Clock.System.now() + 1000.minutes
        val tokens = TokensDto(refreshExpiration = futureInstant.toString())

        every { tokensDataStore.data } returns flowOf(tokens)

        sessionManager.observeExpirationStatus().test {
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

        assertThat(sessionManager.getAccessToken()).isEqualTo(tokens.accessToken)

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

        assertThat(sessionManager.getAccessToken()).isEqualTo(tokens.accessToken)

        verify { tokenApiService.refreshTokens(any()) }
        coVerify { tokensDataStore.updateData(any()) }
    }

    @Test
    fun `getAccessToken, if tokens aren't set, returns empty string`() = runTest {
        every { tokensDataStore.data } returns flowOf(TokensDto())

        assertThat(sessionManager.getAccessToken()).isEqualTo("")

        verify(inverse = true) { tokenApiService.refreshTokens(any()) }
        coVerify(inverse = true) { tokensDataStore.updateData(any()) }
    }
}