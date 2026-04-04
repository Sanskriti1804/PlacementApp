package com.example.placementprojectmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StudentProfile(
    val id : Long,
    val rollNo : String? = null,
    val photUrl : String? = null,
    val bio : String? = null,
    val skills : List<Skill> = emptyList(),
    val projects : List<Project> = emptyList(),
    val links : List<Link> = emptyList()
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
    val name : String,
    val url : String
)