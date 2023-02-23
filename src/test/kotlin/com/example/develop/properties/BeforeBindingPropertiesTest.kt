package com.example.develop.properties

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BeforeBindingPropertiesTest(
    val beforeBindingProperties: BeforeBindingProperties
) {

    @Test
    fun `바인딩 출력 값 확인`() {
        println(beforeBindingProperties.a)
        println(beforeBindingProperties.b)
        println(beforeBindingProperties.c)
    }
}