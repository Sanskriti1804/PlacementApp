package com.example.placementprojectmp.di

import com.example.placementprojectmp.auth.TokenStore
import com.example.placementprojectmp.data.remote.NetworkConfig
import com.example.placementprojectmp.data.remote.api.ApplicationsApi
import com.example.placementprojectmp.data.remote.api.BacklogsApi
import com.example.placementprojectmp.data.remote.api.CompaniesApi
import com.example.placementprojectmp.data.remote.api.DepartmentsApi
import com.example.placementprojectmp.data.remote.api.DriveBranchesApi
import com.example.placementprojectmp.data.remote.api.DriveOfferedRolesApi
import com.example.placementprojectmp.data.remote.api.DriveSelectionRoundsApi
import com.example.placementprojectmp.data.remote.api.DrivesApi
import com.example.placementprojectmp.data.remote.api.EducationApi
import com.example.placementprojectmp.data.remote.api.EductionApiImpl
import com.example.placementprojectmp.data.remote.api.EducationProfilesApi
import com.example.placementprojectmp.data.remote.api.HealthApi
import com.example.placementprojectmp.data.remote.api.JobSelectionRoundsApi
import com.example.placementprojectmp.data.remote.api.JobsApi
import com.example.placementprojectmp.data.remote.api.MetaApi
import com.example.placementprojectmp.data.remote.api.MetaApiImpl
import com.example.placementprojectmp.data.remote.api.PlatformsApi
import com.example.placementprojectmp.data.remote.api.ProjectsApi
import com.example.placementprojectmp.data.remote.api.RolesCatalogApi
import com.example.placementprojectmp.data.remote.api.SkillsApi
import com.example.placementprojectmp.data.remote.api.SpringMetaApi
import com.example.placementprojectmp.data.remote.api.StaffProfilesApi
import com.example.placementprojectmp.data.remote.api.StudentApi
import com.example.placementprojectmp.data.remote.api.StudentApiImpl
import com.example.placementprojectmp.data.remote.api.StudentExperiencesApi
import com.example.placementprojectmp.data.remote.api.StudentProfilesApi
import com.example.placementprojectmp.data.remote.api.StudentsLegacyApi
import com.example.placementprojectmp.data.remote.api.UserApi
import com.example.placementprojectmp.data.remote.api.UserApiImpl
import com.example.placementprojectmp.data.remote.api.UsersApi
import com.example.placementprojectmp.data.local.StudentPersonalDraftStore
import com.example.placementprojectmp.data.repo.ApplicationRepository
import com.example.placementprojectmp.data.repo.ApplicationRepositoryImpl
import com.example.placementprojectmp.data.repo.AuthRepository
import com.example.placementprojectmp.data.repo.BackendApis
import com.example.placementprojectmp.data.repo.BackendIntegrationRepository
import com.example.placementprojectmp.data.repo.DriveRepository
import com.example.placementprojectmp.data.repo.DriveRepositoryImpl
import com.example.placementprojectmp.data.repo.JobRepository
import com.example.placementprojectmp.data.repo.JobRepositoryImpl
import com.example.placementprojectmp.data.repo.MetaRepository
import com.example.placementprojectmp.data.repo.MetaRepositoryImpl
import com.example.placementprojectmp.data.repo.SpringMetaRepository
import com.example.placementprojectmp.data.repo.SpringMetaRepositoryImpl
import com.example.placementprojectmp.data.repo.StudentLegacyRepository
import com.example.placementprojectmp.data.repo.StudentLegacyRepositoryImpl
import com.example.placementprojectmp.integration.data.api.StudentProfileApi
import com.example.placementprojectmp.integration.data.repository.StudentRepository
import com.example.placementprojectmp.integration.presentation.viewmodel.PlatformLinksViewModel
import com.example.placementprojectmp.integration.presentation.viewmodel.StudentProfileViewModel
import com.example.placementprojectmp.network.AuthApi
import com.example.placementprojectmp.network.AuthInterceptor
import com.example.placementprojectmp.viewmodel.AuthViewModel
import com.example.placementprojectmp.viewmodel.BackendApplicationsViewModel
import com.example.placementprojectmp.viewmodel.BackendDirectoryViewModel
import com.example.placementprojectmp.viewmodel.BackendMetaViewModel
import com.example.placementprojectmp.viewmodel.DriveBrowseViewModel
import com.example.placementprojectmp.viewmodel.DriveDetailsViewModel
import com.example.placementprojectmp.viewmodel.EducationViewModel
import com.example.placementprojectmp.viewmodel.JobBrowseViewModel
import com.example.placementprojectmp.viewmodel.JobDetailsViewModel
import com.example.placementprojectmp.viewmodel.PlacementWorkspaceViewModel
import com.example.placementprojectmp.viewmodel.StaffCandidateDetailViewModel
import com.example.placementprojectmp.viewmodel.StaffCompanyDetailViewModel
import com.example.placementprojectmp.viewmodel.StaffDriveViewModel
import com.example.placementprojectmp.viewmodel.StaffJobDetailViewModel
import com.example.placementprojectmp.viewmodel.StudentOnboardingViewModel
import com.example.placementprojectmp.viewmodel.StudentPersonalDraftViewModel
import com.example.placementprojectmp.viewmodel.StudentViewModel
import com.example.placementprojectmp.viewmodel.UserViewModel
import com.example.placementprojectmp.data.repo.EducationRepo
import com.example.placementprojectmp.data.repo.StudentRepo
import com.example.placementprojectmp.data.repo.UserRepo
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(get())
            }
        }
    }
    single { TokenStore(get()) }
    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<AuthApi> { get<Retrofit>().create(AuthApi::class.java) }
    single { AuthRepository(get(), get(), get()) }
    viewModel { AuthViewModel(get()) }

    single<HealthApi> { get<Retrofit>().create(HealthApi::class.java) }
    single<SpringMetaApi> { get<Retrofit>().create(SpringMetaApi::class.java) }
    single<UsersApi> { get<Retrofit>().create(UsersApi::class.java) }
    single<CompaniesApi> { get<Retrofit>().create(CompaniesApi::class.java) }
    single<DepartmentsApi> { get<Retrofit>().create(DepartmentsApi::class.java) }
    single<RolesCatalogApi> { get<Retrofit>().create(RolesCatalogApi::class.java) }
    single<JobsApi> { get<Retrofit>().create(JobsApi::class.java) }
    single<DrivesApi> { get<Retrofit>().create(DrivesApi::class.java) }
    single<ApplicationsApi> { get<Retrofit>().create(ApplicationsApi::class.java) }
    single<StaffProfilesApi> { get<Retrofit>().create(StaffProfilesApi::class.java) }
    single<StudentProfilesApi> { get<Retrofit>().create(StudentProfilesApi::class.java) }
    single<StudentsLegacyApi> { get<Retrofit>().create(StudentsLegacyApi::class.java) }
    single<EducationProfilesApi> { get<Retrofit>().create(EducationProfilesApi::class.java) }
    single<StudentExperiencesApi> { get<Retrofit>().create(StudentExperiencesApi::class.java) }
    single<SkillsApi> { get<Retrofit>().create(SkillsApi::class.java) }
    single<PlatformsApi> { get<Retrofit>().create(PlatformsApi::class.java) }
    single<ProjectsApi> { get<Retrofit>().create(ProjectsApi::class.java) }
    single<BacklogsApi> { get<Retrofit>().create(BacklogsApi::class.java) }
    single<JobSelectionRoundsApi> { get<Retrofit>().create(JobSelectionRoundsApi::class.java) }
    single<DriveSelectionRoundsApi> { get<Retrofit>().create(DriveSelectionRoundsApi::class.java) }
    single<DriveBranchesApi> { get<Retrofit>().create(DriveBranchesApi::class.java) }
    single<DriveOfferedRolesApi> { get<Retrofit>().create(DriveOfferedRolesApi::class.java) }

    single {
        BackendApis(
            health = get(),
            meta = get(),
            users = get(),
            companies = get(),
            departments = get(),
            rolesCatalog = get(),
            jobs = get(),
            drives = get(),
            applications = get(),
            staffProfiles = get(),
            studentProfiles = get(),
            studentsLegacy = get(),
            educationProfiles = get(),
            studentExperiences = get(),
            skills = get(),
            platforms = get(),
            projects = get(),
            backlogs = get(),
            jobSelectionRounds = get(),
            driveSelectionRounds = get(),
            driveBranches = get(),
            driveOfferedRoles = get()
        )
    }
    single { BackendIntegrationRepository(get(), get()) }
    single<JobRepository> { JobRepositoryImpl(get(), get()) }
    single<DriveRepository> { DriveRepositoryImpl(get(), get()) }
    single<ApplicationRepository> { ApplicationRepositoryImpl(get(), get()) }
    single<SpringMetaRepository> { SpringMetaRepositoryImpl(get(), get()) }
    single<StudentLegacyRepository> { StudentLegacyRepositoryImpl(get(), get()) }

    single<UserApi> { UserApiImpl(get(), get()) }
    single { UserRepo(get()) }
    viewModel<UserViewModel> { UserViewModel(get()) }

    single<StudentApi> { StudentApiImpl(get()) }
    single { StudentRepo(get()) }
    viewModel<StudentViewModel> { StudentViewModel(get()) }
    single { StudentPersonalDraftStore(get()) }
    single<StudentProfileApi> { get<Retrofit>().create(StudentProfileApi::class.java) }
    single { StudentRepository(get(), get()) }
    viewModel<StudentPersonalDraftViewModel> { StudentPersonalDraftViewModel(get(), get()) }
    viewModel<StudentProfileViewModel> { StudentProfileViewModel(get()) }
    viewModel<PlatformLinksViewModel> { PlatformLinksViewModel(get()) }

    single<EducationApi> { EductionApiImpl(get()) }
    single { EducationRepo(get()) }
    single<MetaApi> { MetaApiImpl(get()) }
    single<MetaRepository> { MetaRepositoryImpl(get()) }
    viewModel<EducationViewModel> { EducationViewModel(get(), get()) }

    viewModel { BackendApplicationsViewModel(get()) }
    viewModel { BackendDirectoryViewModel(get()) }
    viewModel { BackendMetaViewModel(get()) }
    viewModel { JobBrowseViewModel(get()) }
    viewModel { DriveBrowseViewModel(get()) }
    viewModel { StudentOnboardingViewModel(get()) }
    viewModel { (jobId: String) -> JobDetailsViewModel(jobId, get()) }
    viewModel { (driveId: String) -> DriveDetailsViewModel(driveId, get()) }

    viewModel { StaffDriveViewModel() }
    viewModel { StaffCompanyDetailViewModel() }
    viewModel { StaffJobDetailViewModel() }
    viewModel { StaffCandidateDetailViewModel() }
    viewModel { PlacementWorkspaceViewModel() }
}
