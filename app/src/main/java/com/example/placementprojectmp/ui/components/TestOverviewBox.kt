package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        OverviewRow("Total Questions", "$totalQuestions")
        OverviewRow("Time Limit", "$timeMinutes Minutes")
        OverviewRow("Sections", sections.joinToString(", "))
    }
}

private val overviewLabelColumnWidth = 172.dp

@Composable
private fun OverviewRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(overviewLabelColumnWidth)
        )
        Text(
            text = ":",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}
