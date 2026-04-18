package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.repo.BackendIntegrationRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject

data class BackendMetaUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val branches: List<String> = emptyList(),
    val courses: List<String> = emptyList(),
    val allMeta: JsonObject? = null
)

/**
 * Meta catalog (branches / courses / domains / aggregate). UI layer not included — consume [state] from a screen later.
 */
class BackendMetaViewModel(
    private val backend: BackendIntegrationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BackendMetaUiState())
    val state: StateFlow<BackendMetaUiState> = _state.asStateFlow()

    init {
        refreshBranches()
    }

    fun refreshBranches() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = backend.metaBranches()) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, branches = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun refreshCourses() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = backend.metaCourses()) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, courses = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun refreshAllMeta() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = backend.metaAll()) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, allMeta = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun loadCoursesForBranch(branch: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = backend.metaCoursesForBranch(branch)) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, courses = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }
}
