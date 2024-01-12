package com.example.develop.replace

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * 코틀린 표준 라이브러리에 포함된 String.replace에 대한 테스트 및 정리
 */
class ReplaceTest {

    @Test
    fun `문자열 replace 기본 테스트`() {
        val originalString = "Hello, World!"
        val replacedString = originalString.replace("Hello", "Hi")
        Assertions.assertEquals("Hi, World!", replacedString)
    }

}