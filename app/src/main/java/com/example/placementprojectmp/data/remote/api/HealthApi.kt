package com.example.placementprojectmp.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface HealthApi {
    /** Secured smoke test — plain text body, typically `"Backend is running"`. */
    @GET("/test")
    suspend fun test(): Response<ResponseBody>
}
