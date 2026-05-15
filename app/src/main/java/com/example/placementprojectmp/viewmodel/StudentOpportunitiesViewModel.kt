package com.example.placementprojectmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.local.OpportunitiesCatalogHolder
import com.example.placementprojectmp.data.mapper.PlacementUiMappers
import com.example.placementprojectmp.data.repo.DriveRepository
import com.example.placementprojectmp.data.repo.JobRepository
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.data.local.StudentOpportunitiesFallbackData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StudentOpportunitiesUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val jobs: List<JobUiModel> = StudentOpportunitiesFallbackData.jobs,
    val drives: List<DriveUiModel> = StudentOpportunitiesFallbackData.drives,
    val usedApiJobs: Boolean = false,
    val usedApiDrives: Boolean = false
)

class StudentOpportunitiesViewModel(
    private val jobsRepo: JobRepository,
    private val drivesRepo: DriveRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StudentOpportunitiesUiState())
    val state: StateFlow<StudentOpportunitiesUiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            val fallbackJobs = StudentOpportunitiesFallbackData.jobs
            val fallbackDrives = StudentOpportunitiesFallbackData.drives

            val jobsResult = jobsRepo.fetchList()
            val mappedJobs = when (jobsResult) {
                is ApiResult.Success ->
                    jobsResult.data
                        .map { PlacementUiMappers.jobToUiModel(it) }
                        .takeIf { it.isNotEmpty() }
                else -> null
            }
            val drivesResult = drivesRepo.fetchList()
            val mappedDrives = when (drivesResult) {
                is ApiResult.Success ->
                    drivesResult.data
                        .map { PlacementUiMappers.driveToUiModel(it) }
                        .takeIf { it.isNotEmpty() }
                else -> null
            }

            val err = listOfNotNull(
                (jobsResult as? ApiResult.Error)?.message,
                (drivesResult as? ApiResult.Error)?.message
            ).joinToString(" · ").ifBlank { null }

            val finalJobs = mappedJobs ?: fallbackJobs
            val finalDrives = mappedDrives ?: fallbackDrives
            OpportunitiesCatalogHolder.update(finalJobs, finalDrives)

            _state.update {
                it.copy(
                    loading = false,
                    error = err,
                    jobs = finalJobs,
                    drives = finalDrives,
                    usedApiJobs = mappedJobs != null,
                    usedApiDrives = mappedDrives != null
                )
            }
        }
    }
}
