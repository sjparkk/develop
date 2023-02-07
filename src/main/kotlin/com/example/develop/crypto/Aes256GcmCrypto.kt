package com.example.develop.crypto

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES 256 GCM 암호화 및 복호화
 * 참고 문서 링크
 * https://www.javainterviewpoint.com/java-aes-256-gcm-encryption-and-decryption/
 */
class Aes256GcmCrypto {

    companion object{

        private const val AES_KEY_SIZE = 256
        private const val GCM_IV_LENGTH = 12
        private const val GCM_TAG_LENGTH = 16

        fun encrypt(plaintext: ByteArray?): Triple<ByteArray, SecretKey, ByteArray> {

            println("Original Text :  $plaintext")

            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(AES_KEY_SIZE)

            //키 생성
            val key = keyGenerator.generateKey()
            val IV = ByteArray(GCM_IV_LENGTH)
            val random = SecureRandom()
            random.nextBytes(IV)

            // 암호 인스턴스 얻기
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")

            // SecretKeySpec 생성
            val keySpec = SecretKeySpec(key.encoded, "AES")

            // GCMParameterSpec 생성
            val gcmParameterSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, IV)

            // ENCRYPT_MODE 에 대한 암호 초기화
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec)

            // 암호화 수행
            val cipherText = cipher.doFinal(plaintext)
            println("암호화 결과 : $cipherText")

            return Triple(cipherText, key, IV)
        }

        fun decrypt(cipherText: ByteArray?, key: SecretKey, IV: ByteArray?): String {

            // 암호 인스턴스 얻기
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")

            // SecretKeySpec 생성
            val keySpec = SecretKeySpec(key.encoded, "AES")

            // GCMParameterSpec 생성
            val gcmParameterSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, IV)

            // DECRYPT_MODE에 대한 암호 초기화
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec)

            // 복호화 수행
            val decryptedText = cipher.doFinal(cipherText)

            return String(decryptedText)
        }
    }
}