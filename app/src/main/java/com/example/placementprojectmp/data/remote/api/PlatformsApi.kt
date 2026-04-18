package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.PlatformLinkRequest
import com.example.placementprojectmp.data.remote.dto.PlatformResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PlatformsApi {
    @GET("/api/platforms")
    suspend fun listPlatforms(): Response<List<PlatformResponse>>

    @GET("/api/platforms/{id}")
    suspend fun getPlatform(@Path("id") id: Long): Response<PlatformResponse>

    @POST("/api/platforms")
    suspend fun createPlatform(
        @Query("studentProfileId") studentProfileId: Long,
        @Body body: PlatformLinkRequest
    ): Response<PlatformResponse>

    @PUT("/api/platforms/{id}")
    suspend fun updatePlatform(@Path("id") id: Long, @Body body: PlatformLinkRequest): Response<PlatformResponse>

    @DELETE("/api/platforms/{id}")
    suspend fun deletePlatform(@Path("id") id: Long): Response<ResponseBody>
}
