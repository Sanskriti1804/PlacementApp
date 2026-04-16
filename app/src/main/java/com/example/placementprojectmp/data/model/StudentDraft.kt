package com.example.placementprojectmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JobEntryDraft(
    val companyName: String = "",
    val jobTypeTitle: String = "", // Serialize enum name/title
    val location: String = "",
    val fromDay: String = "",
    val fromMonth: String = "",
    val fromYear: String = "",
    val toDay: String = "",
    val toMonth: String = "",
    val toYear: String = "",
    val roleDescription: String = ""
)

@Serializable
data class TeamMemberDraft(
    val name: String = "",
    val role: String = ""
)

data class StudentDraft(
    // Personal fields
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val role: String = "",
    val phone: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val pinCode: String = "",
    val day: String = "",
    val month: String = "",
    val year: String = "",

    // Education fields
    val university: String = "",
    val course: String = "",
    val selectedYear: String = "",
    val class12Percent: String = "",
    val school12Name: String = "",
    val passYear12: String = "",
    val class10Percent: String = "",
    val school10Name: String = "",
    val passYear10: String = "",
    val gradCgpa: String = "",
    val gradPassYear: String = "",
    val activeBacklogsEnabled: Boolean = false,
    val backlogCount: Int = 1,
    val backlogSubjects: String = "",
    val academicGapEnabled: Boolean = false,
    val gapYears: String = "",
    val gapExplanation: String = "",

    // Skills fields
    val languagesSelected: Set<String> = emptySet(),
    val frameworksSelected: Set<String> = emptySet(),
    val toolsSelected: Set<String> = emptySet(),
    val softSkillsSelected: Set<String> = emptySet(),

    // Experience fields
    val hasWorkExperience: Boolean = false,
    val jobEntriesJson: String = "[]",

    // Projects fields
    val projectName: String = "",
    val projectImageUri: String = "", // To store main project image URI
    val projectLink: String = "",
    val projectDescription: String = "",
    val technologiesSelected: Set<String> = emptySet(),
    val githubRepo: String = "",
    val liveDemo: String = "",
    val teamSize: Int = 0,
    val teamMembersJson: String = "[]",
    
    // Additional Image URIs
    val profileImageUri: String = "",
    val connectorLinksJson: String = "{}"
)
