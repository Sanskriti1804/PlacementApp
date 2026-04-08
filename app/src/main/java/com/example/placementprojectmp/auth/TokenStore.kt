package com.example.placementprojectmp.auth

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenStore(private val context: Context) {
    private val tokenKey = stringPreferencesKey("jwt_token")
    private val emailKey = stringPreferencesKey("auth_email")
    private val roleKey = stringPreferencesKey("primary_role")

    val tokenFlow: Flow<String?> = context.dataStore.data.map { prefs: Preferences ->
        prefs[tokenKey]
    }

    val roleFlow: Flow<String?> = context.dataStore.data.map { prefs: Preferences ->
        prefs[roleKey]
    }

    suspend fun saveSession(token: String, email: String, roles: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[tokenKey] = token
            prefs[emailKey] = email
            prefs[roleKey] = roles.firstOrNull().orEmpty()
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(tokenKey)
            prefs.remove(emailKey)
            prefs.remove(roleKey)
        }
    }
}
