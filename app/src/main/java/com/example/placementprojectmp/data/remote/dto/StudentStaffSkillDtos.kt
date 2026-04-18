package com.example.placementprojectmp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SkillRequest(
    val languages: String? = null,
    val frameworks: String? = null,
    val tools: String? = null,
    val platforms: String? = null
)

@Serializable
data class SkillResponse(
    val id: Long? = null,
    val languages: String? = null,
    val frameworks: String? = null,
    val tools: String? = null,
    val platforms: String? = null
)

@Serializable
data class ProjectRequest(
    val title: String? = null,
    val description: String? = null,
    val link: String? = null
)

@Serializable
data class ProjectResponse(
    val id: Long? = null,
    val title: String? = null,
    val description: String? = null,
    val link: String? = null
)

@Serializable
data class PlatformLinkRequest(
    val type: String,
    val url: String
)

@Serializable
data class PlatformResponse(
    val id: Long? = null,
    val type: String? = null,
    val url: String? = null
)

@Serializable
data class BacklogRequest(
    val subject: String? = null,
    val semester: Int? = null
)

@Serializable
data class BacklogResponse(
    val id: Long? = null,
    val subject: String? = null,
    val semester: Int? = null
)

@Serializable
data class EducationProfileRequest(
    val studentId: Long? = null,
    val university: String? = null,
    val branch: String? = null,
    val course: String? = null,
    val domain: String? = null,
    val currentYear: Int? = null,
    val tenthPercentage: Double? = null,
    val twelfthPercentage: Double? = null,
    val currentCgpa: Double? = null,
    val tenthSchoolName: String? = null,
    val twelfthSchoolName: String? = null,
    val graduationCollegeName: String? = null,
    val postGraduationCollegeName: String? = null,
    val gapYears: Int? = null,
    val gapReason: String? = null,
    val backlogs: List<BacklogRequest>? = null
)

@Serializable
data class EducationResponse(
    val id: Long? = null,
    val university: String? = null,
    val branch: String? = null,
    val course: String? = null,
    val domain: String? = null,
    val currentYear: Int? = null,
    val tenthPercentage: Double? = null,
    val twelfthPercentage: Double? = null,
    val currentCgpa: Double? = null,
    val tenthSchoolName: String? = null,
    val twelfthSchoolName: String? = null,
    val graduationCollegeName: String? = null,
    val postGraduationCollegeName: String? = null,
    val gapYears: Int? = null,
    val gapReason: String? = null,
    val backlogs: List<BacklogResponse>? = null
)

typealias EducationProfileDto = EducationResponse

@Serializable
data class StudentProfileRequest(
    val userId: Long? = null,
    val name: String? = null,
    val username: String? = null,
    val userEmail: String? = null,
    val domainRole: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val bio: String? = null,
    val addressLine: String? = null,
    val city: String? = null,
    val state: String? = null,
    val pinCode: String? = null,
    val resumeUrl: String? = null,
    val hired: Boolean? = null,
    val hiredCompanyName: String? = null,
    val dob: String? = null,
    val skills: SkillRequest? = null
)

@Serializable
data class StudentProfileResponse(
    val id: Long? = null,
    val user: UserResponse? = null,
    val name: String? = null,
    val username: String? = null,
    val userEmail: String? = null,
    val domainRole: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val bio: String? = null,
    val addressLine: String? = null,
    val city: String? = null,
    val state: String? = null,
    val pinCode: String? = null,
    val resumeUrl: String? = null,
    val hired: Boolean? = null,
    val hiredCompanyName: String? = null,
    val dob: String? = null,
    val skills: SkillResponse? = null,
    val projects: List<ProjectResponse>? = null,
    val platformLinks: List<PlatformResponse>? = null,
    val applications: List<JobApplicationResponse>? = null,
    val experiences: List<StudentExperienceResponse>? = null
)

typealias StudentProfileCrudDto = StudentProfileResponse

@Serializable
data class StudentExperienceCreateRequest(
    val studentId: Long,
    val companyName: String,
    val jobType: String,
    val location: String? = null,
    val fromDate: String? = null,
    val toDate: String? = null,
    val roleDescription: String? = null
)

