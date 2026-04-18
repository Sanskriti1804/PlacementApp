package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.remote.dto.JobApplicationCreateRequest
import com.example.placementprojectmp.data.remote.dto.JobApplicationResponse
import com.example.placementprojectmp.data.remote.dto.JobApplicationUpdateRequest
import com.example.placementprojectmp.data.repo.ApplicationRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BackendApplicationsUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val applications: List<JobApplicationResponse> = emptyList()
)

/**
 * Job applications CRUD at `/api/applications`.
 * [JobApplicationResponse.studentId] is **StudentProfile.id**. List is not server-filtered by student — filter client-side until backend adds a query param (see [com.example.placementprojectmp.data.remote.dto.BackendIntegrationGaps]).
 */
class BackendApplicationsViewModel(
    private val applications: ApplicationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BackendApplicationsUiState())
    val state: StateFlow<BackendApplicationsUiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = applications.fetchList()) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, applications = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun submitApplication(body: JobApplicationCreateRequest) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = applications.create(body)) {
                is ApiResult.Success -> refresh()
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun updateApplication(id: Long, body: JobApplicationUpdateRequest) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = applications.update(id, body)) {
                is ApiResult.Success -> refresh()
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }
}
