package com.example.placementprojectmp.data.repo

import com.example.placementprojectmp.auth.AuthRequest
import com.example.placementprojectmp.auth.AuthResponse
import com.example.placementprojectmp.auth.AuthRole
import com.example.placementprojectmp.auth.RegisterRequest
import com.example.placementprojectmp.auth.TokenStore
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.remote.safeApiCall
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
            val normalizedEmail = email.trim().lowercase()
            val request = AuthRequest(
                email = normalizedEmail,
                password = password,
                role = role.name.uppercase()
            )
            val roleCandidates = listOf(role.name.uppercase())

            var fallbackCode: Int? = null
            var rawError: String? = null

            roleCandidates.forEach { candidateRole ->
                val roleRequest = request.copy(role = candidateRole)
                val accessResponse = authApi.access(roleRequest)
                if (accessResponse.isSuccessful) {
                    val body = accessResponse.body() ?: throw IllegalStateException("Empty authentication response.")
                    tokenStore.saveSession(body.token, body.email, body.roles)
                    return@runCatching body
                }

                fallbackCode = accessResponse.code()
                rawError = accessResponse.errorBody()?.string().takeIf { !it.isNullOrBlank() } ?: rawError

                // New student path: explicit register if access does not authenticate.
                if (candidateRole == AuthRole.STUDENT.name && accessResponse.code() != 401) {
                    val registerResponse = authApi.registerStudent(
                        RegisterRequest(
                            email = normalizedEmail,
                            password = password,
                            passwordBased = true,
                            role = AuthRole.STUDENT.name
                        )
                    )
                    if (registerResponse.isSuccessful) {
                        val body = registerResponse.body() ?: throw IllegalStateException("Empty authentication response.")
                        tokenStore.saveSession(body.token, body.email, body.roles)
                        return@runCatching body
                    }
                    fallbackCode = registerResponse.code()
                    rawError = registerResponse.errorBody()?.string().takeIf { !it.isNullOrBlank() } ?: rawError
                }

                val loginResponse = authApi.login(roleRequest)
                if (loginResponse.isSuccessful) {
                    val body = loginResponse.body() ?: throw IllegalStateException("Empty authentication response.")
                    tokenStore.saveSession(body.token, body.email, body.roles)
                    return@runCatching body
                }
                fallbackCode = loginResponse.code()
                rawError = loginResponse.errorBody()?.string().takeIf { !it.isNullOrBlank() } ?: rawError
            }

            val backendMessage = rawError?.takeIf { it.isNotBlank() }?.let { safeBackendMessage(it) }
            val message = when (fallbackCode) {
                400 -> backendMessage ?: "Invalid email-role format."
                401 -> backendMessage ?: "Incorrect email or password"
                404 -> backendMessage ?: "User not found."
                else -> backendMessage ?: "Authentication failed. Please verify your credentials."
            }
            throw IllegalStateException(message)
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

    /** `POST /api/auth/login` — persists JWT on success. */
    suspend fun loginExchange(request: AuthRequest): ApiResult<AuthResponse> =
        when (val r = safeApiCall(json) { authApi.login(request) }) {
            is ApiResult.Success -> {
                tokenStore.saveSession(r.data.token, r.data.email, r.data.roles)
                r
            }
            is ApiResult.Error -> r
        }

    /** `POST /api/auth/access` — persists JWT on success. */
    suspend fun accessExchange(request: AuthRequest): ApiResult<AuthResponse> =
        when (val r = safeApiCall(json) { authApi.access(request) }) {
            is ApiResult.Success -> {
                tokenStore.saveSession(r.data.token, r.data.email, r.data.roles)
                r
            }
            is ApiResult.Error -> r
        }

    /** `POST /api/auth/register/student` — persists JWT on success. */
    suspend fun registerStudentExchange(request: RegisterRequest): ApiResult<AuthResponse> =
        when (val r = safeApiCall(json) { authApi.registerStudent(request) }) {
            is ApiResult.Success -> {
                tokenStore.saveSession(r.data.token, r.data.email, r.data.roles)
                r
            }
            is ApiResult.Error -> r
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
