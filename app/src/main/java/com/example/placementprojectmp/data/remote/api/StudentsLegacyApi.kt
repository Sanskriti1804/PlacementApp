package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.EducationProfileRequest
import com.example.placementprojectmp.data.remote.dto.EducationResponse
import com.example.placementprojectmp.data.remote.dto.PlatformLinkRequest
import com.example.placementprojectmp.data.remote.dto.PlatformResponse
import com.example.placementprojectmp.data.remote.dto.ProjectRequest
import com.example.placementprojectmp.data.remote.dto.ProjectResponse
import com.example.placementprojectmp.data.remote.dto.StudentProfileRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/** Legacy composite `/api/students/...` paths (distinct from `/api/student-profiles` CRUD). */
interface StudentsLegacyApi {
    @POST("/api/students/{userId}/profile")
    suspend fun createProfileForUser(
        @Path("userId") userId: Long,
        @Body body: StudentProfileRequest
    ): Response<StudentProfileResponse>

    @GET("/api/students/{studentId}/profile")
    suspend fun getProfile(@Path("studentId") studentId: Long): Response<StudentProfileResponse>

    @POST("/api/students/{studentId}/projects")
    suspend fun addProject(
        @Path("studentId") studentId: Long,
        @Body body: ProjectRequest
    ): Response<ProjectResponse>

    @POST("/api/students/{studentId}/platforms")
    suspend fun addPlatform(
        @Path("studentId") studentId: Long,
        @Body body: PlatformLinkRequest
    ): Response<PlatformResponse>

    @POST("/api/students/{studentId}/education")
    suspend fun saveEducation(
        @Path("studentId") studentId: Long,
        @Body body: EducationProfileRequest
    ): Response<EducationResponse>

    @GET("/api/students/{studentId}/education")
    suspend fun getEducation(@Path("studentId") studentId: Long): Response<EducationResponse>
}
