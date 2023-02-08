package com.example.develop.crypto

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CryptoUtilsTest(
    val cryptoUtils: CryptoUtils
) {

    val plainText = "password123sasasasasas4"

    @Test
    fun `암복호화`() {

        println("plainText :: $plainText")

        val encryptoText = cryptoUtils.encryptAES(plainText)
        println("encryptoText :: $encryptoText")

        val decryptoText = cryptoUtils.decryptAES(encryptoText)
        println("decryptoText :: $decryptoText")

        Assertions.assertEquals(plainText, decryptoText)
    }

    @Test
    fun `복호화`() {
        val encryptoText = "jwQPM2gjGYO/lqnl4N4dBWioBlh/eLdYqnk="
        println("encryptoText :: $encryptoText")

        val decryptoText = cryptoUtils.decryptAES(encryptoText)
        println("decryptoText :: $decryptoText")

        Assertions.assertEquals("test121212", decryptoText)
    }

}