package com.example.placementprojectmp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.local.DummyEducationMapping
import com.example.placementprojectmp.data.model.EducationProfile
import com.example.placementprojectmp.data.repo.EducationRepo
import com.example.placementprojectmp.data.repo.MetaRepository
import kotlinx.coroutines.launch

class EducationViewModel(
    private val repo : EducationRepo,
    private val metaRepository: MetaRepository
): ViewModel() {
    var education by mutableStateOf<EducationProfile?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var courses by mutableStateOf<List<String>>(emptyList())
        private set

    var domains by mutableStateOf<List<String>>(emptyList())
        private set

    private val domainCache = mutableMapOf<String, List<String>>()

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

    fun fetchCourses() {
        viewModelScope.launch {
            try {
                isLoading = true
                val fromApi = runCatching { metaRepository.getCourses() }.getOrNull()
                courses = if (!fromApi.isNullOrEmpty()) fromApi else DummyEducationMapping.getAllCourses()
            } catch (e: Exception) {
                Log.e("EducationVM", "Error fetching courses", e)
                courses = DummyEducationMapping.getAllCourses()
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchDomains(course: String) {
        viewModelScope.launch {
            try {
                if (domainCache.containsKey(course)) {
                    domains = domainCache[course].orEmpty()
                    return@launch
                }

                isLoading = true
                val fromApi = runCatching { metaRepository.getDomains(course) }.getOrNull()
                val result = if (!fromApi.isNullOrEmpty()) fromApi else DummyEducationMapping.getDomainsForCourse(course)
                domains = result
                domainCache[course] = result
            } catch (e: Exception) {
                Log.e("EducationVM", "Error fetching domains", e)
                val fallback = DummyEducationMapping.getDomainsForCourse(course)
                domains = fallback
                domainCache[course] = fallback
            } finally {
                isLoading = false
            }
        }
    }
}