package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.DriveCreateRequest
import com.example.placementprojectmp.data.remote.dto.DriveResponse
import com.example.placementprojectmp.data.remote.dto.DriveUpdateRequest
import com.example.placementprojectmp.data.remote.dto.SelectionRoundResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DrivesApi {
    @GET("/api/drives/{id}")
    suspend fun getDrive(@Path("id") id: String): Response<DriveResponse>

    @GET("/api/drives")
    suspend fun listDrives(): Response<List<DriveResponse>>

    @POST("/api/drives")
    suspend fun createDrive(@Body body: DriveCreateRequest): Response<DriveResponse>

    @PUT("/api/drives/{id}")
    suspend fun updateDrive(@Path("id") id: Long, @Body body: DriveUpdateRequest): Response<DriveResponse>

    @DELETE("/api/drives/{id}")
    suspend fun deleteDrive(@Path("id") id: Long): Response<ResponseBody>

    @GET("/api/drives/{driveId}/selection-rounds")
    suspend fun listSelectionRoundsForDrive(@Path("driveId") driveId: Long): Response<List<SelectionRoundResponse>>
}
