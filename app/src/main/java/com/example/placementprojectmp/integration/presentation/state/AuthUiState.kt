package com.example.placementprojectmp.integration.presentation.state

import com.example.placementprojectmp.integration.data.dto.RoleType

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val selectedRole: RoleType = RoleType.STUDENT,
    val loading: Boolean = false,
    val error: String? = null,
    val authSuccess: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val roleError: String? = null
)

