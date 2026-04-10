package com.example.placementprojectmp.ui.screens.shared.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.placementprojectmp.ui.components.SearchBar

@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = "Search opportunities...",
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    searchIconResId: Int = 0,
    filterIconResId: Int = 0
) {
    SearchBar(
        modifier = modifier,
        placeholder = placeholder,
        query = query,
        onQueryChange = onQueryChange,
        onFilterClick = onFilterClick,
        searchIconResId = searchIconResId,
        filterIconResId = filterIconResId
    )
}
