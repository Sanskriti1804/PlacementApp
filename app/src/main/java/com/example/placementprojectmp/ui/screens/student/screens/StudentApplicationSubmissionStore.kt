package com.example.placementprojectmp.ui.screens.student.screens

import com.example.placementprojectmp.data.local.OpportunitiesCatalogHolder
import com.example.placementprojectmp.data.local.StudentOpportunitiesFallbackData
import com.example.placementprojectmp.viewmodel.JobUiModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal data class SubmittedApplicationEntry(
    val companyName: String,
    val role: String,
    val location: String,
    val appliedDate: String,
    val status: String
)

internal object StudentApplicationSubmissionStore {
    private val dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
    private val _submittedApplications = MutableStateFlow<List<SubmittedApplicationEntry>>(emptyList())
    val submittedApplications: StateFlow<List<SubmittedApplicationEntry>> = _submittedApplications.asStateFlow()

    fun addAppliedJob(jobId: String, companyNameFallback: String = "") {
        val job = OpportunitiesCatalogHolder.jobs.firstOrNull { it.id == jobId }
            ?: StudentOpportunitiesFallbackData.jobs.firstOrNull { it.id == jobId }
        if (job != null) {
            addAppliedJob(job)
            return
        }
        val entry = SubmittedApplicationEntry(
            companyName = companyNameFallback.trim().ifBlank { "Company" },
            role = "—",
            location = "—",
            appliedDate = LocalDate.now().format(dateFormatter),
            status = "Applied"
        )
        _submittedApplications.value = listOf(entry) + _submittedApplications.value
    }

    private fun addAppliedJob(job: JobUiModel) {
        val entry = SubmittedApplicationEntry(
            companyName = job.companyName,
            role = job.jobRole,
            location = job.location,
            appliedDate = LocalDate.now().format(dateFormatter),
            status = "Applied"
        )
        _submittedApplications.value = listOf(entry) + _submittedApplications.value
    }
}
