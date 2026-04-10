package com.example.placementprojectmp.ui.screens.student.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
    com.example.placementprojectmp.ui.components.ResourceFolderRow(
        modifier = modifier,
        folders = folders,
        onFolderClick = onFolderClick
    )
}
