package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.data.model.StudentProfile
import com.example.placementprojectmp.data.remote.api.StudentApi

class StudentRepo(
    private val api : StudentApi
) {
    suspend fun getStudentProfile(userId : Long) : StudentProfile{
        return api.getStudentProfile(userId)
    }
}