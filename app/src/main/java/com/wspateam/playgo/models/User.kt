package com.wspateam.playgo.models

import java.io.Serializable

data class User(
        var uid: String? = "",
        var username: String? = "",
        var email: String? = "",
        var password: String? = "",
        var status: String? = "offline",
        var postedPosts: MutableList<String> = mutableListOf()
) : Serializable
{
     override fun toString(): String
     {
          return "UID: $uid, USERNAME: $username, EMAIL: $email, PASSWORD: $password, STATUS: $status."
     }
}

