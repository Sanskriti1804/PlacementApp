package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * "Connect Your Profile *" section: label + horizontal LazyRow of platform cards.
 */
@Composable
fun ConnectProfileRow(
    expandedPlatform: ProfilePlatform?,
    urlValues: Map<ProfilePlatform, String>,
    onUrlChange: (ProfilePlatform, String) -> Unit,
    onExpandToggle: (ProfilePlatform) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Connect Your Profile *",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ProfilePlatform.entries, key = { it }) { platform ->
                ProfilePlatformCard(
                    platform = platform,
                    expanded = expandedPlatform == platform,
                    urlValue = urlValues[platform] ?: "",
                    onUrlChange = { onUrlChange(platform, it) },
                    onExpandToggle = { onExpandToggle(platform) },
                    modifier = Modifier
                        .widthIn(min = 100.dp)
                        .animateContentSize()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}
