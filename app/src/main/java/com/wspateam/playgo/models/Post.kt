package com.wspateam.playgo.models

import java.io.Serializable

data class Post(
    var uid: String? = "",
    var game: String? = "",
    var author: String? = "",
    var title: String? = "",
    var body: String? = "",
    var date: String? = "",
    var repliesCount: Int? = 0
) : Serializable