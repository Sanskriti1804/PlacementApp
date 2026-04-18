package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.data.remote.api.ApplicationsApi
import com.example.placementprojectmp.data.remote.api.BacklogsApi
import com.example.placementprojectmp.data.remote.api.CompaniesApi
import com.example.placementprojectmp.data.remote.api.DepartmentsApi
import com.example.placementprojectmp.data.remote.api.DriveBranchesApi
import com.example.placementprojectmp.data.remote.api.DriveOfferedRolesApi
import com.example.placementprojectmp.data.remote.api.DriveSelectionRoundsApi
import com.example.placementprojectmp.data.remote.api.DrivesApi
import com.example.placementprojectmp.data.remote.api.EducationProfilesApi
import com.example.placementprojectmp.data.remote.api.HealthApi
import com.example.placementprojectmp.data.remote.api.JobSelectionRoundsApi
import com.example.placementprojectmp.data.remote.api.JobsApi
import com.example.placementprojectmp.data.remote.api.PlatformsApi
import com.example.placementprojectmp.data.remote.api.ProjectsApi
import com.example.placementprojectmp.data.remote.api.RolesCatalogApi
import com.example.placementprojectmp.data.remote.api.SkillsApi
import com.example.placementprojectmp.data.remote.api.SpringMetaApi
import com.example.placementprojectmp.data.remote.api.StaffProfilesApi
import com.example.placementprojectmp.data.remote.api.StudentExperiencesApi
import com.example.placementprojectmp.data.remote.api.StudentProfilesApi
import com.example.placementprojectmp.data.remote.api.StudentsLegacyApi
import com.example.placementprojectmp.data.remote.api.UsersApi

data class BackendApis(
    val health: HealthApi,
    val meta: SpringMetaApi,
    val users: UsersApi,
    val companies: CompaniesApi,
    val departments: DepartmentsApi,
    val rolesCatalog: RolesCatalogApi,
    val jobs: JobsApi,
    val drives: DrivesApi,
    val applications: ApplicationsApi,
    val staffProfiles: StaffProfilesApi,
    val studentProfiles: StudentProfilesApi,
    val studentsLegacy: StudentsLegacyApi,
    val educationProfiles: EducationProfilesApi,
    val studentExperiences: StudentExperiencesApi,
    val skills: SkillsApi,
    val platforms: PlatformsApi,
    val projects: ProjectsApi,
    val backlogs: BacklogsApi,
    val jobSelectionRounds: JobSelectionRoundsApi,
    val driveSelectionRounds: DriveSelectionRoundsApi,
    val driveBranches: DriveBranchesApi,
    val driveOfferedRoles: DriveOfferedRolesApi
)
