package com.example.placementprojectmp.network

import com.example.placementprojectmp.auth.TokenStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenStore.tokenFlow.firstOrNull() }
        val path = chain.request().url.encodedPath
        val isAuthEndpoint = path.contains("/api/auth/")
        val request = chain.request().newBuilder().apply {
            // Do not send a previous session JWT on login/register — backend must not run JWT user load there.
            if (!isAuthEndpoint && !token.isNullOrBlank()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()
        return chain.proceed(request)
    }
}
