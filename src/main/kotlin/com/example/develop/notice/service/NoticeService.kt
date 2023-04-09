package com.example.develop.notice.service

import com.example.develop.notice.event.NoticeEvent

interface NoticeService {
    fun sendMessage(event: NoticeEvent): Any
}