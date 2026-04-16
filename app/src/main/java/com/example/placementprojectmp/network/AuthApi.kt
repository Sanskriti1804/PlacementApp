package com.example.placementprojectmp.network

import com.example.placementprojectmp.auth.AuthRequest
import com.example.placementprojectmp.auth.AuthResponse
import com.example.placementprojectmp.auth.RegisterStudentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @POST("/api/auth/access")
    suspend fun access(@Body request: AuthRequest): Response<AuthResponse>

    @POST("/api/auth/register/student")
    suspend fun registerStudent(@Body request: RegisterStudentRequest): Response<AuthResponse>
}
