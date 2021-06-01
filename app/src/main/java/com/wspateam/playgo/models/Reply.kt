package com.wspateam.playgo.models

import java.io.Serializable

data class Reply(
    var uid: String? = "",
    var postUid: String? = "",
    var author: String? = "",
    var body: String? = "",
    var date: String? = ""
) : Serializable
