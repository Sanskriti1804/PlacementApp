package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Status dot for profile form tabs.
 * Filled = section completed; Outlined = section not yet filled.
 */
@Composable
fun CompletionDot(
    completed: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 8.dp,
    borderWidth: Dp = 1.5.dp
) {
    val color by animateColorAsState(
        targetValue = if (completed) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outline,
        label = "completion_dot_color"
    )
    val scale by animateFloatAsState(
        targetValue = if (completed) 1f else 0.95f,
        label = "completion_dot_scale"
    )

    if (completed) {
        Box(
            modifier = modifier
                .size(size)
                .scale(scale)
                .background(color, CircleShape)
        )
    } else {
        Box(
            modifier = modifier
                .size(size)
                .scale(scale)
                .border(
                    width = borderWidth,
                    color = color,
                    shape = CircleShape
                )
        )
    }
}
