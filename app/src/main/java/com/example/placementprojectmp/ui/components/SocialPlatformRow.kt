package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Horizontal row of platform cards (Twitter, LinkedIn, Dribbble, GitHub).
 * Uses PlatformCard; pass icon drawable ids when available.
 */
@Composable
fun SocialPlatformRow(
    modifier: Modifier = Modifier,
    platforms: List<SocialPlatform> = listOf(
        SocialPlatform("Twitter", 0),
        SocialPlatform("LinkedIn", 0),
        SocialPlatform("Dribbble", 0),
        SocialPlatform("GitHub", 0)
    ),
    onPlatformClick: (String) -> Unit = {}
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(platforms.size) { index ->
            val p = platforms[index]
            PlatformCard(
                modifier = Modifier.width(80.dp),
                platformName = p.name,
                iconResId = p.iconResId,
                onClick = { onPlatformClick(p.name) }
            )
        }
    }
}

data class SocialPlatform(
    val name: String,
    val iconResId: Int = 0
)
