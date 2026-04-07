package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.data.remote.api.MetaApi

class MetaRepositoryImpl(
    private val api: MetaApi
) : MetaRepository {
    override suspend fun getCourses(): List<String> = api.getCourses()

    override suspend fun getDomains(course: String): List<String> = api.getDomains(course)
}
