package com.example.placementprojectmp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.model.EducationProfile
import com.example.placementprojectmp.data.repo.EducationRepo
import kotlinx.coroutines.launch

class EducationViewModel(
    private val repo : EducationRepo
): ViewModel() {
    var education by mutableStateOf<EducationProfile?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun fetchEducation(studentId : Long){
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                education = repo.getEducation(studentId)
            }catch (e : Exception){
                error = e.message ?: "Error fetching education"
            }
            finally {
                isLoading = false

            }
        }
    }

    fun saveEducation(studentId: Long, request : EducationProfile) {
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                education = repo.saveEducation(studentId, request)
            } catch (e: Exception) {
                error = e.message ?: "Error saving education"
            } finally {
                isLoading = false
            }
        }
    }
}