package com.example.placementprojectmp.integration.data.remote

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.Response
import java.io.IOException

suspend inline fun <reified T> safeApiCall(
    json: Json,
    crossinline block: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResult.Success(body)
            } else {
                ApiResult.Error(code = response.code(), message = "Empty response from server.")
            }
        } else {
            val raw = response.errorBody()?.string()
            ApiResult.Error(
                code = response.code(),
                message = mapHttpCodeToMessage(response.code(), raw, json),
                raw = raw
            )
        }
    } catch (io: IOException) {
        ApiResult.Error(message = "Network error. Please check your internet connection.", raw = io.message)
    } catch (t: Throwable) {
        ApiResult.Error(message = "Unexpected error. Please try again.", raw = t.message)
    }
}

fun mapHttpCodeToMessage(code: Int, raw: String?, json: Json): String {
    val backendMessage = parseBackendMessage(raw, json)
    return when (code) {
        400 -> backendMessage ?: "Invalid request. Check email-role format and required fields."
        401 -> backendMessage ?: "Invalid credentials. Please verify email and password."
        404 -> backendMessage ?: "Requested user/student record was not found."
        500 -> backendMessage ?: "Server error. Please try again shortly."
        else -> backendMessage ?: "Request failed with code $code."
    }
}

private fun parseBackendMessage(raw: String?, json: Json): String? {
    if (raw.isNullOrBlank()) return null
    return runCatching {
        val parsed = json.parseToJsonElement(raw)
        if (parsed is JsonObject) {
            parsed["message"]?.jsonPrimitive?.contentOrNull
                ?: parsed["error"]?.jsonPrimitive?.contentOrNull
                ?: raw
        } else {
            raw
        }
    }.getOrNull()
}

