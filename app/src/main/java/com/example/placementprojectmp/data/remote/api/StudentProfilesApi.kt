package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.StudentProfileRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentProfilesApi {
    @GET("/api/student-profiles")
    suspend fun listStudentProfiles(): Response<List<StudentProfileResponse>>

    @GET("/api/student-profiles/{id}")
    suspend fun getStudentProfile(@Path("id") id: Long): Response<StudentProfileResponse>

    @POST("/api/student-profiles")
    suspend fun createStudentProfile(
        @Query("userId") userId: Long,
        @Body body: StudentProfileRequest
    ): Response<StudentProfileResponse>

    @PUT("/api/student-profiles/{id}")
    suspend fun updateStudentProfile(
        @Path("id") id: Long,
        @Body body: StudentProfileRequest
    ): Response<StudentProfileResponse>

    @DELETE("/api/student-profiles/{id}")
    suspend fun deleteStudentProfile(@Path("id") id: Long): Response<ResponseBody>
}
