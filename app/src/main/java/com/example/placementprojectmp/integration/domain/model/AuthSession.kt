package com.example.placementprojectmp.integration.domain.model

data class AuthSession(
    val token: String,
    val email: String,
    val roles: List<String>
)

