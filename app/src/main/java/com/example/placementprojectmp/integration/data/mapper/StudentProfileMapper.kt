package com.example.placementprojectmp.integration.data.mapper

import com.example.placementprojectmp.integration.data.dto.PlatformLinkDto
import com.example.placementprojectmp.integration.data.dto.SkillsDto
import com.example.placementprojectmp.integration.data.dto.StudentProfileResponseDto
import com.example.placementprojectmp.integration.domain.model.PlatformLinkModel
import com.example.placementprojectmp.integration.domain.model.SkillsModel
import com.example.placementprojectmp.integration.domain.model.StudentProfileModel

fun StudentProfileResponseDto.toDomain(): StudentProfileModel {
    return StudentProfileModel(
        id = id,
        userId = userId,
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
        platformLinks = platformLinks.map { it.toDomain() }
    )
}

private fun SkillsDto.toDomain(): SkillsModel {
    return SkillsModel(
        languages = languages.orEmpty(),
        frameworks = frameworks.orEmpty(),
        tools = tools.orEmpty(),
        platforms = platforms.orEmpty()
    )
}

private fun PlatformLinkDto.toDomain(): PlatformLinkModel {
    return PlatformLinkModel(
        id = id,
        studentId = studentId,
        platformType = platformType,
        url = url
    )
}

