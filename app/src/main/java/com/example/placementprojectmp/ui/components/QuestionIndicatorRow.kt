package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Row of question number indicators: unanswered (outline), answered (accent ring), current (filled).
 */
@Composable
fun QuestionIndicatorRow(
    modifier: Modifier = Modifier,
    totalQuestions: Int,
    currentIndex: Int,
    answeredIndices: Set<Int>,
    onQuestionClick: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalQuestions) { index ->
            val isCurrent = index == currentIndex
            val isAnswered = index in answeredIndices
            val borderColor by animateColorAsState(
                targetValue = when {
                    isCurrent -> MaterialTheme.colorScheme.primary
                    isAnswered -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                },
                label = "indicator"
            )
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .then(
                        if (isCurrent) Modifier.background(MaterialTheme.colorScheme.primary)
                        else Modifier.border(2.dp, borderColor, CircleShape)
                    )
                    .clickable { onQuestionClick(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${index + 1}",
                    style = MaterialTheme.typography.labelMedium,
                    color = when {
                        isCurrent -> MaterialTheme.colorScheme.onPrimary
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }
    }
}
