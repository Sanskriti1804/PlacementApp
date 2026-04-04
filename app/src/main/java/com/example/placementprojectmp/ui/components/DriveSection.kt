package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Section with divider, title "Drives", and horizontal LazyRow of DriveCards.
 */
@Composable
fun DriveSection(
    modifier: Modifier = Modifier,
    title: String = "Drives",
    drives: List<DriveItem> = emptyList()
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
        ) {
            items(drives.size) { index ->
                val drive = drives[index]
                DriveCard(
                    companyName = drive.companyName,
                    driveTiming = drive.driveTiming,
                    date = drive.date,
                    isCurrentDrive = index == 0
                )
            }
        }
    }
}

/** Dummy data model for a drive. */
data class DriveItem(
    val companyName: String,
    val driveTiming: String,
    val date: String
)
