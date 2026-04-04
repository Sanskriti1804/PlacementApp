package com.example.placementprojectmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentProfile(
    val id : Long,
    val rollNo : String? = null,
    @SerialName("photoUrl") val photUrl : String? = null,
    val bio : String? = null,
    val skills : List<Skill> = emptyList(),
    val projects : List<Project> = emptyList(),
    val links : List<Link> = emptyList(),
    val user : StudentProfileUser? = null
)

@Serializable
data class Skill(
    val id : Long,
    val name : String
)

@Serializable
data class Project(
    val id : Long,
    val title : String,
    val description : String
)

@Serializable
data class Link(
    val id : Long,
    @SerialName("type") val name : String,
    val url : String
)

@Serializable
data class StudentProfileUser(
    val id : Int,
    val email : String? = null,
    val name : String,
    val phone : String? = null,
    val roles : List<StudentProfileRole> = emptyList()
)

@Serializable
data class StudentProfileRole(
    val id : Long = 0,
    val roleName : String = ""
)