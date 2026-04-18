package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.StaffProfileCreateRequest
import com.example.placementprojectmp.data.remote.dto.StaffProfileResponse
import com.example.placementprojectmp.data.remote.dto.StaffProfileUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StaffProfilesApi {
    @GET("/api/staff-profiles")
    suspend fun listStaffProfiles(): Response<List<StaffProfileResponse>>

    @GET("/api/staff-profiles/{id}")
    suspend fun getStaffProfile(@Path("id") id: Long): Response<StaffProfileResponse>

    @POST("/api/staff-profiles")
    suspend fun createStaffProfile(@Body body: StaffProfileCreateRequest): Response<StaffProfileResponse>

    @PUT("/api/staff-profiles/{id}")
    suspend fun updateStaffProfile(
        @Path("id") id: Long,
        @Body body: StaffProfileUpdateRequest
    ): Response<StaffProfileResponse>

    @DELETE("/api/staff-profiles/{id}")
    suspend fun deleteStaffProfile(@Path("id") id: Long): Response<ResponseBody>
}
