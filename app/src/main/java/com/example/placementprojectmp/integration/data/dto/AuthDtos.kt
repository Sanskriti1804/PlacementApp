package com.example.placementprojectmp.integration.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDto(
    val email: String,
    val password: String,
    val role: RoleType
)

@Serializable
data class AuthResponseDto(
    val token: String,
    val email: String,
    val roles: List<String>
)

@Serializable
data class RegisterStudentRequestDto(
    val email: String,
    val password: String,
    val passwordBased: Boolean = true,
    val role: RoleType = RoleType.STUDENT
)

@Serializable
data class ErrorResponseDto(
    @SerialName("message") val message: String? = null,
    @SerialName("error") val error: String? = null
)

