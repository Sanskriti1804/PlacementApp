package com.example.placementprojectmp.integration.data.api

import com.example.placementprojectmp.integration.data.dto.AuthRequestDto
import com.example.placementprojectmp.integration.data.dto.AuthResponseDto
import com.example.placementprojectmp.integration.data.dto.RegisterStudentRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequestDto): Response<AuthResponseDto>

    @POST("/api/auth/access")
    suspend fun access(@Body request: AuthRequestDto): Response<AuthResponseDto>

    @POST("/api/auth/register/student")
    suspend fun registerStudent(@Body request: RegisterStudentRequestDto): Response<AuthResponseDto>
}

