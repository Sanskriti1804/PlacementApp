package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.local.StudentPersonalDraftStore
import com.example.placementprojectmp.data.model.StudentPersonalDraft
import com.example.placementprojectmp.integration.data.dto.StudentProfileRequestDto
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentPersonalDraftViewModel(
    private val store: StudentPersonalDraftStore,
    private val studentRepository: StudentRepository
) : ViewModel() {
    private val _draft = MutableStateFlow(StudentPersonalDraft())
    val draft: StateFlow<StudentPersonalDraft> = _draft.asStateFlow()

    init {
        viewModelScope.launch {
            store.draftFlow.collect { _draft.value = it }
        }
    }

    fun updateFullName(value: String) = update { copy(fullName = value) }
    fun updateUsername(value: String) = update { copy(username = value) }
    fun updatePhone(value: String) = update { copy(phone = value) }
    fun updateAddress(value: String) = update { copy(address = value) }
    fun updateCity(value: String) = update { copy(city = value) }
    fun updateState(value: String) = update { copy(state = value) }
    fun updateCountry(value: String) = update { copy(country = value) }
    fun updatePinCode(value: String) = update { copy(pinCode = value) }
    fun updateDob(day: String, month: String, year: String) = update {
        copy(day = day, month = month, year = year)
    }

    fun save() {
        viewModelScope.launch {
            val draft = _draft.value
            store.saveDraft(draft)

            val addressLine = buildString {
                if (draft.address.isNotBlank()) append(draft.address.trim())
                if (draft.pinCode.isNotBlank()) {
                    if (isNotEmpty()) append(", ")
                    append(draft.pinCode.trim())
                }
                if (draft.country.isNotBlank()) {
                    if (isNotEmpty()) append(", ")
                    append(draft.country.trim())
                }
            }.ifBlank { null }

            val dob = if (
                draft.day.isNotBlank() &&
                draft.month.isNotBlank() &&
                draft.year.isNotBlank()
            ) {
                "${draft.year.padStart(4, '0')}-${draft.month.padStart(2, '0')}-${draft.day.padStart(2, '0')}"
            } else {
                null
            }

            val request = StudentProfileRequestDto(
                userId = 3L,
                name = draft.fullName.trim().ifBlank { null },
                phoneNumber = draft.phone.trim().ifBlank { null },
                addressLine = addressLine,
                city = draft.city.trim().ifBlank { null },
                state = draft.state.trim().ifBlank { null },
                dob = dob
            )
            when (studentRepository.createProfile(3L, request)) {
                is ApiResult.Success -> Unit
                is ApiResult.Error -> Unit
            }
        }
    }

    private fun update(transform: StudentPersonalDraft.() -> StudentPersonalDraft) {
        _draft.value = _draft.value.transform()
    }
}
