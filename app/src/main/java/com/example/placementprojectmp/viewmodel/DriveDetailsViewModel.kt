package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.remote.dto.DriveResponse
import com.example.placementprojectmp.data.repo.DriveRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DriveDetailsViewModel(
    private val driveId: String,
    private val driveRepository: DriveRepository
) : ViewModel() {

    private val _drive = MutableStateFlow<DriveResponse?>(null)
    val drive: StateFlow<DriveResponse?> = _drive.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            if (driveId.isBlank()) {
                _error.value = "not found"
                _drive.value = null
                return@launch
            }
            _loading.value = true
            _error.value = null
            when (val result = driveRepository.fetchById(driveId)) {
                is ApiResult.Success -> {
                    _drive.value = result.data
                    _error.value = null
                }
                is ApiResult.Error -> {
                    _drive.value = null
                    _error.value = result.message
                }
            }
            _loading.value = false
        }
    }
}
