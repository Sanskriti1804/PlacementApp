package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.auth.RegisterRequest
import com.example.placementprojectmp.data.remote.dto.UserResponse
import com.example.placementprojectmp.data.remote.dto.UserUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApi {
    @GET("/api/users")
    suspend fun listUsers(): Response<List<UserResponse>>

    @GET("/api/users/{id}")
    suspend fun getUser(@Path("id") id: Long): Response<UserResponse>

    @POST("/api/users")
    suspend fun createUser(@Body body: RegisterRequest): Response<UserResponse>

    @PUT("/api/users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body body: UserUpdateRequest): Response<UserResponse>

    @DELETE("/api/users/{id}")
    suspend fun deleteUser(@Path("id") id: Long): Response<ResponseBody>
}
