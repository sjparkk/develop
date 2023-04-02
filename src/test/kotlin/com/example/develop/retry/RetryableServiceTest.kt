package com.example.develop.retry

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RetryableServiceTest {

    companion object {
        const val DEFAULT_RETRY_COUNT = 3
    }

    @Autowired
    lateinit var retryableService: RetryableService

    @Test
    @DisplayName("@Retryable의 기본 재시도 값 3회 이하로 수행 되는지 확인")
    fun basicRetry() {
        try {
            val count = retryableService.basicRetry()
            println(":: 재시도 횟수 : $count")
            Assertions.assertTrue(count <= DEFAULT_RETRY_COUNT)
        } catch (e: Exception) {
            println(":: 재시도 횟수 : ${e.message} ")
            Assertions.assertTrue(e.message?.toInt()!! <= DEFAULT_RETRY_COUNT)
        }
    }

}