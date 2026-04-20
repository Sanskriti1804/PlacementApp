package com.example.placementprojectmp.ui.screens.shared.cards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.viewmodel.JobUiModel
import com.example.placementprojectmp.viewmodel.Status
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val jobLastApplyTimeDummy = "6:00 PM"

/** Matches Drive card Register button treatment (same cyan family, softened). */
private val jobApplyButtonContainer = Color(0xFF00A3C4)

@Composable
fun JobCard(
    job: JobUiModel,
    onApplyClick: () -> Unit,
    onClick: () -> Unit,
    showApplyButton: Boolean = true
) {
    val scope = rememberCoroutineScope()
    var applyScaleTarget by remember { mutableFloatStateOf(1f) }
    val applyScale by animateFloatAsState(
        targetValue = applyScaleTarget,
        animationSpec = tween(durationMillis = 90),
        label = "job_apply_scale"
    )
    val lastDateFormatted = remember(job.lastDate) {
        job.lastDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }
    val workTypeLabel = job.workMode.label
    val employmentLabel = job.jobType.label
    val ctcLabel = remember(job.salaryLpa) { formatJobSalaryLpa(job.salaryLpa) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(jobCardStatusColor(job.status))
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
            Spacer(modifier = Modifier.height(22.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (showApplyButton) {
                    Arrangement.SpaceBetween
                } else {
                    Arrangement.Start
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Last date to apply",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$lastDateFormatted · $jobLastApplyTimeDummy",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (showApplyButton) {
                    Button(
                        onClick = {
                            scope.launch {
                                applyScaleTarget = 1.05f
                                delay(95)
                                applyScaleTarget = 1f
                                delay(95)
                                onApplyClick()
                            }
                        },
                        modifier = Modifier.graphicsLayer {
                            scaleX = applyScale
                            scaleY = applyScale
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = jobApplyButtonContainer,
                            contentColor = Color.White
                        ),
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Text(text = "Apply")
                    }
                }
            }
        }
    }
}

private fun jobCardStatusColor(status: Status): Color = when (status) {
    Status.OPEN -> Color(0xFF2E7D32)
    Status.UPCOMING -> Color(0xFFFFC107)
    Status.CLOSED -> Color(0xFF1976D2)
}

private fun formatJobSalaryLpa(lpa: Float): String {
    val s = if (lpa == lpa.toLong().toFloat()) {
        lpa.toLong().toString()
    } else {
        String.format(Locale.US, "%.1f", lpa)
    }
    return "$s LPA"
}
