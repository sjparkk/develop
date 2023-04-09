package com.example.develop.notice.service.custom

import com.example.develop.notice.client.custom.TelegramCustomClient
import com.example.develop.notice.event.NoticeEvent
import com.example.develop.notice.service.NoticeService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class TelegramNotificationService(
    private val telegramCustomClient: TelegramCustomClient
): NoticeService {

    @Value("\${telegram.bot.token}")
    private val telegramBotToken: String? = null

    @Value("\${telegram.bot.chat-id}")
    private val telegramBotChatId: String? = null

    @EventListener(NoticeEvent::class)
    override fun sendMessage(event: NoticeEvent): Any {

        if(telegramBotToken?.isBlank() == true || telegramBotChatId?.isBlank() == true)
            throw Exception("Telegram Token or ChatId reconfirm")

        val message = ":: 전달 Message - ${event.message}"
        return telegramCustomClient.sendMessage(message, telegramBotToken.toString(), telegramBotChatId.toString())
    }

}