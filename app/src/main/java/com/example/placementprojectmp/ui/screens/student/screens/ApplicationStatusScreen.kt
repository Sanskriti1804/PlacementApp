package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.ApplicationStatusScreenCard
import com.example.placementprojectmp.ui.components.ApplicationStatusScreenItem
import com.example.placementprojectmp.ui.components.ApplicationStatusStage
import com.example.placementprojectmp.ui.components.AppTopBar

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
 */
@Composable
fun ApplicationStatusScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Application Status",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Track the progress of your job and internship applications.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
        }
        items(dummyApplications) { item ->
            ApplicationStatusScreenCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                item = item
            )
        }
    }
}
