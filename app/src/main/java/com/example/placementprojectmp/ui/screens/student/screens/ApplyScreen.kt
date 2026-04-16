package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.cards.DriveStyleCapsuleChip
import com.example.placementprojectmp.ui.screens.shared.cards.LogoImage
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.viewmodel.JobUiModel
import java.util.Locale

/** Matches [com.example.placementprojectmp.ui.screens.shared.screens.ApplicationScreen]: AppTopBar row + 12.dp spacer below it. */
private val ApplicationScreenTopInset = 80.dp

private val SubmitSectionExtraBottomPadding = 40.dp

/**
 * Apply flow: short guidance, embedded [ApplicationScreen] (unchanged), and submit CTA.
 */
@Composable
fun ApplyScreen(
    modifier: Modifier = Modifier,
    selectedJobId: String = "",
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigateToProfileForm: () -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {
    val selectedJob = remember(selectedJobId) {
        studentOpportunitiesDummyJobs().firstOrNull { it.id == selectedJobId }
    }
    val applicationSectionHeight = LocalConfiguration.current.screenHeightDp.dp
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp + SubmitSectionExtraBottomPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AppTopBar(
                title = "Opportunities",
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        if (selectedJob != null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    ApplyJobCardContentPreview(job = selectedJob)
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                ApplyBulletLine(text = "Your application is pre-filled from your profile")
                ApplyBulletLine(text = "You can review or edit before submitting")
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToProfileForm)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit profile",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(applicationSectionHeight + ApplicationScreenTopInset)
                    .clip(RectangleShape)
            ) {
                ApplicationScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = -ApplicationScreenTopInset)
                )
            }
        }
        item {
            Button(
                onClick = onSubmitClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = SubmitSectionExtraBottomPadding),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Submit")
            }
        }
    }
}

/**
 * Subset of [com.example.placementprojectmp.ui.screens.shared.cards.JobCard] layout only (company block + chips row).
 * Does not modify the shared JobCard composable.
 */
@Composable
private fun ApplyJobCardContentPreview(job: JobUiModel) {
    val workTypeLabel = job.workMode.label
    val employmentLabel = job.jobType.label
    val ctcLabel = remember(job.salaryLpa) { applyFormatJobSalaryLpa(job.salaryLpa) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LogoImage(logoResId = job.companyLogoResId, containerSize = 46.dp)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = job.companyName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = job.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = job.jobRole,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DriveStyleCapsuleChip(label = workTypeLabel)
                    DriveStyleCapsuleChip(label = employmentLabel)
                    DriveStyleCapsuleChip(label = ctcLabel)
                }
            }
        }
    }
}

private fun applyFormatJobSalaryLpa(lpa: Float): String {
    val s = if (lpa == lpa.toLong().toFloat()) {
        lpa.toLong().toString()
    } else {
        String.format(Locale.US, "%.1f", lpa)
    }
    return "$s LPA"
}

@Composable
private fun ApplyBulletLine(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "\u2022",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
