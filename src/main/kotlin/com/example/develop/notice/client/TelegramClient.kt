package com.example.develop.notice.client

import com.example.develop.notice.config.FeignLoggingConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "telegram", url = "https://api.telegram.org", configuration = [FeignLoggingConfig::class])
interface TelegramClient {

    @GetMapping(value = ["/bot{token}/sendmessage"])
    fun sendMessage(
        @RequestParam("text") text: String,
        @PathVariable("token") token: String,
        @RequestParam("chat_id") chatId: String
    ): Any

}
