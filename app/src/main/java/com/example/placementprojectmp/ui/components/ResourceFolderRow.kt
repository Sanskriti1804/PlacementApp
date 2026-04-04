package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Horizontal LazyRow of glass-style resource folder cards.
 */
@Composable
fun ResourceFolderRow(
    modifier: Modifier = Modifier,
    folders: List<String> = listOf(
        "Notes",
        "PYQ",
        "Resources",
        "Cheat Codes",
        "Preparation Sheets"
    ),
    onFolderClick: (String) -> Unit = {}
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        items(folders.size) { index ->
            val folder = folders[index]
            ResourceFolderCard(
                modifier = Modifier.width(100.dp),
                label = folder,
                onClick = { onFolderClick(folder) }
            )
        }
    }
}