@Serializable
data class StudentExperienceUpdateRequest(
    val companyName: String? = null,
    val jobType: String? = null,
    val location: String? = null,
    val fromDate: String? = null,
    val toDate: String? = null,
    val roleDescription: String? = null
)

@Serializable
data class StudentExperienceResponse(
    val id: Long? = null,
    val studentId: Long? = null,
    val companyName: String? = null,
    val jobType: String? = null,
    val location: String? = null,
    val fromDate: String? = null,
    val toDate: String? = null,
    val roleDescription: String? = null
)

typealias StudentExperienceDto = StudentExperienceResponse

@Serializable
data class StaffProfessionalExperienceResponse(
    val id: Long? = null,
    val companyName: String? = null,
    val roleTitle: String? = null,
    val fromDate: String? = null,
    val toDate: String? = null,
    val description: String? = null
)

@Serializable
data class StaffManagedRoleResponse(
    val id: Long? = null,
    val companyIds: List<Long>? = null,
    val driveIds: List<Long>? = null,
    val studentIds: List<Long>? = null,
    val departments: List<String>? = null
)

@Serializable
data class StaffProfessionalExperienceRequest(
    val companyName: String? = null,
    val roleTitle: String? = null,
    val fromDate: String? = null,
    val toDate: String? = null,
    val description: String? = null
)

@Serializable
data class StaffManagedRoleRequest(
    val companyIds: List<Long>? = null,
    val driveIds: List<Long>? = null,
    val studentIds: List<Long>? = null,
    val departments: List<String>? = null
)

@Serializable
data class StaffProfileCreateRequest(
    val userId: Long,
    val name: String? = null,
    val userEmail: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val linkedin: String? = null,
    val officeLocation: String? = null,
    val collegeName: String? = null,
    val joiningYear: Int? = null,
    val joiningMonth: Int? = null,
    val endingYear: Int? = null,
    val endingMonth: Int? = null,
    val subjects: List<String>? = null,
    val qualifications: List<String>? = null,
    val professionalExperiences: List<StaffProfessionalExperienceRequest>? = null,
    val managedRoles: List<StaffManagedRoleRequest>? = null
)

@Serializable
data class StaffProfileUpdateRequest(
    val name: String? = null,
    val userEmail: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val linkedin: String? = null,
    val officeLocation: String? = null,
    val collegeName: String? = null,
    val joiningYear: Int? = null,
    val joiningMonth: Int? = null,
    val endingYear: Int? = null,
    val endingMonth: Int? = null,
    val subjects: List<String>? = null,
    val qualifications: List<String>? = null,
    val professionalExperiences: List<StaffProfessionalExperienceRequest>? = null,
    val managedRoles: List<StaffManagedRoleRequest>? = null
)

@Serializable
data class StaffProfileResponse(
    val id: Long? = null,
    val userId: Long? = null,
    val name: String? = null,
    val userEmail: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val linkedin: String? = null,
    val officeLocation: String? = null,
    val collegeName: String? = null,
    val joiningYear: Int? = null,
    val joiningMonth: Int? = null,
    val endingYear: Int? = null,
    val endingMonth: Int? = null,
    val subjects: List<String>? = null,
    val qualifications: List<String>? = null,
    val professionalExperiences: List<StaffProfessionalExperienceResponse>? = null,
    val managedRoles: List<StaffManagedRoleResponse>? = null
)

typealias StaffProfileDto = StaffProfileResponse

@Serializable
data class DriveBranchCreateRequest(
    val driveId: Long,
    val branch: String
)

@Serializable
data class DriveBranchUpdateRequest(
    val branch: String? = null
)

@Serializable
data class DriveOfferedRoleCreateRequest(
    val driveId: Long,
    val roleName: String,
    val linkedJobId: Long? = null
)

@Serializable
data class DriveOfferedRoleUpdateRequest(
    val roleName: String? = null,
    val linkedJobId: Long? = null
)
