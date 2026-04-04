package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/** Platform for "Connect Your Profile": display name and whether it's optional. */
enum class ProfilePlatform(
    val label: String,
    val required: Boolean
) {
    GitHub("GitHub", true),
    LinkedIn("LinkedIn", true),
    Portfolio("Portfolio", false),
    LeetCode("LeetCode", true),
    Resume("Resume Upload", true)
}

/**
 * Small card: icon + platform name. On click expands to show URL input with smooth animation.
 * Icons will be provided later; placeholder used for now.
 */
@Composable
fun ProfilePlatformCard(
    platform: ProfilePlatform,
    expanded: Boolean,
    urlValue: String,
    onUrlChange: (String) -> Unit,
    onExpandToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .animateContentSize()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onExpandToggle),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = if (platform.required) "${platform.label} *" else platform.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically(),
                label = "platform_expand"
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    val urlLabel = when (platform) {
                        ProfilePlatform.Portfolio -> "Paste your Portfolio URL"
                        else -> "Paste your ${platform.label} URL"
                    }
                    FormField(
                        label = urlLabel,
                        value = urlValue,
                        onValueChange = onUrlChange,
                        required = platform.required,
                        placeholder = "https://..."
                    )
                }
            }
        }
    }
}
