package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.ApplicationStatusScreenItem
import com.example.placementprojectmp.ui.components.ApplicationStatusStage
import com.example.placementprojectmp.ui.screens.shared.cards.ApplicationStatusCard
import com.example.placementprojectmp.ui.screens.shared.component.AppScreenHeader
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar

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
    val submittedApplications by StudentApplicationSubmissionStore.submittedApplications.collectAsState()
    val applications = dummyApplications + submittedApplications.map { submitted ->
        ApplicationStatusScreenItem(
            companyName = submitted.companyName,
            location = submitted.location,
            role = submitted.role,
            appliedDate = submitted.appliedDate,
            currentStage = ApplicationStatusStage.Applied
        )
    }
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
