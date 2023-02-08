package com.example.develop.crypto

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

@Component
class CryptoUtils {

    @Value(value = "\${crypto.key}")
    private lateinit var key: String

    private val GCM_IV_LENGTH = 12
    private val GCM_TAG_LENGTH = 16

    private val iv = generateIv()

    fun encryptAES(plaintext: String): String {

        val secretKey = SecretKeySpec(key.toByteArray(), "AES")

        // 암호 인스턴스 얻기
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")

        // SecretKeySpec 생성
        val keySpec = SecretKeySpec(secretKey.encoded, "AES")

        // GCMParameterSpec 생성
        val gcmParameterSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)

        // ENCRYPT_MODE 에 대한 암호 초기화
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec)

        // 암호화 수행
        val cipherText = cipher.doFinal(plaintext.toByteArray())

        return Base64.getEncoder().encodeToString(cipherText)
    }

    fun decryptAES(encryptedText: String): String {

        val cipherText = Base64.getDecoder().decode(encryptedText)

        val secretKey = SecretKeySpec(key.toByteArray(), "AES")

        // 암호 인스턴스 얻기
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")

        // SecretKeySpec 생성
        val keySpec = SecretKeySpec(secretKey.encoded, "AES")

        // GCMParameterSpec 생성
        val gcmParameterSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)

        // DECRYPT_MODE에 대한 암호 초기화
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec)

        // 복호화 수행
        val decryptedText = cipher.doFinal(cipherText)

        return String(decryptedText)
    }


    private final fun generateIv(): ByteArray {
        val iv = ByteArray(GCM_IV_LENGTH)
        return iv
    }
}