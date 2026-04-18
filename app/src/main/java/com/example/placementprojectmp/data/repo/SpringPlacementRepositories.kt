package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.data.remote.api.SpringMetaApi
import com.example.placementprojectmp.data.remote.api.StudentsLegacyApi
import com.example.placementprojectmp.data.remote.dto.EducationProfileRequest
import com.example.placementprojectmp.data.remote.dto.EducationResponse
import com.example.placementprojectmp.data.remote.dto.JobApplicationCreateRequest
import com.example.placementprojectmp.data.remote.dto.JobApplicationResponse
import com.example.placementprojectmp.data.remote.dto.JobApplicationUpdateRequest
import com.example.placementprojectmp.data.remote.dto.JobCreateRequest
import com.example.placementprojectmp.data.remote.dto.JobResponse
import com.example.placementprojectmp.data.remote.dto.JobUpdateRequest
import com.example.placementprojectmp.data.remote.dto.DriveCreateRequest
import com.example.placementprojectmp.data.remote.dto.DriveResponse
import com.example.placementprojectmp.data.remote.dto.DriveUpdateRequest
import com.example.placementprojectmp.data.remote.dto.PlatformLinkRequest
import com.example.placementprojectmp.data.remote.dto.PlatformResponse
import com.example.placementprojectmp.data.remote.dto.ProjectRequest
import com.example.placementprojectmp.data.remote.dto.ProjectResponse
import com.example.placementprojectmp.data.remote.dto.SelectionRoundResponse
import com.example.placementprojectmp.data.remote.dto.StudentProfileRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileResponse
import com.example.placementprojectmp.data.remote.api.ApplicationsApi
import com.example.placementprojectmp.data.remote.api.DrivesApi
import com.example.placementprojectmp.data.remote.api.JobsApi
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.remote.safeApiCall
import com.example.placementprojectmp.integration.data.remote.safeApiCallNoContent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/** `/api/meta` (secured) — branches, courses, domains, aggregate. */
interface SpringMetaRepository {
    suspend fun fetchBranches(): ApiResult<List<String>>
    suspend fun fetchCoursesForBranch(branch: String): ApiResult<List<String>>
    suspend fun fetchCourses(): ApiResult<List<String>>
    suspend fun fetchDomainsForCourse(course: String): ApiResult<List<String>>
    suspend fun fetchAll(): ApiResult<JsonObject>
}

class SpringMetaRepositoryImpl(
    private val api: SpringMetaApi,
    private val json: Json
) : SpringMetaRepository {
    override suspend fun fetchBranches(): ApiResult<List<String>> = safeApiCall(json) { api.getBranches() }
    override suspend fun fetchCoursesForBranch(branch: String): ApiResult<List<String>> =
        safeApiCall(json) { api.getCoursesForBranch(branch) }
    override suspend fun fetchCourses(): ApiResult<List<String>> = safeApiCall(json) { api.getCourses() }
    override suspend fun fetchDomainsForCourse(course: String): ApiResult<List<String>> =
        safeApiCall(json) { api.getDomainsForCourse(course) }
    override suspend fun fetchAll(): ApiResult<JsonObject> = safeApiCall(json) { api.getAllMeta() }
}

interface JobRepository {
    suspend fun fetchList(): ApiResult<List<JobResponse>>
    suspend fun fetchById(id: String): ApiResult<JobResponse>
    suspend fun create(body: JobCreateRequest): ApiResult<JobResponse>
    suspend fun update(id: Long, body: JobUpdateRequest): ApiResult<JobResponse>
    suspend fun delete(id: Long): ApiResult<Unit>
    suspend fun listSelectionRounds(jobId: Long): ApiResult<List<SelectionRoundResponse>>
}

class JobRepositoryImpl(
    private val api: JobsApi,
    private val json: Json
) : JobRepository {
    override suspend fun fetchList(): ApiResult<List<JobResponse>> = safeApiCall(json) { api.listJobs() }
    override suspend fun fetchById(id: String): ApiResult<JobResponse> = safeApiCall(json) { api.getJob(id) }
    override suspend fun create(body: JobCreateRequest): ApiResult<JobResponse> = safeApiCall(json) { api.createJob(body) }
    override suspend fun update(id: Long, body: JobUpdateRequest): ApiResult<JobResponse> =
        safeApiCall(json) { api.updateJob(id, body) }
    override suspend fun delete(id: Long): ApiResult<Unit> = safeApiCallNoContent(json) { api.deleteJob(id) }
    override suspend fun listSelectionRounds(jobId: Long): ApiResult<List<SelectionRoundResponse>> =
        safeApiCall(json) { api.listSelectionRoundsForJob(jobId) }
}

