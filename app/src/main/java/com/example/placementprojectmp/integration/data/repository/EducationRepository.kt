package com.example.placementprojectmp.integration.data.repository

import com.example.placementprojectmp.integration.data.api.EducationApi
import com.example.placementprojectmp.integration.data.dto.EducationRequestDto
import com.example.placementprojectmp.integration.data.dto.EducationResponseDto
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.remote.safeApiCall
import kotlinx.serialization.json.Json

class EducationRepository(
    private val api: EducationApi,
    private val json: Json
) {
    suspend fun createOrUpdateEducation(
        studentId: Long,
        request: EducationRequestDto
    ): ApiResult<EducationResponseDto> = safeApiCall(json) {
        api.createOrUpdateEducation(studentId, request)
    }

    suspend fun getEducation(studentId: Long): ApiResult<EducationResponseDto> = safeApiCall(json) {
        api.getEducation(studentId)
    }
}

