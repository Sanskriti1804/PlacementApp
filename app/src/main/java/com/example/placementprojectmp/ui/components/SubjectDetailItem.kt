package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Single row for expanded subject details: label (e.g. "CI 1") and value (e.g. "18 / 20").
 */
@Composable
fun SubjectDetailItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.95f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}
