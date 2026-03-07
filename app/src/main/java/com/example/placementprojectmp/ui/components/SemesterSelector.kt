package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Horizontal scrollable semester selector (All, Semester 1–4).
 * Clear selection highlight; smooth interaction.
 */
@Composable
fun SemesterSelector(
    modifier: Modifier = Modifier,
    options: List<String> = listOf("All", "Semester 1", "Semester 2", "Semester 3", "Semester 4"),
    selectedIndex: Int = 0,
    onSelect: (Int) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        items(options.size) { i ->
            val selected = i == selectedIndex
            val backgroundColor by animateColorAsState(
                targetValue = if (selected)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant,
                animationSpec = tween(200),
                label = "semester_bg"
            )
            val textColor by animateColorAsState(
                targetValue = if (selected)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(200),
                label = "semester_text"
            )
            Text(
                text = options[i],
                style = MaterialTheme.typography.labelLarge,
                color = textColor,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(backgroundColor)
                    .then(
                        if (selected)
                            Modifier.border(
                                1.dp,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                RoundedCornerShape(20.dp)
                            )
                        else Modifier
                    )
                    .clickable { onSelect(i) }
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}
