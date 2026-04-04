package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.model.User

interface UserApi {
    suspend fun getUser() : List<User>
}