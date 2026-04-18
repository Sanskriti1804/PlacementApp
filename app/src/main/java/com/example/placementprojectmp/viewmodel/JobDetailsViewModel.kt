package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.remote.dto.JobResponse
import com.example.placementprojectmp.data.repo.JobRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JobDetailsViewModel(
    private val jobId: String,
    private val jobRepository: JobRepository
) : ViewModel() {

    private val _job = MutableStateFlow<JobResponse?>(null)
    val job: StateFlow<JobResponse?> = _job.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            if (jobId.isBlank()) {
                _error.value = "not found"
                _job.value = null
                return@launch
            }
            _loading.value = true
            _error.value = null
            when (val result = jobRepository.fetchById(jobId)) {
                is ApiResult.Success -> {
                    _job.value = result.data
                    _error.value = null
                }
                is ApiResult.Error -> {
                    _job.value = null
                    _error.value = result.message
                }
            }
            _loading.value = false
        }
    }
}
