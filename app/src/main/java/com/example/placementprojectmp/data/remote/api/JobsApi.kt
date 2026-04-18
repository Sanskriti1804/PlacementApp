package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.JobCreateRequest
import com.example.placementprojectmp.data.remote.dto.JobResponse
import com.example.placementprojectmp.data.remote.dto.JobUpdateRequest
import com.example.placementprojectmp.data.remote.dto.SelectionRoundResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JobsApi {
    @GET("/api/jobs/{id}")
    suspend fun getJob(@Path("id") id: String): Response<JobResponse>

    @GET("/api/jobs")
    suspend fun listJobs(): Response<List<JobResponse>>

    @POST("/api/jobs")
    suspend fun createJob(@Body body: JobCreateRequest): Response<JobResponse>

    @PUT("/api/jobs/{id}")
    suspend fun updateJob(@Path("id") id: Long, @Body body: JobUpdateRequest): Response<JobResponse>

    @DELETE("/api/jobs/{id}")
    suspend fun deleteJob(@Path("id") id: Long): Response<ResponseBody>

    @GET("/api/jobs/{jobId}/selection-rounds")
    suspend fun listSelectionRoundsForJob(@Path("jobId") jobId: Long): Response<List<SelectionRoundResponse>>
}
