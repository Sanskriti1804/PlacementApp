package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.auth.RegisterRequest
import com.example.placementprojectmp.data.remote.dto.DriveBranchCreateRequest
import com.example.placementprojectmp.data.remote.dto.DriveBranchResponse
import com.example.placementprojectmp.data.remote.dto.DriveBranchUpdateRequest
import com.example.placementprojectmp.data.remote.dto.DriveOfferedRoleCreateRequest
import com.example.placementprojectmp.data.remote.dto.DriveOfferedRoleResponse
import com.example.placementprojectmp.data.remote.dto.DriveOfferedRoleUpdateRequest
import com.example.placementprojectmp.data.remote.dto.EducationProfileRequest
import com.example.placementprojectmp.data.remote.dto.EducationResponse
import com.example.placementprojectmp.data.remote.dto.JobApplicationCreateRequest
import com.example.placementprojectmp.data.remote.dto.JobApplicationResponse
import com.example.placementprojectmp.data.remote.dto.JobApplicationUpdateRequest
import com.example.placementprojectmp.data.remote.dto.JobSelectionRoundCreateRequest
import com.example.placementprojectmp.data.remote.dto.JobSelectionRoundUpdateRequest
import com.example.placementprojectmp.data.remote.dto.DriveSelectionRoundUpdateRequest
import com.example.placementprojectmp.data.remote.dto.RoleCreateRequest
import com.example.placementprojectmp.data.remote.dto.RoleResponse
import com.example.placementprojectmp.data.remote.dto.RoleUpdateRequest
import com.example.placementprojectmp.data.remote.dto.SelectionRoundResponse
import com.example.placementprojectmp.data.remote.dto.StaffProfileCreateRequest
import com.example.placementprojectmp.data.remote.dto.StaffProfileResponse
import com.example.placementprojectmp.data.remote.dto.StaffProfileUpdateRequest
import com.example.placementprojectmp.data.remote.dto.StudentExperienceCreateRequest
import com.example.placementprojectmp.data.remote.dto.StudentExperienceResponse
import com.example.placementprojectmp.data.remote.dto.StudentExperienceUpdateRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileResponse
import com.example.placementprojectmp.data.remote.dto.UserResponse
import com.example.placementprojectmp.data.remote.dto.UserUpdateRequest
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.remote.safeApiCall
import com.example.placementprojectmp.integration.data.remote.safeApiCallNoContent
import com.example.placementprojectmp.integration.data.remote.safeApiCallPlainText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/**
 * REST facade for the placement backend: maps server exception-handler errors via [safeApiCall] (`{"error":"..."}`).
 */
