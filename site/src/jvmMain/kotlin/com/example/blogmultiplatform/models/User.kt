package com.example.blogmultiplatform.models

import org.bson.codecs.ObjectIdGenerator

data class User(
    val _id: String = ObjectIdGenerator().generate().toString(),
    val username: String = "",
    val password: String = "",
)

data class UserWithoutPassword(
    val _id: String = ObjectIdGenerator().generate().toString(),
    val username: String = "",
)
