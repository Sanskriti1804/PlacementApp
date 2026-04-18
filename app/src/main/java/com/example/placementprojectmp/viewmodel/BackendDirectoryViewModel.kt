package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.remote.dto.CompanyDto
import com.example.placementprojectmp.data.remote.dto.UserDto
import com.example.placementprojectmp.data.repo.BackendIntegrationRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BackendDirectoryUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val users: List<UserDto> = emptyList(),
    val companies: List<CompanyDto> = emptyList()
)

/** Users + companies directory for admin/staff flows; consumes `/api/users` and `/api/companies`. */
class BackendDirectoryViewModel(
    private val backend: BackendIntegrationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BackendDirectoryUiState())
    val state: StateFlow<BackendDirectoryUiState> = _state.asStateFlow()

    init {
        refreshUsers()
        refreshCompanies()
    }

    fun refreshUsers() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = backend.usersList()) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, users = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }

    fun refreshCompanies() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            when (val r = backend.companiesList()) {
                is ApiResult.Success -> _state.update {
                    it.copy(loading = false, companies = r.data, error = null)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(loading = false, error = r.message)
                }
            }
        }
    }
}
