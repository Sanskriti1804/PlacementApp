package com.example.placementprojectmp.integration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.integration.data.dto.BacklogDto
import com.example.placementprojectmp.integration.data.dto.EducationRequestDto
import com.example.placementprojectmp.integration.data.dto.EducationResponseDto
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.repository.EducationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EducationFormState(
    val university: String = "",
    val branch: String = "",
    val course: String = "",
    val domain: String = "",
    val currentYear: String = "",
    val tenthPercentage: String = "",
    val twelfthPercentage: String = "",
    val currentCgpa: String = "",
    val gapYears: String = "",
    val gapReason: String = "",
    val tenthSchoolName: String = "",
    val twelfthSchoolName: String = "",
    val graduationCollegeName: String = "",
    val postGraduationCollegeName: String = "",
    val backlogs: List<BacklogDto> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val data: EducationResponseDto? = null
)

class EducationViewModel(
    private val repository: EducationRepository
) : ViewModel() {
    private val _ui = MutableStateFlow(EducationFormState())
    val ui: StateFlow<EducationFormState> = _ui.asStateFlow()

    fun patch(update: EducationFormState.() -> EducationFormState) {
        _ui.value = _ui.value.update()
    }

    fun addBacklog(subject: String, semester: Int) {
        if (subject.isBlank()) return
        _ui.value = _ui.value.copy(backlogs = _ui.value.backlogs + BacklogDto(subject = subject, semester = semester))
    }

    fun removeBacklog(index: Int) {
        _ui.value = _ui.value.copy(backlogs = _ui.value.backlogs.filterIndexed { i, _ -> i != index })
    }

    fun submit(studentId: Long) {
        val s = _ui.value
        _ui.value = s.copy(loading = true, error = null, success = false)
        viewModelScope.launch {
            val request = EducationRequestDto(
                university = s.university.nullIfBlank(),
                branch = s.branch.nullIfBlank(),
                course = s.course.nullIfBlank(),
                domain = s.domain.nullIfBlank(),
                currentYear = s.currentYear.toIntOrNull(),
                tenthPercentage = s.tenthPercentage.toDoubleOrNull(),
                twelfthPercentage = s.twelfthPercentage.toDoubleOrNull(),
                currentCgpa = s.currentCgpa.toDoubleOrNull(),
                gapYears = s.gapYears.toIntOrNull(),
                gapReason = s.gapReason.nullIfBlank(),
                tenthSchoolName = s.tenthSchoolName.nullIfBlank(),
                twelfthSchoolName = s.twelfthSchoolName.nullIfBlank(),
                graduationCollegeName = s.graduationCollegeName.nullIfBlank(),
                postGraduationCollegeName = s.postGraduationCollegeName.nullIfBlank(),
                backlogs = s.backlogs
            )
            when (val result = repository.createOrUpdateEducation(studentId, request)) {
                is ApiResult.Success -> _ui.value = _ui.value.copy(loading = false, success = true, data = result.data)
                is ApiResult.Error -> _ui.value = _ui.value.copy(loading = false, error = result.message)
            }
        }
    }

    fun load(studentId: Long) {
        _ui.value = _ui.value.copy(loading = true, error = null)
        viewModelScope.launch {
            when (val result = repository.getEducation(studentId)) {
                is ApiResult.Success -> {
                    val d = result.data
                    _ui.value = _ui.value.copy(
                        loading = false,
                        data = d,
                        university = d.university.orEmpty(),
                        branch = d.branch.orEmpty(),
                        course = d.course.orEmpty(),
                        domain = d.domain.orEmpty(),
                        currentYear = d.currentYear?.toString().orEmpty(),
                        tenthPercentage = d.tenthPercentage?.toString().orEmpty(),
                        twelfthPercentage = d.twelfthPercentage?.toString().orEmpty(),
                        currentCgpa = d.currentCgpa?.toString().orEmpty(),
                        gapYears = d.gapYears?.toString().orEmpty(),
                        gapReason = d.gapReason.orEmpty(),
                        tenthSchoolName = d.tenthSchoolName.orEmpty(),
                        twelfthSchoolName = d.twelfthSchoolName.orEmpty(),
                        graduationCollegeName = d.graduationCollegeName.orEmpty(),
                        postGraduationCollegeName = d.postGraduationCollegeName.orEmpty(),
                        backlogs = d.backlogs
                    )
                }

                is ApiResult.Error -> _ui.value = _ui.value.copy(loading = false, error = result.message)
            }
        }
    }
}

private fun String.nullIfBlank(): String? = trim().ifBlank { null }

