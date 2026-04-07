package com.example.placementprojectmp

import android.app.Application
import com.example.placementprojectmp.di.appModule
import com.example.placementprojectmp.viewmodel.EducationViewModel
import com.example.placementprojectmp.viewmodel.StudentViewModel
import com.example.placementprojectmp.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}