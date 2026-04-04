package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.model.User
import com.example.placementprojectmp.data.remote.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class UserApiImpl(
    private val client: HttpClient,
    private val json: Json
) : UserApi {

    override suspend fun getUser(): List<User> {
        val responseText = client
            .get("${NetworkConfig.BASE_URL}/students")
            .bodyAsText()
        return json.decodeFromString(ListSerializer(User.serializer()), responseText)
    }
}