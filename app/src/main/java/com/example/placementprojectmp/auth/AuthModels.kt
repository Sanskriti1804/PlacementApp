package com.example.placementprojectmp.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val email: String,
    val password: String,
    val role: String
)

@Serializable
data class AuthResponse(
    val token: String,
    val email: String,
    val roles: List<String>
)

enum class AuthRole {
    STUDENT,
    STAFF;

    companion object {
        fun fromInput(value: String): AuthRole =
            entries.firstOrNull { it.name.equals(value.trim(), ignoreCase = true) } ?: STUDENT
    }
}
