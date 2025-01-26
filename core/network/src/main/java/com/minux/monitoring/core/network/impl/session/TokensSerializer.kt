package com.minux.monitoring.core.network.impl.session

import androidx.datastore.core.Serializer
import com.minux.monitoring.core.network.BuildConfig
import com.minux.monitoring.core.network.api.session.TokensDto
import com.minux.monitoring.core.network.impl.session.security.CryptoManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

internal class TokensSerializer(private val cryptoManager: CryptoManager) : Serializer<TokensDto> {

    override val defaultValue: TokensDto
        get() = TokensDto()

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(input: InputStream): TokensDto {
        return try {
            val decryptedBytes = cryptoManager.decrypt(
                keyAlias = BuildConfig.CIPHER_KEY_ALIAS,
                bytes = input.readBytes()
            )

            val tokensByteArray = decryptedBytes ?: return defaultValue

            ProtoBuf.decodeFromByteArray(
                deserializer = TokensDto.serializer(),
                bytes = tokensByteArray
            )
        } catch (ex: SerializationException) {
            defaultValue
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun writeTo(t: TokensDto, output: OutputStream) {
        val tokensByteArray = ProtoBuf.encodeToByteArray(
            serializer = TokensDto.serializer(),
            value = t
        )

        val encryptedBytes = cryptoManager.encrypt(
            keyAlias = BuildConfig.CIPHER_KEY_ALIAS,
            bytes = tokensByteArray
        )

        withContext(Dispatchers.IO) {
            output.write(encryptedBytes)
        }
    }
}