package com.example.placementprojectmp.integration.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RoleType {
    STUDENT,
    STAFF,
    SYSTEM
}

@Serializable
enum class PlatformType {
    GITHUB,
    LINKEDIN,
    RESUME,
    PORTFOLIO,
    BEHANCE,
    DRIBBBLE,
    LEETCODE
}

@Serializable
enum class AuthMode {
    @SerialName("access")
    ACCESS,
    @SerialName("login")
    LOGIN
}

