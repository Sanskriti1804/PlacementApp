package com.example.placementprojectmp.ui.screens.shared.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.placementprojectmp.ui.components.ApplicationStatusScreenCard
import com.example.placementprojectmp.ui.components.ApplicationStatusScreenItem

@Composable
fun ApplicationStatusCard(
    modifier: Modifier = Modifier,
    item: ApplicationStatusScreenItem,
    onCompanyClick: ((ApplicationStatusScreenItem) -> Unit)? = null
) {
    ApplicationStatusScreenCard(
        modifier = modifier,
        item = item,
        onCompanyClick = onCompanyClick
    )
}
