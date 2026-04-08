package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.auth.AuthRequest
import com.example.placementprojectmp.auth.AuthResponse
import com.example.placementprojectmp.auth.AuthRole
import com.example.placementprojectmp.auth.TokenStore
import com.example.placementprojectmp.network.AuthApi
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val json: Json
) {
    val tokenFlow: Flow<String?> = tokenStore.tokenFlow
    val roleFlow: Flow<String?> = tokenStore.roleFlow

    suspend fun login(email: String, password: String, role: AuthRole): Result<AuthResponse> {
        return runCatching {
            val response = authApi.access(
                AuthRequest(
                    email = email.trim(),
                    password = password,
                    role = role.name
                )
            )
            if (!response.isSuccessful) {
                val backendMessage = response.errorBody()?.string()
                    ?.takeIf { it.isNotBlank() }
                    ?.let { safeBackendMessage(it) }
                throw IllegalStateException(backendMessage ?: "Authentication failed. Please verify your credentials.")
            }
            val body = response.body() ?: throw IllegalStateException("Empty authentication response.")
            tokenStore.saveSession(body.token, body.email, body.roles)
            body
        }.recoverCatching { throwable ->
            when (throwable) {
                is IOException -> throw IllegalStateException("Network error. Please check your internet connection.")
                is HttpException -> throw IllegalStateException("Server error (${throwable.code()}). Please try again.")
                is IllegalStateException -> throw throwable
                else -> throw IllegalStateException("Login failed. Please try again.")
            }
        }
    }

    suspend fun logout() {
        tokenStore.clearToken()
    }

    private fun safeBackendMessage(raw: String): String {
        return runCatching {
            val parsed = json.parseToJsonElement(raw)
            if (parsed is JsonObject) {
                parsed["message"]?.jsonPrimitive?.contentOrNull
                    ?: parsed["error"]?.jsonPrimitive?.contentOrNull
                    ?: raw
            } else raw
        }.getOrDefault(raw).take(300)
    }
}
