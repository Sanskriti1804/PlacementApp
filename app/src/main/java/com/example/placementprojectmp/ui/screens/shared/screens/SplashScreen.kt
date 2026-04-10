package com.example.placementprojectmp.ui.screens.shared.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppLogo
import androidx.compose.material3.MaterialTheme

private const val SPLASH_DELAY_MS = 2000L

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToAbout: (() -> Unit)? = null
) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(SPLASH_DELAY_MS)
        onNavigateToAbout?.invoke()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLogo(size = 140.dp)
        }
    }
}
