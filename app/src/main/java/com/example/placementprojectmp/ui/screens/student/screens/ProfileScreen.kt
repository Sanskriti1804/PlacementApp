package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.viewmodel.UserViewModel
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.ProfileCompletionCard
import com.example.placementprojectmp.ui.components.ProfileCompletionItem
import com.example.placementprojectmp.ui.components.ProfileHeader
import com.example.placementprojectmp.ui.components.RecentWorkCard
import com.example.placementprojectmp.ui.components.SkillsCard
import com.example.placementprojectmp.ui.components.SocialPlatformRow
import com.example.placementprojectmp.viewmodel.StudentViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Profile screen: TopBar, Profile Header, Completion Card, Social Platforms, Recent Work + Skills.
 * Tapping "Complete your profile" opens StudentProfileFormScreen; tapping intro card opens PersonalInformationFormScreen.
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onCompleteProfileClick: (() -> Unit)? = null,
    onIntroCardClick: (() -> Unit)? = null
) {

    val userViewModel : UserViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        println("FETCH CALLED")
        userViewModel.fetchUsers()
    }
    val currentUser = userViewModel.students.firstOrNull()

    val studentViewModel : StudentViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        println("STUDENT FETCH CALLED")
        studentViewModel.fetchStudentProfile(3)
    }
    val currentStudentProfile  = studentViewModel.profile

    val resolvedUserName = currentUser?.name ?: "Sanskriti"
    val resolvedRole = currentUser?.role ?: "Android Developer" 
    val resolvedHandle = currentUser?.username
        ?.takeIf { it.isNotBlank() }
        ?.let { if (it.startsWith("@")) it else "@$it" }
        ?: "@sunsdev"
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(20.dp)) {
                when {
                    userViewModel.isLoading || studentViewModel.isLoading ->
                        Text(text = "Loading profile...")
                    userViewModel.errorMessage != null ->
                        Text(text = userViewModel.errorMessage!!)
                    userViewModel.students.isEmpty() ->
                        Text(text = "No data found OR API failed")
                    else -> {
                        userViewModel.students.forEach { student ->
                            Text(text = student.name)
                        }
                    }
                }
                if (studentViewModel.errorMessage != null) {
                    Text(text = studentViewModel.errorMessage!!)
                }
                currentStudentProfile?.bio?.takeIf { it.isNotBlank() }?.let { bio ->
                    Text(text = bio)
                }
            }
        }
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item {
            ProfileHeader(
                modifier = Modifier.padding(horizontal = 20.dp),
                userName = resolvedUserName,
                role = resolvedRole,
                handle = resolvedHandle,
                onClick = onIntroCardClick
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
                ),
                onTitleClick = onCompleteProfileClick
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
                    skills = currentStudentProfile?.skills?.map { it.name } ?: emptyList()
                )
            }
        }
    }
}
