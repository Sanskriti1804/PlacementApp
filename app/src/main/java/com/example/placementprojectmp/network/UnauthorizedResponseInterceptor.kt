package com.example.placementprojectmp.network

import com.example.placementprojectmp.auth.TokenStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Clears stored JWT on 401 from protected routes so the app can route users to re-login.
 * Skips `/api/auth/` so failed login attempts do not wipe an existing session unnecessarily.
 */
class UnauthorizedResponseInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 401) {
            val path = chain.request().url.encodedPath
            if (!path.contains("/api/auth/")) {
                runBlocking { tokenStore.clearToken() }
            }
        }
        return response
    }
}
