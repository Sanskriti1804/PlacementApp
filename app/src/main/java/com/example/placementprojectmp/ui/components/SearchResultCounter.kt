package com.example.placementprojectmp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Displays "Search Results (N)" or custom label with count.
 */
@Composable
fun SearchResultCounter(
    modifier: Modifier = Modifier,
    count: Int,
    label: String = "Search Results"
) {
    Text(
        text = "$label ($count)",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}
