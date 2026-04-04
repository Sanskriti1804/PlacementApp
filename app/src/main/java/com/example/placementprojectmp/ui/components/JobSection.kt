package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Section with title "Jobs Based on Your Preference" and a list of JobCards.
 */
@Composable
fun JobSection(
    modifier: Modifier = Modifier,
    title: String = "Jobs Based on Your Preference",
    jobs: List<JobItem>,
    onDismissJob: (JobItem) -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            jobs.forEach { job ->
                JobCard(
                    companyName = job.companyName,
                    roleTitle = job.roleTitle,
                    timeAgo = job.timeAgo,
                    logoIconResId = job.logoIconResId,
                    onDismiss = { onDismissJob(job) }
                )
            }
        }
    }
}

/** Dummy data model for a job listing. */
data class JobItem(
    val id: String,
    val companyName: String,
    val roleTitle: String,
    val timeAgo: String,
    val logoIconResId: Int = 0
)
