package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Drive card: Company name, timing, date.
 * Use isCurrentDrive = true for accent background, false for secondary/light.
 */
@Composable
fun DriveCard(
    modifier: Modifier = Modifier,
    companyName: String,
    driveTiming: String,
    date: String,
    isCurrentDrive: Boolean = false
) {
    Column(
        modifier = modifier
            .width(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isCurrentDrive)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant
            )
            .padding(16.dp)
    ) {
        Text(
            text = companyName,
            style = MaterialTheme.typography.titleMedium,
            color = if (isCurrentDrive)
                MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = driveTiming,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isCurrentDrive)
                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f)
        )
        Text(
            text = date,
            style = MaterialTheme.typography.labelMedium,
            color = if (isCurrentDrive)
                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
    }
}
