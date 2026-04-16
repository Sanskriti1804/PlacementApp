package com.example.placementprojectmp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.auth.AuthRole
import com.example.placementprojectmp.data.repo.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var selectedRole by mutableStateOf(AuthRole.STUDENT)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isAuthenticated by mutableStateOf(false)
        private set

    fun setRole(role: AuthRole) {
        selectedRole = role
        errorMessage = null
    }

    fun login(onSuccess: (AuthRole) -> Unit = {}) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Email and password are required."
            return
        }
        val normalizedEmail = email.trim().lowercase()
        val roleSlug = selectedRole.name.lowercase()
        val formatRegex = Regex("^[a-z0-9._%+-]+@([a-z0-9-]+)\\.edu\\.com$")
        val match = formatRegex.matchEntire(normalizedEmail)
        if (match == null) {
            errorMessage = "Email must be in format name@role.edu.com"
            return
        }
        val domainRole = match.groupValues.getOrNull(1).orEmpty()
        if (!domainRole.equals(roleSlug, ignoreCase = true)) {
            errorMessage = "Email domain does not match selected role."
            return
        }
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            repository.login(email = normalizedEmail, password = password, role = selectedRole)
                .onSuccess {
                    isAuthenticated = true
                    onSuccess(selectedRole)
                }
                .onFailure { errorMessage = it.message ?: "Unable to authenticate." }
            isLoading = false
        }
    }
}
