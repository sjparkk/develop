package com.example.develop.notice.client.custom

import com.example.develop.notice.client.TelegramClient
import feign.FeignException
import org.springframework.stereotype.Component

@Component
class TelegramCustomClient(private val telegramClient: TelegramClient) {

    fun sendMessage(message: String, token: String, chatId: String): Any {
        try {
            return telegramClient.sendMessage(message, token, chatId)
        } catch (exception: FeignException.FeignClientException) {
            throw Exception(":: feign client exception")
        } catch (exception: FeignException.FeignServerException) {
            throw Exception(":: feign server exception")
        }
    }


}