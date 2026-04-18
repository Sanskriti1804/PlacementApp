package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.ProjectRequest
import com.example.placementprojectmp.data.remote.dto.ProjectResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectsApi {
    @GET("/api/projects")
    suspend fun listProjects(): Response<List<ProjectResponse>>

    @GET("/api/projects/{id}")
    suspend fun getProject(@Path("id") id: Long): Response<ProjectResponse>

    @POST("/api/projects")
    suspend fun createProject(
        @Query("studentProfileId") studentProfileId: Long,
        @Body body: ProjectRequest? = null
    ): Response<ProjectResponse>

    @PUT("/api/projects/{id}")
    suspend fun updateProject(@Path("id") id: Long, @Body body: ProjectRequest? = null): Response<ProjectResponse>

    @DELETE("/api/projects/{id}")
    suspend fun deleteProject(@Path("id") id: Long): Response<ResponseBody>
}
