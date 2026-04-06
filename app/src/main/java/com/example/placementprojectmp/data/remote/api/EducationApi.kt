package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.model.EducationProfile

interface EducationApi {
    suspend fun getEducationProfile(studentId : Long) : EducationProfile

    suspend fun createOrUpdateEducation(studentId: Long, request : EducationProfile) : EducationProfile
}