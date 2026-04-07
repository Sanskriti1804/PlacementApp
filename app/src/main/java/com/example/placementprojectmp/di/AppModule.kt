package com.example.placementprojectmp.di

import com.example.placementprojectmp.data.remote.api.EducationApi
import com.example.placementprojectmp.data.remote.api.EductionApiImpl
import com.example.placementprojectmp.data.remote.api.MetaApi
import com.example.placementprojectmp.data.remote.api.MetaApiImpl
import com.example.placementprojectmp.data.remote.api.StudentApi
import com.example.placementprojectmp.data.remote.api.StudentApiImpl
import com.example.placementprojectmp.data.repo.MetaRepository
import com.example.placementprojectmp.data.repo.MetaRepositoryImpl
import com.example.placementprojectmp.viewmodel.EducationViewModel
import com.example.placementprojectmp.viewmodel.StudentViewModel
import com.example.placementprojectmp.viewmodel.UserViewModel
import com.example.placementprojectmp.data.remote.api.UserApi
import com.example.placementprojectmp.data.remote.api.UserApiImpl
import com.example.placementprojectmp.data.repo.EducationRepo
import com.example.placementprojectmp.data.repo.StudentRepo
import com.example.placementprojectmp.data.repo.UserRepo
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single{
        HttpClient {
            install(ContentNegotiation){
                json(get())
            }
        }
    }

    single<UserApi> { UserApiImpl(get(), get()) }
    single { UserRepo(get()) }
    viewModel<UserViewModel> { UserViewModel(get()) }

    single<StudentApi> { StudentApiImpl(get()) }
    single { StudentRepo(get()) }
    viewModel<StudentViewModel> { StudentViewModel(get()) }

    single<EducationApi> { EductionApiImpl(get()) }
    single { EducationRepo(get()) }
    single<MetaApi> { MetaApiImpl(get()) }
    single<MetaRepository> { MetaRepositoryImpl(get()) }
    viewModel<EducationViewModel> { EducationViewModel(get(), get()) }
}