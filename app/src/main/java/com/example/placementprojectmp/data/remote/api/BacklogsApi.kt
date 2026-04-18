package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.BacklogRequest
import com.example.placementprojectmp.data.remote.dto.BacklogResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BacklogsApi {
    @GET("/api/backlogs")
    suspend fun listBacklogs(): Response<List<BacklogResponse>>

    @GET("/api/backlogs/{id}")
    suspend fun getBacklog(@Path("id") id: Long): Response<BacklogResponse>

    @POST("/api/backlogs")
    suspend fun createBacklog(
        @Query("educationProfileId") educationProfileId: Long,
        @Body body: BacklogRequest? = null
    ): Response<BacklogResponse>

    @PUT("/api/backlogs/{id}")
    suspend fun updateBacklog(@Path("id") id: Long, @Body body: BacklogRequest? = null): Response<BacklogResponse>

    @DELETE("/api/backlogs/{id}")
    suspend fun deleteBacklog(@Path("id") id: Long): Response<ResponseBody>
}
