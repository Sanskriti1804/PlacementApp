package com.example.placementprojectmp.integration.data.api

import com.example.placementprojectmp.integration.data.dto.PlatformLinkDto
import com.example.placementprojectmp.integration.data.dto.PlatformLinkRequestDto
import com.example.placementprojectmp.integration.data.dto.StudentProfileRequestDto
import com.example.placementprojectmp.integration.data.dto.StudentProfileResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentProfileApi {
    @POST("/api/students/{userId}/profile")
    suspend fun createProfile(
        @Path("userId") userId: Long,
        @Body request: StudentProfileRequestDto
    ): Response<StudentProfileResponseDto>

    @GET("/api/students/{studentId}/profile")
    suspend fun getProfile(@Path("studentId") studentId: Long): Response<StudentProfileResponseDto>

    @POST("/api/students/{studentId}/platforms")
    suspend fun addPlatform(
        @Path("studentId") studentId: Long,
        @Body request: PlatformLinkRequestDto
    ): Response<PlatformLinkDto>
}

