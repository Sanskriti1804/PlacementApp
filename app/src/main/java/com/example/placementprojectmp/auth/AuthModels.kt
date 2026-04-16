package com.example.placementprojectmp.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val email: String,
    val password: String,
    val role: String
)

@Serializable
data class RegisterStudentRequest(
    val email: String,
    val password: String,
    val passwordBased: Boolean,
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
    STAFF,
    SYSTEM;

    companion object {
        fun fromInput(value: String): AuthRole =
            when (value.trim().uppercase()) {
                "STUDENT" -> STUDENT
                "STAFF", "ADMIN", "MANAGEMENT" -> STAFF
                "SYSTEM" -> SYSTEM
                else -> STUDENT
            }
    }
}
