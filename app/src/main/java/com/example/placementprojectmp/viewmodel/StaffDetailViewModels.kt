package com.example.placementprojectmp.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.placementprojectmp.ui.theme.colormap.ApplicationStatus

@Immutable
data class CompanyDetailUiState(
    val isLoading: Boolean = false,
    val data: List<String> = emptyList(),
    val error: String? = null
)

@Immutable
data class JobDetailUiState(
    val isLoading: Boolean = false,
    val data: List<String> = emptyList(),
    val error: String? = null
)

@Immutable
data class CandidateItemUi(
    val name: String,
    val roll: String,
    val status: ApplicationStatus
)

@Immutable
data class CandidateDetailUiState(
    val isLoading: Boolean = false,
    val data: List<CandidateItemUi> = emptyList(),
    val error: String? = null
)

class StaffCompanyDetailViewModel : ViewModel() {
    private val _uiState = mutableStateOf(
        CompanyDetailUiState(data = listOf("Drives Conducted: 12", "Students Placed: 48"))
    )
    val uiState: State<CompanyDetailUiState> = _uiState
}

class StaffJobDetailViewModel : ViewModel() {
    private val _uiState = mutableStateOf(
        JobDetailUiState(
            data = listOf(
                "Job Type: Full-time",
                "Department: Tech",
                "CTC: 12 LPA",
                "Rounds: 3"
            )
        )
    )
    val uiState: State<JobDetailUiState> = _uiState
}

class StaffCandidateDetailViewModel : ViewModel() {
    private val _uiState = mutableStateOf(
        CandidateDetailUiState(
            data = listOf(
                CandidateItemUi("Aarav Singh", "CSE-2201", ApplicationStatus.APPLIED),
                CandidateItemUi("Meera Nair", "IT-2212", ApplicationStatus.SHORTLISTED),
                CandidateItemUi("Rohan Das", "ECE-2122", ApplicationStatus.INTERVIEW_SCHEDULED),
                CandidateItemUi("Kavya Patel", "ME-2018", ApplicationStatus.SELECTED),
                CandidateItemUi("Ishaan Rao", "CE-2119", ApplicationStatus.REJECTED)
            )
        )
    )
    val uiState: State<CandidateDetailUiState> = _uiState
}
