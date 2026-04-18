package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.remote.dto.DriveResponse
import com.example.placementprojectmp.data.repo.DriveRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DriveBrowseUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val drives: List<DriveResponse> = emptyList()
)

class DriveBrowseViewModel(
    private val drives: DriveRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DriveBrowseUiState())
    val state: StateFlow<DriveBrowseUiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = drives.fetchList()) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, drives = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }
}
