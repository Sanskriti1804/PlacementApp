package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLPath

class MetaApiImpl(
    private val client: HttpClient
) : MetaApi {
    override suspend fun getCourses(): List<String> {
        return client.get("${NetworkConfig.BASE_URL}/api/meta/courses").body()
    }

    override suspend fun getDomains(course: String): List<String> {
        val encodedCourse = course.encodeURLPath()
        return client.get("${NetworkConfig.BASE_URL}/api/meta/courses/$encodedCourse/domains").body()
    }
}
