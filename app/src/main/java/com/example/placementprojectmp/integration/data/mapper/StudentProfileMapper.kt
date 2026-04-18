package com.example.placementprojectmp.integration.data.mapper

import com.example.placementprojectmp.data.remote.dto.PlatformResponse
import com.example.placementprojectmp.data.remote.dto.SkillResponse
import com.example.placementprojectmp.data.remote.dto.StudentProfileResponse
import com.example.placementprojectmp.integration.data.dto.PlatformType
import com.example.placementprojectmp.integration.domain.model.PlatformLinkModel
import com.example.placementprojectmp.integration.domain.model.SkillsModel
import com.example.placementprojectmp.integration.domain.model.StudentProfileModel

fun StudentProfileResponse.toDomain(): StudentProfileModel {
    val uid = user?.id ?: 0L
    val sid = id ?: 0L
    return StudentProfileModel(
        id = sid,
        userId = uid,
        name = name,
        domainRole = domainRole,
        bio = bio,
        dob = dob,
        phoneNumber = phoneNumber,
        photoUrl = photoUrl,
        addressLine = addressLine,
        city = city,
        state = state,
        skills = skills?.toDomain(),
        platformLinks = platformLinks.orEmpty().map { it.toDomain(sid) }
    )
}

private fun SkillResponse.toDomain(): SkillsModel {
    return SkillsModel(
        languages = stringToParts(languages),
        frameworks = stringToParts(frameworks),
        tools = stringToParts(tools),
        platforms = stringToParts(platforms)
    )
}

private fun stringToParts(value: String?): List<String> {
    if (value.isNullOrBlank()) return emptyList()
    return value.split(',').map { it.trim() }.filter { it.isNotEmpty() }
}

private fun PlatformResponse.toDomain(fallbackStudentId: Long): PlatformLinkModel {
    val type = runCatching { PlatformType.valueOf(type.orEmpty()) }.getOrDefault(PlatformType.RESUME)
    return PlatformLinkModel(
        id = id ?: 0L,
        studentId = fallbackStudentId,
        platformType = type,
        url = url.orEmpty()
    )
}
