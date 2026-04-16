package com.example.placementprojectmp.integration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.integration.data.dto.SkillsDto
import com.example.placementprojectmp.integration.data.dto.StudentProfileRequestDto
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.repository.StudentRepository
import com.example.placementprojectmp.integration.domain.model.StudentProfileModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StudentProfileFormState(
    val userId: Long? = null,
    val name: String = "",
    val domainRole: String = "",
    val bio: String = "",
    val dob: String = "",
    val phoneNumber: String = "",
    val photoUrl: String = "",
    val addressLine: String = "",
    val city: String = "",
    val state: String = "",
    val languages: String = "",
    val frameworks: String = "",
    val tools: String = "",
    val platforms: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val data: StudentProfileModel? = null
)

class StudentProfileViewModel(
    private val repository: StudentRepository
) : ViewModel() {
    private val _ui = MutableStateFlow(StudentProfileFormState())
    val ui: StateFlow<StudentProfileFormState> = _ui.asStateFlow()

    fun patch(update: StudentProfileFormState.() -> StudentProfileFormState) {
        _ui.value = _ui.value.update()
    }

    fun submit(userId: Long) {
        val state = _ui.value
        _ui.value = state.copy(loading = true, error = null, success = false)
        viewModelScope.launch {
            val request = StudentProfileRequestDto(
                userId = userId,
                name = state.name.nullIfBlank(),
                domainRole = state.domainRole.nullIfBlank(),
                bio = state.bio.nullIfBlank(),
                dob = state.dob.nullIfBlank(),
                phoneNumber = state.phoneNumber.nullIfBlank(),
                photoUrl = state.photoUrl.nullIfBlank(),
                addressLine = state.addressLine.nullIfBlank(),
                city = state.city.nullIfBlank(),
                state = state.state.nullIfBlank(),
                skills = SkillsDto(
                    languages = state.languages.toListFromCsv(),
                    frameworks = state.frameworks.toListFromCsv(),
                    tools = state.tools.toListFromCsv(),
                    platforms = state.platforms.toListFromCsv()
                )
            )
            when (val result = repository.createProfile(userId, request)) {
                is ApiResult.Success -> _ui.value = _ui.value.copy(loading = false, success = true, data = result.data)
                is ApiResult.Error -> _ui.value = _ui.value.copy(loading = false, error = result.message)
            }
        }
    }

    fun load(studentId: Long) {
        _ui.value = _ui.value.copy(loading = true, error = null)
        viewModelScope.launch {
            when (val result = repository.getProfile(studentId)) {
                is ApiResult.Success -> {
                    val data = result.data
                    _ui.value = _ui.value.copy(
                        loading = false,
                        data = data,
                        userId = data.userId,
                        name = data.name.orEmpty(),
                        domainRole = data.domainRole.orEmpty(),
                        bio = data.bio.orEmpty(),
                        dob = data.dob.orEmpty(),
                        phoneNumber = data.phoneNumber.orEmpty(),
                        photoUrl = data.photoUrl.orEmpty(),
                        addressLine = data.addressLine.orEmpty(),
                        city = data.city.orEmpty(),
                        state = data.state.orEmpty(),
                        languages = data.skills?.languages?.joinToString(", ").orEmpty(),
                        frameworks = data.skills?.frameworks?.joinToString(", ").orEmpty(),
                        tools = data.skills?.tools?.joinToString(", ").orEmpty(),
                        platforms = data.skills?.platforms?.joinToString(", ").orEmpty()
                    )
                }

                is ApiResult.Error -> _ui.value = _ui.value.copy(loading = false, error = result.message)
            }
        }
    }
}

private fun String.nullIfBlank(): String? = trim().ifBlank { null }
private fun String.toListFromCsv(): List<String>? =
    split(",").map { it.trim() }.filter { it.isNotBlank() }.takeIf { it.isNotEmpty() }

