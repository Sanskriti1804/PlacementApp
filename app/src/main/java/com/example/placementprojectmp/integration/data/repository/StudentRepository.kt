package com.example.placementprojectmp.integration.data.repository

import com.example.placementprojectmp.data.remote.dto.PlatformLinkRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileRequest
import com.example.placementprojectmp.integration.data.api.StudentProfileApi
import com.example.placementprojectmp.integration.data.dto.PlatformType
import com.example.placementprojectmp.integration.data.mapper.toDomain
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.remote.safeApiCall
import com.example.placementprojectmp.integration.domain.model.PlatformLinkModel
import com.example.placementprojectmp.integration.domain.model.StudentProfileModel
import kotlinx.serialization.json.Json

class StudentRepository(
    private val api: StudentProfileApi,
    private val json: Json
) {
    suspend fun createProfile(
        userId: Long,
        request: StudentProfileRequest
    ): ApiResult<StudentProfileModel> {
        return when (val result = safeApiCall(json) { api.createProfile(userId, request) }) {
            is ApiResult.Success -> ApiResult.Success(result.data.toDomain())
            is ApiResult.Error -> result
        }
    }

    suspend fun getProfile(studentId: Long): ApiResult<StudentProfileModel> {
        return when (val result = safeApiCall(json) { api.getProfile(studentId) }) {
            is ApiResult.Success -> ApiResult.Success(result.data.toDomain())
            is ApiResult.Error -> result
        }
    }

    suspend fun addPlatform(
        studentId: Long,
        type: PlatformType,
        url: String
    ): ApiResult<PlatformLinkModel> {
        return when (
            val result = safeApiCall(json) {
                api.addPlatform(studentId, PlatformLinkRequest(type = type.name, url = url))
            }
        ) {
            is ApiResult.Success -> {
                val body = result.data
                ApiResult.Success(
                    PlatformLinkModel(
                        id = body.id ?: 0L,
                        studentId = studentId,
                        platformType = type,
                        url = body.url.orEmpty()
                    )
                )
            }

            is ApiResult.Error -> result
        }
    }
}
