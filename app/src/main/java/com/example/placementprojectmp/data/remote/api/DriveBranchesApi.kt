package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.DriveBranchCreateRequest
import com.example.placementprojectmp.data.remote.dto.DriveBranchResponse
import com.example.placementprojectmp.data.remote.dto.DriveBranchUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DriveBranchesApi {
    @GET("/api/drive-branches")
    suspend fun listDriveBranches(): Response<List<DriveBranchResponse>>

    @GET("/api/drive-branches/{id}")
    suspend fun getDriveBranch(@Path("id") id: Long): Response<DriveBranchResponse>

    @POST("/api/drive-branches")
    suspend fun createDriveBranch(@Body body: DriveBranchCreateRequest): Response<DriveBranchResponse>

    @PUT("/api/drive-branches/{id}")
    suspend fun updateDriveBranch(
        @Path("id") id: Long,
        @Body body: DriveBranchUpdateRequest
    ): Response<DriveBranchResponse>

    @DELETE("/api/drive-branches/{id}")
    suspend fun deleteDriveBranch(@Path("id") id: Long): Response<ResponseBody>
}
