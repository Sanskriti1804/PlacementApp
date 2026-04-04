package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.model.StudentProfile

interface StudentApi {
    suspend fun getStudentProfile(userId : Long) : StudentProfile
}