package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Wrapping grid of tags. Uses FlowRow for multi-row wrap; consistent spacing.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagGrid(
    tags: List<String>,
    selectedTags: Set<String>,
    onTagClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            SkillTag(
                label = tag,
                selected = tag in selectedTags,
                onClick = { onTagClick(tag) }
            )
        }
    }
}
