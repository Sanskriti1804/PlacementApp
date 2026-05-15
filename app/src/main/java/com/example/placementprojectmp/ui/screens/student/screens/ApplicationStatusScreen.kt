package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.data.mapper.PlacementUiMappers
import com.example.placementprojectmp.ui.components.ApplicationStatusScreenItem
import com.example.placementprojectmp.ui.components.ApplicationStatusStage
import com.example.placementprojectmp.ui.screens.shared.cards.ApplicationStatusCard
import com.example.placementprojectmp.ui.screens.shared.component.AppScreenHeader
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.viewmodel.BackendApplicationsViewModel
import com.example.placementprojectmp.viewmodel.BackendDirectoryViewModel
import com.example.placementprojectmp.viewmodel.JobBrowseViewModel
import org.koin.androidx.compose.koinViewModel

private val dummyApplications = listOf(
    ApplicationStatusScreenItem(
        companyName = "Google",
        location = "Bangalore",
        role = "Android Developer",
        appliedDate = "12 Feb 2026",
        currentStage = ApplicationStatusStage.Shortlisted
    ),
    ApplicationStatusScreenItem(
        companyName = "Amazon",
        location = "Hyderabad",
        role = "Backend Developer",
        appliedDate = "18 Feb 2026",
        currentStage = ApplicationStatusStage.InterviewScheduled,
        interviewDate = "15 March 2026",
        interviewMode = "Offline"
    ),
    ApplicationStatusScreenItem(
        companyName = "Microsoft",
        location = "Remote",
        role = "Software Engineer Intern",
        appliedDate = "1 Feb 2026",
        currentStage = ApplicationStatusStage.ApplicationReviewed
    )
)

/**
 * Application Status screen: TopBar, header (title + description), LazyColumn of status cards.
 * Tapping company name/logo on a card navigates to ApplicationScreen via onApplicationClick.
 */
@Composable
fun ApplicationStatusScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onApplicationClick: (ApplicationStatusScreenItem) -> Unit = {}
) {
    val appsVm = koinViewModel<BackendApplicationsViewModel>()
    val dirVm = koinViewModel<BackendDirectoryViewModel>()
    val jobBrowseVm = koinViewModel<JobBrowseViewModel>()
    val appsState by appsVm.state.collectAsState()
    val dirState by dirVm.state.collectAsState()
    val jobBrowseState by jobBrowseVm.state.collectAsState()

    LaunchedEffect(Unit) {
        appsVm.refresh()
        dirVm.refreshUsers()
        jobBrowseVm.refresh()
    }

    val apiStatusItems = remember(appsState.applications, dirState.companies, jobBrowseState.jobs) {
        val companyById = dirState.companies.associateBy { it.id }
        val jobById = jobBrowseState.jobs.associateBy { it.id }
        appsState.applications.map { app ->
            val company = app.companyId?.let { companyById[it] }
            val companyName = company?.name.orEmpty().ifBlank { "Company #${app.companyId}" }
            val location = company?.location.orEmpty().ifBlank { "—" }
            val job = app.jobId?.let { jobById[it] }
            val role = job?.jobDescription?.lineSequence()?.firstOrNull { it.isNotBlank() }?.take(50)
                ?: job?.jobType?.replace('_', ' ') ?: "Job #${app.jobId}"
            PlacementUiMappers.applicationToStatusScreenItem(app, companyName, location, role)
        }
    }

    val submittedApplications by StudentApplicationSubmissionStore.submittedApplications.collectAsState()
    val submittedAsStatus = submittedApplications.map { submitted ->
        ApplicationStatusScreenItem(
            companyName = submitted.companyName,
            location = submitted.location,
            role = submitted.role,
            appliedDate = submitted.appliedDate,
            currentStage = ApplicationStatusStage.Applied
        )
    }
    val applications =
        (if (apiStatusItems.isNotEmpty()) apiStatusItems else dummyApplications) + submittedAsStatus

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item {
            AppScreenHeader(
                title = "Application Status",
                subtitle = "Track the progress of your job and internship applications."
            )
        }
        items(applications) { item ->
            ApplicationStatusCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                item = item,
                onCompanyClick = onApplicationClick
            )
        }
    }
}
