package com.example.placementprojectmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EducationProfile(
    val id: Long? = null,
    val university: String,
    val branch: String,
    val course: String,
    val domain: String,
    val currentYear: Int,
    val tenthPercentage: Double,
    val twelfthPercentage: Double,
    val currentCgpa: Double,
    val gapYears: Int? = null,
    val gapReason: String? = null,
    val backlogs: List<Backlog> = emptyList()
)

@Serializable
data class Backlog(
    val id: Long? = null,
    val subject: String,
    val semester: Int
)