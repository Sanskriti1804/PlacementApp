package com.example.placementprojectmp.integration.data.repository

import com.example.placementprojectmp.auth.TokenStore
import com.example.placementprojectmp.integration.data.api.AuthApi
import com.example.placementprojectmp.integration.data.dto.AuthRequestDto
import com.example.placementprojectmp.integration.data.dto.AuthResponseDto
import com.example.placementprojectmp.integration.data.dto.RegisterStudentRequestDto
import com.example.placementprojectmp.integration.data.dto.RoleType
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.remote.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class AuthRepository(
    private val api: AuthApi,
    private val tokenStore: TokenStore,
    private val json: Json
) {
    val tokenFlow: Flow<String?> = tokenStore.tokenFlow

    suspend fun loginOrAccess(
        email: String,
        password: String,
        role: RoleType
    ): ApiResult<AuthResponseDto> {
        val request = AuthRequestDto(email = email.trim(), password = password, role = role)
        val accessResult = safeApiCall(json) { api.access(request) }
        return when (accessResult) {
            is ApiResult.Success -> {
                persist(accessResult.data, role)
                accessResult
            }

            is ApiResult.Error -> {
                if (accessResult.code == 404 && role == RoleType.STUDENT) {
                    val registerResult = safeApiCall(json) {
                        api.registerStudent(
                            RegisterStudentRequestDto(
                                email = email.trim(),
                                password = password,
                                passwordBased = true,
                                role = RoleType.STUDENT
                            )
                        )
                    }
                    if (registerResult is ApiResult.Success) persist(registerResult.data, role)
                    registerResult
                } else {
                    val loginFallback = safeApiCall(json) { api.login(request) }
                    if (loginFallback is ApiResult.Success) persist(loginFallback.data, role)
                    loginFallback
                }
            }
        }
    }

    suspend fun logout() {
        tokenStore.clearToken()
    }

    private suspend fun persist(data: AuthResponseDto, role: RoleType) {
        tokenStore.saveSession(data.token, data.email, data.roles)
    }
}

