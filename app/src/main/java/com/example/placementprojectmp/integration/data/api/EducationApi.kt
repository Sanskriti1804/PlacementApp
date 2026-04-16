package com.example.placementprojectmp.integration.data.api

import com.example.placementprojectmp.integration.data.dto.EducationRequestDto
import com.example.placementprojectmp.integration.data.dto.EducationResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EducationApi {
    @POST("/api/students/{studentId}/education")
    suspend fun createOrUpdateEducation(
        @Path("studentId") studentId: Long,
        @Body request: EducationRequestDto
    ): Response<EducationResponseDto>

    @GET("/api/students/{studentId}/education")
    suspend fun getEducation(@Path("studentId") studentId: Long): Response<EducationResponseDto>
}

