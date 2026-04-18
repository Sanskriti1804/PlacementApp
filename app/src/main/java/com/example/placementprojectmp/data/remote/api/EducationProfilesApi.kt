package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.EducationProfileRequest
import com.example.placementprojectmp.data.remote.dto.EducationResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EducationProfilesApi {
    @GET("/api/education-profiles")
    suspend fun listEducationProfiles(): Response<List<EducationResponse>>

    @GET("/api/education-profiles/{id}")
    suspend fun getEducationProfile(@Path("id") id: Long): Response<EducationResponse>

    @POST("/api/education-profiles")
    suspend fun createEducationProfile(@Body body: EducationProfileRequest): Response<EducationResponse>

    @PUT("/api/education-profiles/{id}")
    suspend fun updateEducationProfile(
        @Path("id") id: Long,
        @Body body: EducationProfileRequest
    ): Response<EducationResponse>

    @DELETE("/api/education-profiles/{id}")
    suspend fun deleteEducationProfile(@Path("id") id: Long): Response<ResponseBody>
}
