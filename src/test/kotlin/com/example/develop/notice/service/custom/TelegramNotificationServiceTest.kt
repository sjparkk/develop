package com.example.develop.notice.service.custom

import com.example.develop.notice.event.NoticeEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TelegramNotificationServiceTest(

) {

    @Autowired
    lateinit var telegramNotificationService: TelegramNotificationService

    @Test
    fun `텔레그램 봇 알림 요청 후 응답값 확인`() {
        val result = telegramNotificationService.sendMessage(NoticeEvent("메시지 전달!"))
        println(result)
    }
}