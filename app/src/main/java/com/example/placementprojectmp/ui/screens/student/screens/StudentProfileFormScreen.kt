package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.FormSection
import com.example.placementprojectmp.ui.components.ProfileFormHeader
import com.example.placementprojectmp.ui.components.ProfileFormTab
import com.example.placementprojectmp.ui.components.ProfileTabs

/**
 * Student Profile Form: multi-section tabbed form inside LazyColumn.
 * TopBar, header, divider, tabs, dynamic form content. Form content area blank for now.
 */
@Composable
fun StudentProfileFormScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(ProfileFormTab.Personal) }
    val sectionTitle = sectionTitleFor(selectedTab)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AppTopBar(
                title = stringResource(R.string.app_name),
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item {
            ProfileFormHeader(sectionTitle = sectionTitle)
        }
        item {
            ProfileTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )
        }
        item {
            FormSection(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun sectionTitleFor(tab: ProfileFormTab): String = when (tab) {
    ProfileFormTab.Personal -> "Personal Profile"
    ProfileFormTab.Education -> "Education Profile"
    ProfileFormTab.Skills -> "Skills Profile"
    ProfileFormTab.Experience -> "Experience Profile"
    ProfileFormTab.Projects -> "Projects Profile"
}
