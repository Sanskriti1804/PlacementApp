package com.example.placementprojectmp.integration.data.api

import com.example.placementprojectmp.data.remote.dto.PlatformLinkRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentProfileApi {
    @POST("/api/students/{userId}/profile")
    suspend fun createProfile(
        @Path("userId") userId: Long,
        @Body request: StudentProfileRequest
    ): Response<StudentProfileResponse>

    @GET("/api/students/{studentId}/profile")
    suspend fun getProfile(@Path("studentId") studentId: Long): Response<StudentProfileResponse>

    @POST("/api/students/{studentId}/platforms")
    suspend fun addPlatform(
        @Path("studentId") studentId: Long,
        @Body request: PlatformLinkRequest
    ): Response<com.example.placementprojectmp.data.remote.dto.PlatformResponse>
}
