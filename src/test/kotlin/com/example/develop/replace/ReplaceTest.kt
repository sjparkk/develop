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

    /**
     * replace 함수에서 일치하는 것 모두 바뀌는 이유?
     *
     * replace 함수 내부 구현 코드
    @Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
    public actual fun String.replace(oldValue: String, newValue: String, ignoreCase: Boolean = false): String {
        run {
            var occurrenceIndex: Int = indexOf(oldValue, 0, ignoreCase)
            // FAST PATH: no match
            if (occurrenceIndex < 0) return this

            val oldValueLength = oldValue.length
            val searchStep = oldValueLength.coerceAtLeast(1)
            val newLengthHint = length - oldValueLength + newValue.length
            if (newLengthHint < 0) throw OutOfMemoryError()
            val stringBuilder = StringBuilder(newLengthHint)

            var i = 0
            do {
                stringBuilder.append(this, i, occurrenceIndex).append(newValue)
                i = occurrenceIndex + oldValueLength
                if (occurrenceIndex >= length) break
                occurrenceIndex = indexOf(oldValue, occurrenceIndex + searchStep, ignoreCase)
            } while (occurrenceIndex > 0)
            return stringBuilder.append(this, i, length).toString()
        }
    }
     *  여기서 중요한 부분은 do while문 을 통해서 일치하는 oldValue가 더 이상 발생하지 않을 때까지 루프가 계속 되어 치환 되는 것
     */
    @Test
    fun `코틀린에서 replace는 일치하는 것 모두 바꿈`() {
        val originalString = "Hello, Hello, Hello, World!"
        val replacedString = originalString.replace("Hello", "Hi")
        Assertions.assertEquals("Hi, Hi, Hi, World!", replacedString)
    }

    @Test
    fun `replace는 첫번째만 바뀌지 않음`() {
        val originalString = "Hello, Hello, Hello, World!"
        val replacedString = originalString.replace("Hello", "Hi")
        Assertions.assertNotEquals("Hi, Hello, Hello, World!", replacedString)
    }
}