interface DriveRepository {
    suspend fun fetchList(): ApiResult<List<DriveResponse>>
    suspend fun fetchById(id: String): ApiResult<DriveResponse>
    suspend fun create(body: DriveCreateRequest): ApiResult<DriveResponse>
    suspend fun update(id: Long, body: DriveUpdateRequest): ApiResult<DriveResponse>
    suspend fun delete(id: Long): ApiResult<Unit>
    suspend fun listSelectionRounds(driveId: Long): ApiResult<List<SelectionRoundResponse>>
}

class DriveRepositoryImpl(
    private val api: DrivesApi,
    private val json: Json
) : DriveRepository {
    override suspend fun fetchList(): ApiResult<List<DriveResponse>> = safeApiCall(json) { api.listDrives() }
    override suspend fun fetchById(id: String): ApiResult<DriveResponse> = safeApiCall(json) { api.getDrive(id) }
    override suspend fun create(body: DriveCreateRequest): ApiResult<DriveResponse> = safeApiCall(json) { api.createDrive(body) }
    override suspend fun update(id: Long, body: DriveUpdateRequest): ApiResult<DriveResponse> =
        safeApiCall(json) { api.updateDrive(id, body) }
    override suspend fun delete(id: Long): ApiResult<Unit> = safeApiCallNoContent(json) { api.deleteDrive(id) }
    override suspend fun listSelectionRounds(driveId: Long): ApiResult<List<SelectionRoundResponse>> =
        safeApiCall(json) { api.listSelectionRoundsForDrive(driveId) }
}

interface ApplicationRepository {
    suspend fun fetchList(): ApiResult<List<JobApplicationResponse>>
    suspend fun fetchById(id: Long): ApiResult<JobApplicationResponse>
    suspend fun create(body: JobApplicationCreateRequest): ApiResult<JobApplicationResponse>
    suspend fun update(id: Long, body: JobApplicationUpdateRequest): ApiResult<JobApplicationResponse>
    suspend fun delete(id: Long): ApiResult<Unit>
}

class ApplicationRepositoryImpl(
    private val api: ApplicationsApi,
    private val json: Json
) : ApplicationRepository {
    override suspend fun fetchList(): ApiResult<List<JobApplicationResponse>> =
        safeApiCall(json) { api.listApplications() }
    override suspend fun fetchById(id: Long): ApiResult<JobApplicationResponse> =
        safeApiCall(json) { api.getApplication(id) }
    override suspend fun create(body: JobApplicationCreateRequest): ApiResult<JobApplicationResponse> =
        safeApiCall(json) { api.createApplication(body) }
    override suspend fun update(id: Long, body: JobApplicationUpdateRequest): ApiResult<JobApplicationResponse> =
        safeApiCall(json) { api.updateApplication(id, body) }
    override suspend fun delete(id: Long): ApiResult<Unit> =
        safeApiCallNoContent(json) { api.deleteApplication(id) }
}

/** Legacy `/api/students/...` composite endpoints. */
interface StudentLegacyRepository {
    suspend fun createProfileForUser(userId: Long, body: StudentProfileRequest): ApiResult<StudentProfileResponse>
    suspend fun getProfile(studentProfileId: Long): ApiResult<StudentProfileResponse>
    suspend fun addProject(studentProfileId: Long, body: ProjectRequest): ApiResult<ProjectResponse>
    suspend fun addPlatform(studentProfileId: Long, body: PlatformLinkRequest): ApiResult<PlatformResponse>
    suspend fun getEducation(studentProfileId: Long): ApiResult<EducationResponse>
    suspend fun saveEducation(studentProfileId: Long, body: EducationProfileRequest): ApiResult<EducationResponse>
}

class StudentLegacyRepositoryImpl(
    private val api: StudentsLegacyApi,
    private val json: Json
) : StudentLegacyRepository {
    override suspend fun createProfileForUser(userId: Long, body: StudentProfileRequest): ApiResult<StudentProfileResponse> =
        safeApiCall(json) { api.createProfileForUser(userId, body) }
    override suspend fun getProfile(studentProfileId: Long): ApiResult<StudentProfileResponse> =
        safeApiCall(json) { api.getProfile(studentProfileId) }
    override suspend fun addProject(studentProfileId: Long, body: ProjectRequest): ApiResult<ProjectResponse> =
        safeApiCall(json) { api.addProject(studentProfileId, body) }
    override suspend fun addPlatform(studentProfileId: Long, body: PlatformLinkRequest): ApiResult<PlatformResponse> =
        safeApiCall(json) { api.addPlatform(studentProfileId, body) }
    override suspend fun getEducation(studentProfileId: Long): ApiResult<EducationResponse> =
        safeApiCall(json) { api.getEducation(studentProfileId) }
    override suspend fun saveEducation(studentProfileId: Long, body: EducationProfileRequest): ApiResult<EducationResponse> =
        safeApiCall(json) { api.saveEducation(studentProfileId, body) }
}
