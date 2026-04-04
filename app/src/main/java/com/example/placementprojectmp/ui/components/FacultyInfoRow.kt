package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Single faculty row: Name • email
 * Dot separator between name and email.
 */
@Composable
fun FacultyInfoRow(
    modifier: Modifier = Modifier,
    name: String,
    email: String
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$name • $email",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
