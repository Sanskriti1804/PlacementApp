package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.model.EducationProfile
import com.example.placementprojectmp.data.remote.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class EductionApiImpl(
    private val client : HttpClient
) : EducationApi{
    override suspend fun getEducationProfile(studentId: Long): EducationProfile {
        return client.get ("${NetworkConfig.BASE_URL}/api/students/$studentId/education")
            .body()
    }

    override suspend fun createOrUpdateEducation(
        studentId: Long,
        request: EducationProfile
    ): EducationProfile {
        return client.post("${NetworkConfig.BASE_URL}/api/students/$studentId/education"){
            setBody(request)
        }.body()
    }
}