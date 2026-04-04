package com.example.placementprojectmp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * Circular timer: ring drains from accent to gray as time decreases. Center shows MM:SS.
 */
@Composable
fun TimerRing(
    modifier: Modifier = Modifier,
    totalSeconds: Long,
    remainingSeconds: Long,
    label: String = ""
) {
    val progress by animateFloatAsState(
        targetValue = (remainingSeconds.toFloat() / totalSeconds.coerceAtLeast(1)).coerceIn(0f, 1f),
        label = "timer_ring"
    )
    val minutes = (remainingSeconds / 60).toInt()
    val secs = (remainingSeconds % 60).toInt()
    val timeText = "%d:%02d".format(minutes, secs)
    val outlineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(100.dp)) {
            val strokeWidth = 8.dp.toPx()
            val radius = (size.minDimension / 2) - strokeWidth / 2
            val center = Offset(size.width / 2, size.height / 2)
            drawCircle(
                color = outlineColor,
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = primaryColor,
                startAngle = 270f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                size = Size(size.width - strokeWidth, size.height - strokeWidth),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
        Text(
            text = timeText,
            style = MaterialTheme.typography.titleLarge,
            color = onSurfaceColor
        )
    }
}
