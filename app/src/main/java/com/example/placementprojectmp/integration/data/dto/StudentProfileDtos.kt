package com.example.placementprojectmp.integration.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentProfileRequestDto(
    val userId: Long,
    val name: String? = null,
    val domainRole: String? = null,
    val bio: String? = null,
    val dob: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val addressLine: String? = null,
    val city: String? = null,
    val state: String? = null,
    val skills: SkillsDto? = null
)

@Serializable
data class StudentProfileResponseDto(
    val id: Long,
    @SerialName("user_id") val userId: Long,
    val name: String? = null,
    @SerialName("domain_role") val domainRole: String? = null,
    val bio: String? = null,
    val dob: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("photo_url") val photoUrl: String? = null,
    @SerialName("address_line") val addressLine: String? = null,
    val city: String? = null,
    val state: String? = null,
    val skills: SkillsDto? = null,
    @SerialName("platform_links") val platformLinks: List<PlatformLinkDto> = emptyList()
)

@Serializable
data class SkillsDto(
    val id: Long? = null,
    @SerialName("student_id") val studentId: Long? = null,
    val languages: List<String>? = null,
    val frameworks: List<String>? = null,
    val tools: List<String>? = null,
    val platforms: List<String>? = null
)

@Serializable
data class PlatformLinkRequestDto(
    @SerialName("platform_type") val platformType: PlatformType,
    val url: String
)

@Serializable
data class PlatformLinkDto(
    val id: Long,
    @SerialName("student_id") val studentId: Long,
    @SerialName("platform_type") val platformType: PlatformType,
    val url: String
)

