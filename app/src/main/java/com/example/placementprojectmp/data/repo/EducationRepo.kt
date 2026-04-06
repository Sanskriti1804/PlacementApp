package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.data.model.EducationProfile
import com.example.placementprojectmp.data.remote.api.EducationApi

class EducationRepo(
    private val api : EducationApi
) {
    suspend fun getEducation(studentId : Long) : EducationProfile{
        return api.getEducationProfile(studentId)
    }

    suspend fun saveEducation(
        studentId: Long,
        request : EducationProfile
    ) : EducationProfile{
        return api.createOrUpdateEducation(studentId, request)
    }
}