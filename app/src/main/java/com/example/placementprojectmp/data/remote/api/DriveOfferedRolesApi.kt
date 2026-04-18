package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.DriveOfferedRoleCreateRequest
import com.example.placementprojectmp.data.remote.dto.DriveOfferedRoleResponse
import com.example.placementprojectmp.data.remote.dto.DriveOfferedRoleUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DriveOfferedRolesApi {
    @GET("/api/drive-offered-roles")
    suspend fun listDriveOfferedRoles(): Response<List<DriveOfferedRoleResponse>>

    @GET("/api/drive-offered-roles/{id}")
    suspend fun getDriveOfferedRole(@Path("id") id: Long): Response<DriveOfferedRoleResponse>

    @POST("/api/drive-offered-roles")
    suspend fun createDriveOfferedRole(@Body body: DriveOfferedRoleCreateRequest): Response<DriveOfferedRoleResponse>

    @PUT("/api/drive-offered-roles/{id}")
    suspend fun updateDriveOfferedRole(
        @Path("id") id: Long,
        @Body body: DriveOfferedRoleUpdateRequest
    ): Response<DriveOfferedRoleResponse>

    @DELETE("/api/drive-offered-roles/{id}")
    suspend fun deleteDriveOfferedRole(@Path("id") id: Long): Response<ResponseBody>
}
