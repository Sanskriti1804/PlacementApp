package com.example.placementprojectmp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class JobApplicationCreateRequest(
    val studentId: Long,
    val jobId: Long,
    val appliedDate: String? = null,
    val status: String? = null,
    val interviewDate: String? = null,
    val interviewMode: String? = null
)

@Serializable
data class JobApplicationUpdateRequest(
    val appliedDate: String? = null,
    val status: String? = null,
    val interviewDate: String? = null,
    val interviewMode: String? = null
)

@Serializable
data class JobApplicationResponse(
    val id: Long? = null,
    val studentId: Long? = null,
    val jobId: Long? = null,
    val companyId: Long? = null,
    val appliedDate: String? = null,
    val status: String? = null,
    val interviewDate: String? = null,
    val interviewMode: String? = null
)

typealias JobApplicationDto = JobApplicationResponse

@Serializable
data class DepartmentCreateRequest(
    val name: String,
    val code: String,
    val collegeName: String? = null
)

@Serializable
data class DepartmentUpdateRequest(
    val name: String? = null,
    val code: String? = null,
    val collegeName: String? = null
)

@Serializable
data class DepartmentResponse(
    val id: Long? = null,
    val name: String? = null,
    val code: String? = null,
    val collegeName: String? = null
)

typealias DepartmentDto = DepartmentResponse

@Serializable
data class RoleCreateRequest(
    val roleName: String
)

@Serializable
data class RoleUpdateRequest(
    val roleName: String? = null
)

@Serializable
data class RoleResponse(
    val id: Long? = null,
    val roleName: String? = null
)

typealias JobRoleDto = RoleResponse