class BackendIntegrationRepository(
    private val apis: BackendApis,
    private val json: Json
) {

    suspend fun testSecuredPing(): ApiResult<String> = safeApiCallPlainText(json) { apis.health.test() }

    suspend fun metaBranches(): ApiResult<List<String>> = safeApiCall(json) { apis.meta.getBranches() }
    suspend fun metaCoursesForBranch(branch: String): ApiResult<List<String>> =
        safeApiCall(json) { apis.meta.getCoursesForBranch(branch) }
    suspend fun metaCourses(): ApiResult<List<String>> = safeApiCall(json) { apis.meta.getCourses() }
    suspend fun metaDomainsForCourse(course: String): ApiResult<List<String>> =
        safeApiCall(json) { apis.meta.getDomainsForCourse(course) }
    suspend fun metaAll(): ApiResult<JsonObject> = safeApiCall(json) { apis.meta.getAllMeta() }

    suspend fun usersList(): ApiResult<List<UserResponse>> = safeApiCall(json) { apis.users.listUsers() }
    suspend fun usersGet(id: Long): ApiResult<UserResponse> = safeApiCall(json) { apis.users.getUser(id) }
    suspend fun usersCreate(body: RegisterRequest): ApiResult<UserResponse> =
        safeApiCall(json) { apis.users.createUser(body) }
    suspend fun usersUpdate(id: Long, body: UserUpdateRequest): ApiResult<UserResponse> =
        safeApiCall(json) { apis.users.updateUser(id, body) }
    suspend fun usersDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.users.deleteUser(id) }

    suspend fun companiesList() = safeApiCall(json) { apis.companies.listCompanies() }
    suspend fun companiesGet(id: Long) = safeApiCall(json) { apis.companies.getCompany(id) }
    suspend fun companiesCreate(body: com.example.placementprojectmp.data.remote.dto.CompanyCreateRequest) =
        safeApiCall(json) { apis.companies.createCompany(body) }
    suspend fun companiesUpdate(id: Long, body: com.example.placementprojectmp.data.remote.dto.CompanyUpdateRequest) =
        safeApiCall(json) { apis.companies.updateCompany(id, body) }
    suspend fun companiesDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.companies.deleteCompany(id) }

    suspend fun departmentsList() = safeApiCall(json) { apis.departments.listDepartments() }
    suspend fun departmentsGet(id: Long) = safeApiCall(json) { apis.departments.getDepartment(id) }
    suspend fun departmentsCreate(body: com.example.placementprojectmp.data.remote.dto.DepartmentCreateRequest) =
        safeApiCall(json) { apis.departments.createDepartment(body) }
    suspend fun departmentsUpdate(id: Long, body: com.example.placementprojectmp.data.remote.dto.DepartmentUpdateRequest) =
        safeApiCall(json) { apis.departments.updateDepartment(id, body) }
    suspend fun departmentsDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.departments.deleteDepartment(id) }

    suspend fun rolesCatalogList(): ApiResult<List<RoleResponse>> =
        safeApiCall(json) { apis.rolesCatalog.listRoles() }
    suspend fun rolesCatalogGet(id: Long): ApiResult<RoleResponse> =
        safeApiCall(json) { apis.rolesCatalog.getRole(id) }
    suspend fun rolesCatalogCreate(body: RoleCreateRequest): ApiResult<RoleResponse> =
        safeApiCall(json) { apis.rolesCatalog.createRole(body) }
    suspend fun rolesCatalogUpdate(id: Long, body: RoleUpdateRequest): ApiResult<RoleResponse> =
        safeApiCall(json) { apis.rolesCatalog.updateRole(id, body) }
    suspend fun rolesCatalogDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.rolesCatalog.deleteRole(id) }

    suspend fun applicationsList(): ApiResult<List<JobApplicationResponse>> =
        safeApiCall(json) { apis.applications.listApplications() }
    suspend fun applicationsGet(id: Long): ApiResult<JobApplicationResponse> =
        safeApiCall(json) { apis.applications.getApplication(id) }
    suspend fun applicationsCreate(body: JobApplicationCreateRequest): ApiResult<JobApplicationResponse> =
        safeApiCall(json) { apis.applications.createApplication(body) }
    suspend fun applicationsUpdate(id: Long, body: JobApplicationUpdateRequest): ApiResult<JobApplicationResponse> =
        safeApiCall(json) { apis.applications.updateApplication(id, body) }
    suspend fun applicationsDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.applications.deleteApplication(id) }

    suspend fun staffProfilesList(): ApiResult<List<StaffProfileResponse>> =
        safeApiCall(json) { apis.staffProfiles.listStaffProfiles() }
    suspend fun staffProfilesGet(id: Long): ApiResult<StaffProfileResponse> =
        safeApiCall(json) { apis.staffProfiles.getStaffProfile(id) }
    suspend fun staffProfilesCreate(body: StaffProfileCreateRequest): ApiResult<StaffProfileResponse> =
        safeApiCall(json) { apis.staffProfiles.createStaffProfile(body) }
    suspend fun staffProfilesUpdate(id: Long, body: StaffProfileUpdateRequest): ApiResult<StaffProfileResponse> =
        safeApiCall(json) { apis.staffProfiles.updateStaffProfile(id, body) }
    suspend fun staffProfilesDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.staffProfiles.deleteStaffProfile(id) }

    suspend fun studentProfilesList(): ApiResult<List<StudentProfileResponse>> =
        safeApiCall(json) { apis.studentProfiles.listStudentProfiles() }
    suspend fun studentProfilesGet(id: Long): ApiResult<StudentProfileResponse> =
        safeApiCall(json) { apis.studentProfiles.getStudentProfile(id) }
    suspend fun studentProfilesCreate(userId: Long, body: StudentProfileRequest): ApiResult<StudentProfileResponse> =
        safeApiCall(json) { apis.studentProfiles.createStudentProfile(userId, body) }
    suspend fun studentProfilesUpdate(id: Long, body: StudentProfileRequest): ApiResult<StudentProfileResponse> =
        safeApiCall(json) { apis.studentProfiles.updateStudentProfile(id, body) }
    suspend fun studentProfilesDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.studentProfiles.deleteStudentProfile(id) }

    suspend fun educationProfilesList(): ApiResult<List<EducationResponse>> =
        safeApiCall(json) { apis.educationProfiles.listEducationProfiles() }
    suspend fun educationProfilesGet(id: Long): ApiResult<EducationResponse> =
        safeApiCall(json) { apis.educationProfiles.getEducationProfile(id) }
    suspend fun educationProfilesCreate(body: EducationProfileRequest): ApiResult<EducationResponse> =
        safeApiCall(json) { apis.educationProfiles.createEducationProfile(body) }
    suspend fun educationProfilesUpdate(id: Long, body: EducationProfileRequest): ApiResult<EducationResponse> =
        safeApiCall(json) { apis.educationProfiles.updateEducationProfile(id, body) }
    suspend fun educationProfilesDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.educationProfiles.deleteEducationProfile(id) }

    suspend fun studentExperiencesList(): ApiResult<List<StudentExperienceResponse>> =
        safeApiCall(json) { apis.studentExperiences.listExperiences() }
    suspend fun studentExperiencesGet(id: Long): ApiResult<StudentExperienceResponse> =
        safeApiCall(json) { apis.studentExperiences.getExperience(id) }
    suspend fun studentExperiencesCreate(body: StudentExperienceCreateRequest): ApiResult<StudentExperienceResponse> =
        safeApiCall(json) { apis.studentExperiences.createExperience(body) }
    suspend fun studentExperiencesUpdate(id: Long, body: StudentExperienceUpdateRequest): ApiResult<StudentExperienceResponse> =
        safeApiCall(json) { apis.studentExperiences.updateExperience(id, body) }
    suspend fun studentExperiencesDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.studentExperiences.deleteExperience(id) }

    suspend fun jobSelectionRoundsList(): ApiResult<List<SelectionRoundResponse>> =
        safeApiCall(json) { apis.jobSelectionRounds.listJobSelectionRounds() }
    suspend fun jobSelectionRoundsGet(id: Long): ApiResult<SelectionRoundResponse> =
        safeApiCall(json) { apis.jobSelectionRounds.getJobSelectionRound(id) }
    suspend fun jobSelectionRoundsCreate(jobId: Long, body: JobSelectionRoundCreateRequest): ApiResult<SelectionRoundResponse> =
        safeApiCall(json) { apis.jobSelectionRounds.createJobSelectionRound(jobId, body) }
    suspend fun jobSelectionRoundsUpdate(id: Long, body: JobSelectionRoundUpdateRequest): ApiResult<SelectionRoundResponse> =
        safeApiCall(json) { apis.jobSelectionRounds.updateJobSelectionRound(id, body) }
    suspend fun jobSelectionRoundsDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.jobSelectionRounds.deleteJobSelectionRound(id) }

    suspend fun driveSelectionRoundsList(): ApiResult<List<SelectionRoundResponse>> =
        safeApiCall(json) { apis.driveSelectionRounds.listDriveSelectionRounds() }
    suspend fun driveSelectionRoundsGet(id: Long): ApiResult<SelectionRoundResponse> =
        safeApiCall(json) { apis.driveSelectionRounds.getDriveSelectionRound(id) }
    suspend fun driveSelectionRoundsCreate(driveId: Long, body: JobSelectionRoundCreateRequest): ApiResult<SelectionRoundResponse> =
        safeApiCall(json) { apis.driveSelectionRounds.createDriveSelectionRound(driveId, body) }
    suspend fun driveSelectionRoundsUpdate(id: Long, body: DriveSelectionRoundUpdateRequest): ApiResult<SelectionRoundResponse> =
        safeApiCall(json) { apis.driveSelectionRounds.updateDriveSelectionRound(id, body) }
    suspend fun driveSelectionRoundsDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.driveSelectionRounds.deleteDriveSelectionRound(id) }

    suspend fun driveBranchesList(): ApiResult<List<DriveBranchResponse>> =
        safeApiCall(json) { apis.driveBranches.listDriveBranches() }
    suspend fun driveBranchesGet(id: Long): ApiResult<DriveBranchResponse> =
        safeApiCall(json) { apis.driveBranches.getDriveBranch(id) }
    suspend fun driveBranchesCreate(body: DriveBranchCreateRequest): ApiResult<DriveBranchResponse> =
        safeApiCall(json) { apis.driveBranches.createDriveBranch(body) }
    suspend fun driveBranchesUpdate(id: Long, body: DriveBranchUpdateRequest): ApiResult<DriveBranchResponse> =
        safeApiCall(json) { apis.driveBranches.updateDriveBranch(id, body) }
    suspend fun driveBranchesDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.driveBranches.deleteDriveBranch(id) }

    suspend fun driveOfferedRolesList(): ApiResult<List<DriveOfferedRoleResponse>> =
        safeApiCall(json) { apis.driveOfferedRoles.listDriveOfferedRoles() }
    suspend fun driveOfferedRolesGet(id: Long): ApiResult<DriveOfferedRoleResponse> =
        safeApiCall(json) { apis.driveOfferedRoles.getDriveOfferedRole(id) }
    suspend fun driveOfferedRolesCreate(body: DriveOfferedRoleCreateRequest): ApiResult<DriveOfferedRoleResponse> =
        safeApiCall(json) { apis.driveOfferedRoles.createDriveOfferedRole(body) }
    suspend fun driveOfferedRolesUpdate(id: Long, body: DriveOfferedRoleUpdateRequest): ApiResult<DriveOfferedRoleResponse> =
        safeApiCall(json) { apis.driveOfferedRoles.updateDriveOfferedRole(id, body) }
    suspend fun driveOfferedRolesDelete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { apis.driveOfferedRoles.deleteDriveOfferedRole(id) }
}
