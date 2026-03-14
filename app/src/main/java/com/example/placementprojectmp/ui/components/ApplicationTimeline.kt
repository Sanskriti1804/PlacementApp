package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
 * Vertical timeline trail for application stages. Used when card is expanded.
 */
@Composable
fun ApplicationTimeline(
    modifier: Modifier = Modifier,
    currentStage: ApplicationStatusStage
) {
    val stages = ApplicationStatusStage.entries
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        stages.forEachIndexed { index, stage ->
            val isCompleted = index < stages.indexOf(currentStage)
            val isCurrent = stage == currentStage
            TimelineRow(
                label = stage.label,
                color = stage.color,
                filled = isCompleted || isCurrent,
                highlighted = isCurrent,
                isLast = index == stages.lastIndex
            )
        }
    }
}

@Composable
private fun TimelineRow(
    label: String,
    color: Color,
    filled: Boolean,
    highlighted: Boolean,
    isLast: Boolean
) {
    val dotColor by animateColorAsState(
        targetValue = if (filled) color else Color.Transparent,
        label = "timeline_dot"
    )
    val borderColor by animateColorAsState(
        targetValue = when {
            highlighted -> color
            filled -> color
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
        },
        label = "timeline_border"
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .then(
                        if (filled) Modifier.background(dotColor)
                        else Modifier.border(
                            if (highlighted) 2.dp else 1.dp,
                            borderColor,
                            CircleShape
                        )
                    )
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(16.dp)
                        .background(
                            if (filled) color
                            else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        )
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (filled || highlighted) color else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(start = 12.dp, top = 0.dp, bottom = if (isLast) 0.dp else 8.dp)
        )
    }
}
