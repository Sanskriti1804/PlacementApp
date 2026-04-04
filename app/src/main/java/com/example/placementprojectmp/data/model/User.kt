package com.example.placementprojectmp.data.model

import kotlinx.serialization.Serializable

//marks the data as serializable so it can be parsed from the api response
@Serializable
data class User(
    val id : Int,
    val username : String,
    val name : String,
    val role : String
)