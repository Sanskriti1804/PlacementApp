package com.example.placementprojectmp.integration.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EducationRequestDto(
    val id: Long? = null,
    val university: String? = null,
    val branch: String? = null,
    val course: String? = null,
    val domain: String? = null,
    @SerialName("currentYear") val currentYear: Int? = null,
    @SerialName("tenthPercentage") val tenthPercentage: Double? = null,
    @SerialName("twelfthPercentage") val twelfthPercentage: Double? = null,
    @SerialName("currentCgpa") val currentCgpa: Double? = null,
    @SerialName("gapYears") val gapYears: Int? = null,
    @SerialName("gapReason") val gapReason: String? = null,
    @SerialName("tenth_school_name") val tenthSchoolName: String? = null,
    @SerialName("twelfth_school_name") val twelfthSchoolName: String? = null,
    @SerialName("graduation_college_name") val graduationCollegeName: String? = null,
    @SerialName("post_graduation_college_name") val postGraduationCollegeName: String? = null,
    val backlogs: List<BacklogDto> = emptyList()
)

@Serializable
data class EducationResponseDto(
    val id: Long? = null,
    val university: String? = null,
    val branch: String? = null,
    val course: String? = null,
    val domain: String? = null,
    @SerialName("currentYear") val currentYear: Int? = null,
    @SerialName("tenthPercentage") val tenthPercentage: Double? = null,
    @SerialName("twelfthPercentage") val twelfthPercentage: Double? = null,
    @SerialName("currentCgpa") val currentCgpa: Double? = null,
    @SerialName("gapYears") val gapYears: Int? = null,
    @SerialName("gapReason") val gapReason: String? = null,
    @SerialName("tenth_school_name") val tenthSchoolName: String? = null,
    @SerialName("twelfth_school_name") val twelfthSchoolName: String? = null,
    @SerialName("graduation_college_name") val graduationCollegeName: String? = null,
    @SerialName("post_graduation_college_name") val postGraduationCollegeName: String? = null,
    val backlogs: List<BacklogDto> = emptyList()
)

@Serializable
data class BacklogDto(
    val id: Long? = null,
    val subject: String,
    val semester: Int
)

