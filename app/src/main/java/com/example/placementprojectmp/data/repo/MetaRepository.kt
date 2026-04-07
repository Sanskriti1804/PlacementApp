package com.example.placementprojectmp.data.repo

interface MetaRepository {
    suspend fun getCourses(): List<String>
    suspend fun getDomains(course: String): List<String>
}
