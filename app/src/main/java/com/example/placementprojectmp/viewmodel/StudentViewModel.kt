package com.example.placementprojectmp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.model.StudentProfile
import com.example.placementprojectmp.data.repo.StudentRepo
import kotlinx.coroutines.launch

class StudentViewModel (
    private val repo : StudentRepo
) : ViewModel() {
    var profile by mutableStateOf<StudentProfile?>(null)
        private  set    //prevents ext modification

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchStudentProfile(userId : Long){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                profile = repo.getStudentProfile(userId)
            }catch (e : Exception){
                errorMessage = e.message ?: "Error fetching profile"
            }
            finally {
                isLoading = false
            }
        }
    }
}