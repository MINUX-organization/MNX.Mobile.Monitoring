package com.minux.monitoring.core.network.impl.session.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

internal class CryptoManager {
    private val provider = "AndroidKeyStore"
    private val keyStore by lazy {
        KeyStore.getInstance(provider).apply { load(null) }
    }
    private val keyGenerator by lazy { KeyGenerator.getInstance(ALGORITHM, provider) }

    private val cipher by lazy {
        Cipher.getInstance("$ALGORITHM/$BLOCK_MODE/$PADDING")
    }

    fun encrypt(keyAlias: String, bytes: ByteArray): ByteArray {
        val secretKey = getSecretKey(keyAlias)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        return cipher.iv + cipher.doFinal(bytes)
    }

    fun decrypt(keyAlias: String, bytes: ByteArray): ByteArray? {
        if (bytes.isEmpty()) return null

        val iv = bytes.copyOfRange(fromIndex = 0, toIndex = cipher.blockSize)
        val encryptedData = bytes.copyOfRange(fromIndex = cipher.blockSize, toIndex = bytes.size)

        val secretKey = getSecretKey(keyAlias)
        val gcmParameterSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)

        return cipher.doFinal(encryptedData)
    }

    private fun getSecretKey(keyAlias: String): SecretKey {
        val keyEntry = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        return keyEntry?.secretKey ?: run {
            val keyGenAlgorithm = KeyGenParameterSpec
                .Builder(keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                .setBlockModes(BLOCK_MODE)
                .setEncryptionPaddings(PADDING)
                .setUserAuthenticationRequired(false)
                .setRandomizedEncryptionRequired(true)
                .build()

            keyGenerator.run {
                init(keyGenAlgorithm)
                generateKey()
            }
        }
    }

    companion object {
        private const val ALGORITHM = KEY_ALGORITHM_AES
        private const val BLOCK_MODE = BLOCK_MODE_GCM
        private const val PADDING = ENCRYPTION_PADDING_NONE
    }
}