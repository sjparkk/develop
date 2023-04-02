package com.example.develop.retry

import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import java.lang.Math.random
import java.time.LocalDateTime
import java.util.NoSuchElementException

/**
 * Retryable service
 *
 * - Retry 를 적용할 method 에 Retryable 어노테이션을 적용시켜주면 스프링 AOP 에서 위임받아 Retry 를 적용시켜 주는 방식
 * - @EnableRetry 추가 필요
 * - 기본 retry 횟수 3 -> maxAttempts 옵션을 이용해서 지정도 가능
 * - backoff 옵션을 통해 재시도 간의 time delay를 설정할 수 있음
 * - include 옵션을 통해 특정 Exception에 대해서만 retry를 시도할 수 있도록 설정 가능 -> 리스트 형태로 지원하여 여러 exception 설정 가능
 */
@Service
class RetryableService {

    var count = 0

    @Retryable
    fun basicRetry(): Int {
        return randomFail()
    }

    @Retryable(maxAttempts = 5)
    fun retryByMaxAttempts(): Int {
        return randomFail()
    }

    @Retryable(backoff = Backoff(5000))
    fun retryByTimeDelay(): Int {
        return randomFail()
    }

    @Retryable(include = [RuntimeException::class, IllegalArgumentException::class, NoSuchElementException::class])
    fun retryBySpecificException(): Int {
        return randomFail()
    }

    @Retryable(include = [NoSuchElementException::class])
    fun retryByNoSuchElementException(input: String) {
        throw NoSuchElementException()
    }

    //Recover 사용시 Exception을 제외한 나머지 인자에 대해서 타겟 메소드와 동일해야 한다. (입출력 인자)
    @Recover
    fun recoverByNoSuchElementException(e: NoSuchElementException, input: String) {
        println("recover 실행")
    }


    private fun randomFail(): Int {
        if (random() > 0.1) {
            count += 1
            println("failed")
            println(LocalDateTime.now())
            throw RuntimeException(count.toString())
        } else {
            println("success")
            return count
        }
    }
}

