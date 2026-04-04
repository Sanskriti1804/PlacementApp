package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Status stages for application progress. Order matches pipeline.
 */
enum class ApplicationStatusStage(val label: String, val color: Color) {
    Applied("Applied", Color(0xFF2196F3)),
    ApplicationReviewed("Application Reviewed", Color(0xFFB24BF3)),
    Shortlisted("Shortlisted", Color(0xFFFF9800)),
    InterviewScheduled("Interview Scheduled", Color(0xFF009688)),
    Offer("Offer", Color(0xFF4CAF50)),
    Hired("Hired", Color(0xFF2E7D32))
}

/**
 * Row of 6 circular status indicators. Completed = filled, current = accent, upcoming = outlined.
 */
@Composable
fun StatusIndicatorRow(
    modifier: Modifier = Modifier,
    currentStage: ApplicationStatusStage,
    statusLabel: String = currentStage.label
) {
    val stages = ApplicationStatusStage.entries
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        stages.forEach { stage ->
            val index = stages.indexOf(stage)
            val currentIndex = stages.indexOf(currentStage)
            val isCompleted = index < currentIndex
            val isCurrent = index == currentIndex
            StatusDot(
                color = stage.color,
                filled = isCompleted || isCurrent,
                highlighted = isCurrent
            )
        }
        Text(
            text = statusLabel,
            style = MaterialTheme.typography.labelMedium,
            color = currentStage.color,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun StatusDot(
    color: Color,
    filled: Boolean,
    highlighted: Boolean
) {
    val targetColor by animateColorAsState(
        targetValue = if (filled) color else Color.Transparent,
        label = "dot_color"
    )
    val borderColor by animateColorAsState(
        targetValue = when {
            highlighted -> color
            filled -> color
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
        },
        label = "border_color"
    )
    val borderWidth = if (highlighted) 2.dp else 1.dp
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .then(
                if (filled) Modifier.background(targetColor)
                else Modifier.border(borderWidth, borderColor, CircleShape)
            )
    )
}
