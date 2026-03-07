package com.example.placementprojectmp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.ProfileCompletionCard
import com.example.placementprojectmp.ui.components.ProfileCompletionItem
import com.example.placementprojectmp.ui.components.ProfileHeader
import com.example.placementprojectmp.ui.components.RecentWorkCard
import com.example.placementprojectmp.ui.components.SkillsCard
import com.example.placementprojectmp.ui.components.SocialPlatformRow

/**
 * Profile screen: TopBar, Profile Header, Completion Card, Social Platforms, Recent Work + Skills.
 * All sections in a LazyColumn; uses existing theme and AppTopBar.
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item {
            ProfileHeader(
                modifier = Modifier.padding(horizontal = 20.dp),
                userName = "Alex Johnson",
                role = "Android Developer",
                handle = "@alexdev"
            )
        }
        item {
            ProfileCompletionCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                title = "Complete Your Profile",
                completionPercent = 60,
                items = listOf(
                    ProfileCompletionItem("Profile Photo", true),
                    ProfileCompletionItem("Bio", true),
                    ProfileCompletionItem("Portfolio", false),
                    ProfileCompletionItem("Resume", false),
                    ProfileCompletionItem("Skills", true)
                )
            )
        }
        item {
            SocialPlatformRow(
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RecentWorkCard(
                    modifier = Modifier.weight(0.6f),
                    onNavigateClick = {}
                )
                SkillsCard(
                    modifier = Modifier.weight(0.4f),
                    skills = listOf("Kotlin", "Android", "UI/UX", "Firebase", "Compose")
                )
            }
        }
    }
}
