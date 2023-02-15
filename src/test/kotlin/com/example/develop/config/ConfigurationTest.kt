package com.example.develop.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [ConfigurationTest.Config::class])
class ConfigurationTest {
    @Autowired
    var consumer: SimpleBeanConsumer? = null

    @Test
    fun test() {
        assertNotNull(consumer)
    }

    /**
     * @Component 와 @Configuration 공통점
     * 해당 어노테이션이 기술된 클래스를 빈으로 등록 시킴
     * 내부의 @Bean 어노테이션이 붙은 클래스들을 스프링컨텍스트에 등록 시킴
     *
     * @Component 와 @Configuration 차이점
     * @Configuration이 붙은 Config Class에서는 simpleBean() 호출 시 스프링 컨텍스트에 등록되어 있는 SimpleBean 클래스를 반환
     * @Component가 붙은 Config Class에서는 스프링 컨텍스트에 등록되어 있는 빈이 반환되는 것이 아니라 새로 생성된 빈이 반환됨
     *
     * 결과 확인
     * ConfigurationTest 테스트 진행 시 SimpleBean is being created 한 번 출력되는 것을 확인할 수 있음.
     * -> 즉 최소 빈 등록시 한번 호출된 것
     * 반면, ComponentTest 테스트 진행 시 SimpleBean is being created 두 번 출력되는 것을 확인함
     * -> 빈 등록 시 한 번, 이 후 스프링 컨텍스트에 등록되어 있는 빈이 반환 되는 것이 아닌 새로 생성되므로 두 번 출력되는 것.
     */
    @Configuration
    class Config {
        @Bean
        fun simpleBean(): SimpleBean {
            return SimpleBean()
        }

        @Bean
        fun simpleBeanConsumer(): SimpleBeanConsumer {
            return SimpleBeanConsumer(simpleBean())
        }
    }
}