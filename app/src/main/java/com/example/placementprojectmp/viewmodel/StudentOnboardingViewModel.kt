package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.remote.dto.EducationProfileRequest
import com.example.placementprojectmp.data.remote.dto.EducationResponse
import com.example.placementprojectmp.data.remote.dto.PlatformLinkRequest
import com.example.placementprojectmp.data.remote.dto.ProjectRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileRequest
import com.example.placementprojectmp.data.remote.dto.StudentProfileResponse
import com.example.placementprojectmp.data.repo.StudentLegacyRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StudentOnboardingUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val profile: StudentProfileResponse? = null,
    val education: EducationResponse? = null
)

/**
 * Legacy `/api/students/...` flows: profile, projects, platforms, education.
 * [userId] is [com.example.placementprojectmp.data.remote.dto.UserResponse.id]; [studentProfileId] is [StudentProfileResponse.id].
 */
class StudentOnboardingViewModel(
    private val legacy: StudentLegacyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StudentOnboardingUiState())
    val state: StateFlow<StudentOnboardingUiState> = _state.asStateFlow()

    fun createProfileForUser(userId: Long, body: StudentProfileRequest) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = legacy.createProfileForUser(userId, body)) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, profile = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun loadProfile(studentProfileId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = legacy.getProfile(studentProfileId)) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, profile = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun addProject(studentProfileId: Long, body: ProjectRequest) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = legacy.addProject(studentProfileId, body)) {
                is ApiResult.Success -> loadProfile(studentProfileId)
                is ApiResult.Error -> _state.update { it.copy(loading = false, error = r.message) }
            }
        }
    }

    fun addPlatform(studentProfileId: Long, body: PlatformLinkRequest) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = legacy.addPlatform(studentProfileId, body)) {
                is ApiResult.Success -> loadProfile(studentProfileId)
                is ApiResult.Error -> _state.update { it.copy(loading = false, error = r.message) }
            }
        }
    }

    fun loadEducation(studentProfileId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = legacy.getEducation(studentProfileId)) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, education = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun saveEducation(studentProfileId: Long, body: EducationProfileRequest) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = legacy.saveEducation(studentProfileId, body)) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, education = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }
}
