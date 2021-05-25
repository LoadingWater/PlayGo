package com.wspateam.playgo.models

data class Post(
    var uid: String? = "",
    var author: String? = "",
    var title: String? = "",
    var body: String? = "",
    var date: String? = "",
    var replies: List<String>? = mutableListOf()
)