package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Outlined container for test overview: Total Questions, Time Limit, Sections.
 */
@Composable
fun TestOverviewBox(
    modifier: Modifier = Modifier,
    totalQuestions: Int,
    timeMinutes: Int,
    sections: List<String>
) {
    val outlineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, outlineColor, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Test Overview",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        OverviewRow("Total Questions", "$totalQuestions")
        OverviewRow("Time Limit", "$timeMinutes Minutes")
        OverviewRow("Sections", sections.joinToString(", "))
    }
}

@Composable
private fun OverviewRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label :",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
