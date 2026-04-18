package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.JobApplicationCreateRequest
import com.example.placementprojectmp.data.remote.dto.JobApplicationResponse
import com.example.placementprojectmp.data.remote.dto.JobApplicationUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApplicationsApi {
    @GET("/api/applications")
    suspend fun listApplications(): Response<List<JobApplicationResponse>>

    @GET("/api/applications/{id}")
    suspend fun getApplication(@Path("id") id: Long): Response<JobApplicationResponse>

    @POST("/api/applications")
    suspend fun createApplication(@Body body: JobApplicationCreateRequest): Response<JobApplicationResponse>

    @PUT("/api/applications/{id}")
    suspend fun updateApplication(
        @Path("id") id: Long,
        @Body body: JobApplicationUpdateRequest
    ): Response<JobApplicationResponse>

    @DELETE("/api/applications/{id}")
    suspend fun deleteApplication(@Path("id") id: Long): Response<ResponseBody>
}
