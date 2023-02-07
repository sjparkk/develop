package com.example.develop.crypto

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class Aes256GcmCryptoTest {

    @Test
    fun `암호화 한 평문을 복호화 했을 때 동일한 값이 나와야 한다`() {

        //given
        val plainText = "iampassword1234"

        //when
        val (cipherText, key, iv) = Aes256GcmCrypto.encrypt(plainText.toByteArray())
        println("cipher Text : $cipherText")

        val encryptedText = Base64.getEncoder().encodeToString(cipherText)
        println("Encrypted Text : $encryptedText")

        val decryptedText = Aes256GcmCrypto.decrypt(cipherText, key, iv)
        println("Decrypted Text : $decryptedText")

        //then
        Assertions.assertEquals(plainText, decryptedText)

    }

}