package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Profile form tab type. */
enum class ProfileFormTab {
    Personal,
    Education,
    Skills,
    Experience,
    Projects
}

/**
 * Card-style tab container for profile form sections.
 * Row of selectable tabs with completion dots; dummy completion states for now.
 */
@Composable
fun ProfileTabs(
    selectedTab: ProfileFormTab,
    onTabSelected: (ProfileFormTab) -> Unit,
    modifier: Modifier = Modifier,
    completionState: Map<ProfileFormTab, Boolean> = defaultCompletionState()
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProfileFormTab.entries.forEach { tab ->
                TabItem(
                    label = tab.label(),
                    selected = tab == selectedTab,
                    completed = completionState[tab] ?: false,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

private fun ProfileFormTab.label(): String = when (this) {
    ProfileFormTab.Personal -> "Personal"
    ProfileFormTab.Education -> "Education"
    ProfileFormTab.Skills -> "Skills"
    ProfileFormTab.Experience -> "Experience"
    ProfileFormTab.Projects -> "Projects"
}

/** Dummy completion: Personal completed, rest not. */
private fun defaultCompletionState(): Map<ProfileFormTab, Boolean> = mapOf(
    ProfileFormTab.Personal to true,
    ProfileFormTab.Education to false,
    ProfileFormTab.Skills to false,
    ProfileFormTab.Experience to false,
    ProfileFormTab.Projects to false
)
