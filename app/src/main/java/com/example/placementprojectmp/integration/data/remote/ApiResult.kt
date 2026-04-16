package com.example.placementprojectmp.integration.data.remote

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val code: Int? = null,
        val message: String,
        val raw: String? = null
    ) : ApiResult<Nothing>()
}

