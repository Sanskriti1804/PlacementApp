package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.JobSelectionRoundCreateRequest
import com.example.placementprojectmp.data.remote.dto.JobSelectionRoundUpdateRequest
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

interface JobSelectionRoundsApi {
    @GET("/api/job-selection-rounds")
    suspend fun listJobSelectionRounds(): Response<List<SelectionRoundResponse>>

    @GET("/api/job-selection-rounds/{id}")
    suspend fun getJobSelectionRound(@Path("id") id: Long): Response<SelectionRoundResponse>

    @POST("/api/job-selection-rounds")
    suspend fun createJobSelectionRound(
        @Query("jobId") jobId: Long,
        @Body body: JobSelectionRoundCreateRequest
    ): Response<SelectionRoundResponse>

    @PUT("/api/job-selection-rounds/{id}")
    suspend fun updateJobSelectionRound(
        @Path("id") id: Long,
        @Body body: JobSelectionRoundUpdateRequest
    ): Response<SelectionRoundResponse>

    @DELETE("/api/job-selection-rounds/{id}")
    suspend fun deleteJobSelectionRound(@Path("id") id: Long): Response<ResponseBody>
}
