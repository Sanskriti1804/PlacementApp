package com.example.placementprojectmp.di

import com.example.placementprojectmp.data.remote.api.StudentApi
import com.example.placementprojectmp.data.remote.api.StudentApiImpl
import com.example.placementprojectmp.viewmodel.StudentViewModel
import com.example.placementprojectmp.viewmodel.UserViewModel
import com.example.placementprojectmp.data.remote.api.UserApi
import com.example.placementprojectmp.data.remote.api.UserApiImpl
import com.example.placementprojectmp.data.repo.StudentRepo
import com.example.placementprojectmp.data.repo.UserRepo
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
    single { UserViewModel(get()) }

    single<StudentApi> { StudentApiImpl(get()) }
    single { StudentRepo(get()) }
    single { StudentViewModel(get()) }
}