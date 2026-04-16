package com.example.placementprojectmp.integration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.integration.data.dto.RoleType
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.repository.AuthRepository
import com.example.placementprojectmp.integration.presentation.state.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun updateEmail(value: String) {
        _uiState.value = _uiState.value.copy(email = value, emailError = null, error = null)
    }

    fun updatePassword(value: String) {
        _uiState.value = _uiState.value.copy(password = value, passwordError = null, error = null)
    }

    fun updateRole(role: RoleType) {
        _uiState.value = _uiState.value.copy(selectedRole = role, roleError = null, error = null)
    }

    fun bootstrapSession(onSessionReady: (Boolean) -> Unit) {
        onSessionReady(false)
    }

    fun submit() {
        val state = _uiState.value
        val email = state.email.trim().lowercase()
        val password = state.password
        val role = state.selectedRole

        var emailError: String? = null
        var passwordError: String? = null

        if (email.isBlank()) emailError = "Email is required"
        if (password.isBlank()) passwordError = "Password is required"
        if (email.isNotBlank() && !isEmailValidForRole(email, role)) {
            emailError = "Use format <name>@${role.name.lowercase()}.edu.com"
        }
        if (emailError != null || passwordError != null) {
            _uiState.value = state.copy(
                emailError = emailError,
                passwordError = passwordError,
                roleError = null
            )
            return
        }

        _uiState.value = state.copy(loading = true, error = null)
        viewModelScope.launch {
            when (val result = repository.loginOrAccess(email, password, role)) {
                is ApiResult.Success -> {
                    _uiState.value = _uiState.value.copy(loading = false, authSuccess = true)
                }

                is ApiResult.Error -> {
                    val debug = result.raw?.takeIf { it.isNotBlank() }?.let { " ($it)" } ?: ""
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                        error = "${result.message}$debug"
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch { repository.logout() }
    }

    private fun isEmailValidForRole(email: String, role: RoleType): Boolean {
        val roleSlug = role.name.lowercase()
        val regex = Regex("^[a-z0-9._%+-]+@${Regex.escape(roleSlug)}\\.edu\\.com$")
        return regex.matches(email)
    }
}

