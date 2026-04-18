package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.SkillRequest
import com.example.placementprojectmp.data.remote.dto.SkillResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SkillsApi {
    @GET("/api/skills")
    suspend fun listSkills(): Response<List<SkillResponse>>

    @GET("/api/skills/{id}")
    suspend fun getSkill(@Path("id") id: Long): Response<SkillResponse>

    @POST("/api/skills")
    suspend fun createSkill(
        @Query("studentProfileId") studentProfileId: Long,
        @Body body: SkillRequest? = null
    ): Response<SkillResponse>

    @PUT("/api/skills/{id}")
    suspend fun updateSkill(@Path("id") id: Long, @Body body: SkillRequest? = null): Response<SkillResponse>

    @DELETE("/api/skills/{id}")
    suspend fun deleteSkill(@Path("id") id: Long): Response<ResponseBody>
}
