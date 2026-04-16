package com.example.placementprojectmp.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.placementprojectmp.data.model.StudentDraft
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.personalDraftDataStore by preferencesDataStore(name = "student_personal_draft")

class StudentPersonalDraftStore(private val context: Context) {
    private val fullNameKey = stringPreferencesKey("full_name")
    private val usernameKey = stringPreferencesKey("username")
    private val phoneKey = stringPreferencesKey("phone")
    private val addressKey = stringPreferencesKey("address")
    private val cityKey = stringPreferencesKey("city")
    private val stateKey = stringPreferencesKey("state")
    private val countryKey = stringPreferencesKey("country")
    private val pinCodeKey = stringPreferencesKey("pin_code")
    private val dayKey = stringPreferencesKey("dob_day")
    private val monthKey = stringPreferencesKey("dob_month")
    private val yearKey = stringPreferencesKey("dob_year")

    val draftFlow: Flow<StudentDraft> = context.personalDraftDataStore.data.map { prefs: Preferences ->
        StudentDraft(
            fullName = prefs[fullNameKey].orEmpty(),
            username = prefs[usernameKey].orEmpty(),
            phone = prefs[phoneKey].orEmpty(),
            address = prefs[addressKey].orEmpty(),
            city = prefs[cityKey].orEmpty(),
            state = prefs[stateKey].orEmpty(),
            country = prefs[countryKey].orEmpty(),
            pinCode = prefs[pinCodeKey].orEmpty(),
            day = prefs[dayKey].orEmpty(),
            month = prefs[monthKey].orEmpty(),
            year = prefs[yearKey].orEmpty()
        )
    }

    suspend fun saveDraft(draft: StudentDraft) {
        context.personalDraftDataStore.edit { prefs ->
            prefs[fullNameKey] = draft.fullName
            prefs[usernameKey] = draft.username
            prefs[phoneKey] = draft.phone
            prefs[addressKey] = draft.address
            prefs[cityKey] = draft.city
            prefs[stateKey] = draft.state
            prefs[countryKey] = draft.country
            prefs[pinCodeKey] = draft.pinCode
            prefs[dayKey] = draft.day
            prefs[monthKey] = draft.month
            prefs[yearKey] = draft.year
        }
    }
}
