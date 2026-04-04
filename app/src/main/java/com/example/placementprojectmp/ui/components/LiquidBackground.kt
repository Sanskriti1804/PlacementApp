package com.example.placementprojectmp.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.placementprojectmp.ui.theme.BackgroundBlack
import com.example.placementprojectmp.ui.theme.GlowPurple
import com.example.placementprojectmp.ui.theme.NeonBlueDim
import com.example.placementprojectmp.ui.theme.NeonPurpleDim
import com.example.placementprojectmp.ui.theme.SurfaceDark

@Composable
fun LiquidBackground(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "liquid")
    val phase1 by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "phase1"
    )
    val phase2 by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "phase2"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        // Base black
        drawRect(BackgroundBlack)
        // Fluid gradient orbs (dark tones with subtle movement)
        val cx1 = w * (0.2f + 0.15f * phase1)
        val cy1 = h * (0.3f + 0.2f * phase2)
        val cx2 = w * (0.8f - 0.15f * phase2)
        val cy2 = h * (0.7f - 0.15f * phase1)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    NeonPurpleDim.copy(alpha = 0.35f),
                    GlowPurple.copy(alpha = 0.15f),
                    Color.Transparent
                ),
                center = Offset(cx1, cy1),
                radius = w * 0.6f
            ),
            center = Offset(cx1, cy1),
            radius = w * 0.6f
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    NeonBlueDim.copy(alpha = 0.25f),
                    SurfaceDark,
                    Color.Transparent
                ),
                center = Offset(cx2, cy2),
                radius = w * 0.5f
            ),
            center = Offset(cx2, cy2),
            radius = w * 0.5f
        )
    }
}
