package com.example.placementprojectmp.data.remote.api

import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpringMetaApi {
    @GET("/api/meta/branches")
    suspend fun getBranches(): Response<List<String>>

    @GET("/api/meta/branches/{branch}/courses")
    suspend fun getCoursesForBranch(@Path("branch") branch: String): Response<List<String>>

    @GET("/api/meta/courses")
    suspend fun getCourses(): Response<List<String>>

    @GET("/api/meta/courses/{course}/domains")
    suspend fun getDomainsForCourse(@Path("course") course: String): Response<List<String>>

    @GET("/api/meta/all")
    suspend fun getAllMeta(): Response<JsonObject>
}
