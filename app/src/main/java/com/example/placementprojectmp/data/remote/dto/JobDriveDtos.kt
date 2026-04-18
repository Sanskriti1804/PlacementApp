package com.example.placementprojectmp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlacementCoordinatorResponse(
    val id: Long? = null,
    val name: String? = null,
    val email: String? = null,
    val inMail: String? = null,
    val phoneNumber: String? = null
)

/** Shared shape for embedded lists and `GET .../selection-rounds`. */
@Serializable
data class SelectionRoundResponse(
    val id: Long? = null,
    val jobId: Long? = null,
    val driveId: Long? = null,
    val roundName: String? = null,
    val sequenceNumber: Int? = null,
    val scheduledDate: String? = null,
    val completed: Boolean? = null
)

typealias JobSelectionRoundResponse = SelectionRoundResponse
typealias DriveSelectionRoundResponse = SelectionRoundResponse

@Serializable
data class JobResponse(
    val id: Long? = null,
    val companyId: Long? = null,
    val company: CompanyResponse? = null,
    val placementCoordinatorId: Long? = null,
    val placementCoordinator: PlacementCoordinatorResponse? = null,
    val jobType: String? = null,
    val internshipDuration: String? = null,
    val workMode: String? = null,
    val ctcLpa: Double? = null,
    val additionalInfo: String? = null,
    val lastDateToApply: String? = null,
    val jobPostingTime: String? = null,
    val venue: String? = null,
    val jobDescription: String? = null,
    val preparationGuide: String? = null,
    val requirements: String? = null,
    val responsibilities: String? = null,
    val eligibility: String? = null,
    val resultStatus: String? = null,
    val resultDate: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val selectionRounds: List<SelectionRoundResponse>? = null
)

@Serializable
data class DriveBranchResponse(
    val id: Long? = null,
    val branch: String? = null
)

@Serializable
data class DriveOfferedRoleResponse(
    val id: Long? = null,
    val roleName: String? = null,
    val linkedJobId: Long? = null
)

@Serializable
data class DriveResponse(
    val id: Long? = null,
    val driveName: String? = null,
    val companyId: Long? = null,
    val company: CompanyResponse? = null,
    val lastDateToApply: String? = null,
    val driveDateTime: String? = null,
    val venue: String? = null,
    val resultStatus: String? = null,
    val resultDate: String? = null,
    val resultDisplay: String? = null,
    val placementCoordinatorId: Long? = null,
    val placementCoordinator: PlacementCoordinatorResponse? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val allowedBranches: List<DriveBranchResponse>? = null,
    val offeredRoles: List<DriveOfferedRoleResponse>? = null,
    val selectionRounds: List<SelectionRoundResponse>? = null
)

@Serializable
data class JobSelectionRoundCreateRequest(
    val roundName: String,
    val sequenceOrder: Int,
    val scheduledDate: String? = null
)

@Serializable
data class JobCreateRequest(
    val companyId: Long,
    val jobType: String,
    val workMode: String,
    val placementCoordinatorId: Long,
    val internshipDuration: String? = null,
    val ctcLpa: Double? = null,
    val additionalInfo: String? = null,
    val lastDateToApply: String? = null,
    val jobPostingTime: String? = null,
    val venue: String? = null,
    val jobDescription: String? = null,
    val preparationGuide: String? = null,
    val requirements: String? = null,
    val responsibilities: String? = null,
    val eligibility: String? = null,
    val resultStatus: String? = null,
    val resultDate: String? = null,
    val selectionRounds: List<JobSelectionRoundCreateRequest>? = null
)

@Serializable
data class JobUpdateRequest(
    val companyId: Long? = null,
    val jobType: String? = null,
    val workMode: String? = null,
    val placementCoordinatorId: Long? = null,
    val internshipDuration: String? = null,
    val ctcLpa: Double? = null,
    val additionalInfo: String? = null,
    val lastDateToApply: String? = null,
    val jobPostingTime: String? = null,
    val venue: String? = null,
    val jobDescription: String? = null,
    val preparationGuide: String? = null,
    val requirements: String? = null,
    val responsibilities: String? = null,
    val eligibility: String? = null,
    val resultStatus: String? = null,
    val resultDate: String? = null,
    val selectionRounds: List<JobSelectionRoundCreateRequest>? = null
)

@Serializable
data class DriveCreateRequest(
    val driveName: String,
    val companyId: Long,
    val lastDateToApply: String,
    val placementCoordinatorId: Long,
    val driveDateTime: String? = null,
    val venue: String? = null,
    val resultStatus: String? = null,
    val resultDate: String? = null,
    val allowedBranches: List<String>? = null,
    val offeredRoleTitles: List<String>? = null,
    val selectionRounds: List<JobSelectionRoundCreateRequest>? = null
)

@Serializable
data class DriveUpdateRequest(
    val driveName: String? = null,
    val companyId: Long? = null,
    val lastDateToApply: String? = null,
    val placementCoordinatorId: Long? = null,
    val driveDateTime: String? = null,
    val venue: String? = null,
    val resultStatus: String? = null,
    val resultDate: String? = null,
    val allowedBranches: List<String>? = null,
    val offeredRoleTitles: List<String>? = null,
    val selectionRounds: List<JobSelectionRoundCreateRequest>? = null
)

@Serializable
data class JobSelectionRoundUpdateRequest(
    val roundName: String? = null,
    val sequenceOrder: Int? = null,
    val scheduledDate: String? = null
)

@Serializable
data class DriveSelectionRoundUpdateRequest(
    val roundName: String? = null,
    val sequenceOrder: Int? = null,
    val scheduledDate: String? = null
)
