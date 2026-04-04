package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.model.User
import com.example.placementprojectmp.data.remote.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

class UserApiImpl(
    private val client: HttpClient,
    private val json: Json
) : UserApi {

    override suspend fun getUser(): List<User> {
        val responseText = client
            .get("${NetworkConfig.BASE_URL}/api/students/3/profile")
            .bodyAsText()
        return runCatching {
            val root = json.parseToJsonElement(responseText).jsonObject
            val userEl = root["user"] ?: return emptyList()
            val dto = json.decodeFromJsonElement(ProfileUserDto.serializer(), userEl)
            listOf(dto.toUser())
        }.getOrElse { emptyList() }
    }
}

@Serializable
private data class ProfileUserDto(
    val id: Int,
    val email: String? = null,
    val name: String,
    val phone: String? = null,
    val roles: List<ProfileRoleDto> = emptyList()
)

@Serializable
private data class ProfileRoleDto(
    val id: Long = 0,
    val roleName: String = ""
)

private fun ProfileUserDto.toUser(): User {
    val derivedUsername = email?.substringBefore('@')?.takeIf { it.isNotBlank() }
        ?: name.lowercase().filter { it.isLetterOrDigit() }.ifEmpty { "user$id" }
    val roleLabel = roles.firstOrNull()?.roleName?.takeIf { it.isNotBlank() }
        ?: "Student"
    return User(
        id = id,
        username = derivedUsername,
        name = name,
        role = roleLabel
    )
}
