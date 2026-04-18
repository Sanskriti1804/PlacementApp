package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.remote.dto.JobResponse
import com.example.placementprojectmp.data.repo.JobRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class JobBrowseUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val jobs: List<JobResponse> = emptyList()
)

class JobBrowseViewModel(
    private val jobs: JobRepository
) : ViewModel() {

    private val _state = MutableStateFlow(JobBrowseUiState())
    val state: StateFlow<JobBrowseUiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = jobs.fetchList()) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, jobs = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }
}
