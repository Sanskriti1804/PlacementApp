package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Single selection field: label + tappable surface ("Select X" or selected count) + expandable TagSelectionCard below.
 * Card expands on tap; no collapse behavior (stays expanded).
 */
@Composable
fun SkillSelectionField(
    label: String,
    placeholder: String,
    options: List<String>,
    selected: Set<String>,
    expanded: Boolean,
    onExpandToggle: () -> Unit,
    onSelectionChange: (Set<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$label *",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(MaterialTheme.colorScheme.surface)
                .clickable(onClick = onExpandToggle)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            val displayText = when {
                selected.isEmpty() -> placeholder
                selected.size == 1 -> selected.first()
                else -> "${selected.size} selected"
            }
            Text(
                text = displayText,
                style = MaterialTheme.typography.bodyMedium,
                color = if (selected.isEmpty())
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
        TagSelectionCard(
            visible = expanded,
            tags = options,
            selectedTags = selected,
            onTagClick = { tag ->
                val newSet = if (tag in selected) selected - tag else selected + tag
                onSelectionChange(newSet)
            }
        )
    }
}
