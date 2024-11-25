package com.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import org.litote.kmongo.id.ObjectIdGenerator

data class User(
    @SerialName(value = "_id")
    val id: String = ObjectIdGenerator.newObjectId<String>().id.toHexString(),
    val username: String = "",
    val password: String = "",
)
