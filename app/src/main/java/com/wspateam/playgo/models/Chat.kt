package com.wspateam.playgo.models

data class Chat(
		var chatUid: String? = "",
		var messages: MutableList<Message>? = mutableListOf()
)