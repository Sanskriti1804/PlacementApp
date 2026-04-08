package com.example.placementprojectmp.network

import com.example.placementprojectmp.auth.AuthRequest
import com.example.placementprojectmp.auth.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/access")
    suspend fun access(@Body request: AuthRequest): Response<AuthResponse>
}
