package com.example.blogmultiplatform.api

import com.example.blogmultiplatform.data.MongoDB
import com.example.blogmultiplatform.models.User
import com.example.blogmultiplatform.models.UserWithoutPassword
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Api(routeOverride = "userCheck")
suspend fun userCheck(context: ApiContext) {
    try {
        val userRequest = context.req.body?.decodeToString()?.let { Json.decodeFromString<User>(it) }
        userRequest?.let {
            context.data.getValue<MongoDB>().checkUserExistence(
                user = User(_id = it._id, username = it.username, password = hashPassword(it.password)),
            )
            context.res.setBodyText(
                Json.encodeToString(
                    UserWithoutPassword(_id = it._id, username = it.username),
                ),
            )
        } ?: context.res.setBodyText(Json.encodeToString(Exception("User doesn't exist")))
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(e.message ?: "Exception empty UserCheck"))
    }
}

private fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
    val hexString = StringBuffer()

    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }

    return hexString.toString()
}