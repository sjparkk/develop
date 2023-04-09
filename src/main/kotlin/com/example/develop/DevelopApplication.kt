package com.example.develop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.retry.annotation.EnableRetry

/**
 * Properties Class 설정
 *
 * @EnableConfigurationProperties(BeforeBindingProperties::class)
 * - @ConfigurationProperties
 * - @ConstructorBinding 사용 가능
 * - Properties Class 많아지게되면 코드 무거워지는 단점
 *
 * @ConfigurationPropertiesScan
 * - 스프링부트 2.2 부터 지원
 * - @EnableConfigurationProperties or @Component 없이 사용 가능
 * - @ConstructorBinding 사용 가능
 * - @ConstructorBinding 사용하여 Immutable 한 Properties 객체 보장 가능
 */
//@EnableConfigurationProperties(BeforeBindingProperties::class)
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableRetry
@EnableFeignClients
class DevelopApplication

fun main(args: Array<String>) {
    runApplication<DevelopApplication>(*args)
}
