package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.data.model.User
import com.example.placementprojectmp.data.remote.api.UserApi

class UserRepo(private val api : UserApi) {
    suspend fun getUsers() : List<User>{
        return api.getUser()
    }

}