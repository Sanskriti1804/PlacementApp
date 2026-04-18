package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.JobSelectionRoundCreateRequest
import com.example.placementprojectmp.data.remote.dto.DriveSelectionRoundUpdateRequest
import com.example.placementprojectmp.data.remote.dto.SelectionRoundResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DriveSelectionRoundsApi {
    @GET("/api/drive-selection-rounds")
    suspend fun listDriveSelectionRounds(): Response<List<SelectionRoundResponse>>

    @GET("/api/drive-selection-rounds/{id}")
    suspend fun getDriveSelectionRound(@Path("id") id: Long): Response<SelectionRoundResponse>

    @POST("/api/drive-selection-rounds")
    suspend fun createDriveSelectionRound(
        @Query("driveId") driveId: Long,
        @Body body: JobSelectionRoundCreateRequest
    ): Response<SelectionRoundResponse>

    @PUT("/api/drive-selection-rounds/{id}")
    suspend fun updateDriveSelectionRound(
        @Path("id") id: Long,
        @Body body: DriveSelectionRoundUpdateRequest
    ): Response<SelectionRoundResponse>

    @DELETE("/api/drive-selection-rounds/{id}")
    suspend fun deleteDriveSelectionRound(@Path("id") id: Long): Response<ResponseBody>
}
