package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * Floating reminder card: interview title, date, "Notify me on interview day",
 * and a swipe-to-enable control. On swipe right, shows "You will be notified on interview day" with confirmation.
 */
@Composable
fun ReminderCard(
    modifier: Modifier = Modifier,
    companyName: String,
    interviewDate: String,
    onDismiss: () -> Unit = {}
) {
    var swipeProgress by remember { mutableFloatStateOf(0f) }
    var notificationEnabled by remember { mutableStateOf(false) }

    val progress by animateFloatAsState(
        targetValue = swipeProgress,
        animationSpec = tween(200),
        label = "swipe_progress"
    )
    val scale by animateFloatAsState(
        targetValue = if (notificationEnabled) 1.02f else 1f,
        animationSpec = tween(300),
        label = "confirm_scale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
            .scale(scale),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "$companyName Interview",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = interviewDate,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (notificationEnabled) {
            Text(
                text = "You will be notified on interview day",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Text(
                text = "Notify me on interview day",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            SwipeToNotifyControl(
                progress = progress,
                onProgressChange = { delta ->
                    swipeProgress = (swipeProgress + delta).coerceIn(0f, 1f)
                    if (swipeProgress >= 0.95f) notificationEnabled = true
                }
            )
        }
    }
}

@Composable
private fun SwipeToNotifyControl(
    progress: Float,
    onProgressChange: (Float) -> Unit
) {
    val density = LocalDensity.current
    val trackColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f + progress * 0.5f),
        label = "track"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(4.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    with(density) {
                        val delta = (dragAmount / 200.dp.toPx()).coerceIn(-0.2f, 0.2f)
                        onProgressChange(delta)
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(8.dp))
                .background(trackColor)
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (progress < 0.95f) "Swipe → Notify" else "Notified",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Swipe → Notify",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
