package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.model.StudentProfile
import com.example.placementprojectmp.data.remote.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class StudentApiImpl(
    private val client: HttpClient
) : StudentApi {
    //network call - runs inside a coroutine
    override suspend fun getStudentProfile(userId : Long) : StudentProfile{
        return client.get("${NetworkConfig.BASE_URL}/students/$userId/profile").body()
        //.body() - converts http response body to the kotlin serialization
    }
}