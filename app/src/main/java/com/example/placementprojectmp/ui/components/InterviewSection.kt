package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Interview info block: date, mode (e.g. Offline with icon). Top-right reminder icon opens ReminderCard.
 */
@Composable
fun InterviewSection(
    modifier: Modifier = Modifier,
    interviewDate: String = "15 March 2026",
    mode: String = "Offline",
    companyName: String = "Google",
    onReminderClick: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            modifier = Modifier.padding(vertical = 12.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 48.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Interview",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = interviewDate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Mode: $mode",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(
                onClick = onReminderClick,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Set reminder",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
