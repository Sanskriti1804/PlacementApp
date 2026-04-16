package com.example.placementprojectmp.integration.domain.model

import com.example.placementprojectmp.integration.data.dto.PlatformType

data class StudentProfileModel(
    val id: Long,
    val userId: Long,
    val name: String?,
    val domainRole: String?,
    val bio: String?,
    val dob: String?,
    val phoneNumber: String?,
    val photoUrl: String?,
    val addressLine: String?,
    val city: String?,
    val state: String?,
    val skills: SkillsModel?,
    val platformLinks: List<PlatformLinkModel>
)

data class SkillsModel(
    val languages: List<String>,
    val frameworks: List<String>,
    val tools: List<String>,
    val platforms: List<String>
)

data class PlatformLinkModel(
    val id: Long,
    val studentId: Long,
    val platformType: PlatformType,
    val url: String
)

