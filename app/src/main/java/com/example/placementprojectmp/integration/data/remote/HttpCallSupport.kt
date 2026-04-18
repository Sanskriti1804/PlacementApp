package com.example.placementprojectmp.integration.data.remote

import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

/** Successful responses with no JSON body (e.g. HTTP 204 after DELETE). */
suspend fun safeApiCallNoContent(
    json: Json,
    block: suspend () -> Response<ResponseBody>
): ApiResult<Unit> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            ApiResult.Success(Unit)
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

/** Plain-text body (e.g. `GET /test`). */
suspend fun safeApiCallPlainText(
    json: Json,
    block: suspend () -> Response<ResponseBody>
): ApiResult<String> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            val text = response.body()?.string().orEmpty()
            ApiResult.Success(text)
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
