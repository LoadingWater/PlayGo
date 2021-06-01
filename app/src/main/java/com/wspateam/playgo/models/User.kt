package com.wspateam.playgo.models

import java.io.Serializable

data class User(
  var uid: String? = "",
  var username: String? = "",
  var email: String? = "",
  var password: String? = "",
  var postedPosts: MutableList<String> = mutableListOf()
 ) : Serializable
