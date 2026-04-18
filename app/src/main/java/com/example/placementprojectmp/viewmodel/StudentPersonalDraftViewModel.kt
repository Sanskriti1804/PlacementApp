package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.local.StudentPersonalDraftStore
import com.example.placementprojectmp.data.model.StudentDraft
import com.example.placementprojectmp.data.remote.dto.StudentProfileRequest
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class StudentPersonalDraftViewModel(
    private val store: StudentPersonalDraftStore,
    private val studentRepository: StudentRepository
) : ViewModel() {
    private val _draft = MutableStateFlow(StudentDraft())
    val draft: StateFlow<StudentDraft> = _draft.asStateFlow()

    var profileImageUri by androidx.compose.runtime.mutableStateOf<android.net.Uri?>(null)
        private set

    var projectImagesUris by androidx.compose.runtime.mutableStateOf<List<android.net.Uri>>(emptyList())
        private set

    fun updateProfileImageUri(uri: android.net.Uri?) {
        profileImageUri = uri
        update { copy(profileImageUri = uri?.toString() ?: "") }
    }

    fun updateProjectImagesUris(uris: List<android.net.Uri>) {
        projectImagesUris = uris
        update { copy(projectImageUri = uris.joinToString(",") { it.toString() }) }
    }

    init {
        viewModelScope.launch {
            store.draftFlow.collect { draftState -> 
                _draft.value = draftState 
                if (draftState.profileImageUri.isNotBlank()) {
                    profileImageUri = android.net.Uri.parse(draftState.profileImageUri)
                }
                if (draftState.projectImageUri.isNotBlank()) {
                    projectImagesUris = draftState.projectImageUri.split(",").filter { it.isNotBlank() }.map { android.net.Uri.parse(it) }
                }
            }
        }
    }

    fun updateFullName(value: String) = update { copy(fullName = value) }
    fun updateUsername(value: String) = update { copy(username = value) }
    fun updateEmail(value: String) = update { copy(email = value) }
    fun updateRole(value: String) = update { copy(role = value) }
    fun updatePhone(value: String) = update { copy(phone = value) }
    fun updateAddress(value: String) = update { copy(address = value) }
    fun updateCity(value: String) = update { copy(city = value) }
    fun updateState(value: String) = update { copy(state = value) }
    fun updateCountry(value: String) = update { copy(country = value) }
    fun updatePinCode(value: String) = update { copy(pinCode = value) }
    fun updateDob(day: String, month: String, year: String) = update {
        copy(day = day, month = month, year = year)
    }
    fun updateConnectorLinksJson(value: String) = update { copy(connectorLinksJson = value) }
    fun updateResumePdfUri(value: String) = update { copy(resumePdfUri = value) }

    // Education Updates
    fun updateUniversity(value: String) = update { copy(university = value) }
    fun updateCourse(value: String) = update { copy(course = value) }
    fun updateSelectedYear(value: String) = update { copy(selectedYear = value) }
    fun updateClass12Percent(value: String) = update { copy(class12Percent = value) }
    fun updateSchool12Name(value: String) = update { copy(school12Name = value) }
    fun updatePassYear12(value: String) = update { copy(passYear12 = value) }
    fun updateClass10Percent(value: String) = update { copy(class10Percent = value) }
    fun updateSchool10Name(value: String) = update { copy(school10Name = value) }
    fun updatePassYear10(value: String) = update { copy(passYear10 = value) }
    fun updateGradCgpa(value: String) = update { copy(gradCgpa = value) }
    fun updateGradPassYear(value: String) = update { copy(gradPassYear = value) }
    fun updateActiveBacklogsEnabled(value: Boolean) = update { copy(activeBacklogsEnabled = value) }
    fun updateBacklogCount(value: Int) = update { copy(backlogCount = value) }
    fun updateBacklogSubjects(value: String) = update { copy(backlogSubjects = value) }
    fun updateAcademicGapEnabled(value: Boolean) = update { copy(academicGapEnabled = value) }
    fun updateGapYears(value: String) = update { copy(gapYears = value) }
    fun updateGapExplanation(value: String) = update { copy(gapExplanation = value) }

    // Skills Updates
    fun updateLanguagesSelected(value: Set<String>) = update { copy(languagesSelected = value) }
    fun updateFrameworksSelected(value: Set<String>) = update { copy(frameworksSelected = value) }
    fun updateToolsSelected(value: Set<String>) = update { copy(toolsSelected = value) }
    fun updateSoftSkillsSelected(value: Set<String>) = update { copy(softSkillsSelected = value) }

    // Experience Updates
    fun updateHasWorkExperience(value: Boolean) = update { copy(hasWorkExperience = value) }
    fun updateJobEntriesJson(value: String) = update { copy(jobEntriesJson = value) }

    // Projects Updates
    fun updateProjectName(value: String) = update { copy(projectName = value) }
    fun updateProjectImageUriString(value: String) = update { copy(projectImageUri = value) }
    fun updateProjectLink(value: String) = update { copy(projectLink = value) }
    fun updateProjectDescription(value: String) = update { copy(projectDescription = value) }
    fun updateTechnologiesSelected(value: Set<String>) = update { copy(technologiesSelected = value) }
    fun updateGithubRepo(value: String) = update { copy(githubRepo = value) }
    fun updateLiveDemo(value: String) = update { copy(liveDemo = value) }
    fun updateTeamSize(value: Int) = update { copy(teamSize = value) }
    fun updateTeamMembersJson(value: String) = update { copy(teamMembersJson = value) }
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

            val request = StudentProfileRequest(
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

    private fun update(transform: StudentDraft.() -> StudentDraft) {
        _draft.value = _draft.value.transform()
    }
}
