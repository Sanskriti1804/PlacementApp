package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.StudentExperienceCreateRequest
import com.example.placementprojectmp.data.remote.dto.StudentExperienceResponse
import com.example.placementprojectmp.data.remote.dto.StudentExperienceUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentExperiencesApi {
    @GET("/api/student-experiences")
    suspend fun listExperiences(): Response<List<StudentExperienceResponse>>

    @GET("/api/student-experiences/{id}")
    suspend fun getExperience(@Path("id") id: Long): Response<StudentExperienceResponse>

    @POST("/api/student-experiences")
    suspend fun createExperience(@Body body: StudentExperienceCreateRequest): Response<StudentExperienceResponse>

    @PUT("/api/student-experiences/{id}")
    suspend fun updateExperience(
        @Path("id") id: Long,
        @Body body: StudentExperienceUpdateRequest
    ): Response<StudentExperienceResponse>

    @DELETE("/api/student-experiences/{id}")
    suspend fun deleteExperience(@Path("id") id: Long): Response<ResponseBody>
}
