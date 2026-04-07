package com.example.placementprojectmp.data.remote.api

interface MetaApi {
    suspend fun getCourses(): List<String>
    suspend fun getDomains(course: String): List<String>
}
