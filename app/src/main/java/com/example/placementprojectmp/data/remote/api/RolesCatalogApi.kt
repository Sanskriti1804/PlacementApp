package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.RoleCreateRequest
import com.example.placementprojectmp.data.remote.dto.RoleResponse
import com.example.placementprojectmp.data.remote.dto.RoleUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/** Role **entity** catalog at `/api/roles` — not the same as account RoleType on [com.example.placementprojectmp.auth.AuthResponse.roles]. */
interface RolesCatalogApi {
    @GET("/api/roles")
    suspend fun listRoles(): Response<List<RoleResponse>>

    @GET("/api/roles/{id}")
    suspend fun getRole(@Path("id") id: Long): Response<RoleResponse>

    @POST("/api/roles")
    suspend fun createRole(@Body body: RoleCreateRequest): Response<RoleResponse>

    @PUT("/api/roles/{id}")
    suspend fun updateRole(@Path("id") id: Long, @Body body: RoleUpdateRequest): Response<RoleResponse>

    @DELETE("/api/roles/{id}")
    suspend fun deleteRole(@Path("id") id: Long): Response<ResponseBody>
}
