package com.android.kawsayapp

enum class MessageSender {
    USER,
    BOT
}

data class ChatMessage(
    val text: String,
    val sender: MessageSender
)