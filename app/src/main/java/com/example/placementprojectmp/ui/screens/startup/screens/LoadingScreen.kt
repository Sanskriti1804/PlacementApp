package com.example.placementprojectmp.ui.screens.startup.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppLogo
import com.example.placementprojectmp.ui.components.GlowingProgressBar
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    isSignUp: Boolean,
    modifier: Modifier = Modifier,
    onNavigateToAbout: (() -> Unit)? = null
) {
    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        progress = 1f
    }
    LaunchedEffect(Unit) {
        delay(2500)
        if (onNavigateToAbout != null) {
            onNavigateToAbout()
        }
    }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1800),
        label = "progress"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo(size = 100.dp)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = if (isSignUp) {
                "Creating your account… Please wait."
            } else {
                "Preparing your experience…"
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Upgrading your interface…",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        GlowingProgressBar(progress = animatedProgress)
    }
}
