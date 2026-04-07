package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.viewmodel.UserViewModel
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.ProfileCompletionCard
import com.example.placementprojectmp.ui.components.ProfileCompletionItem
import com.example.placementprojectmp.ui.components.ProfileHeader
import com.example.placementprojectmp.ui.components.RecentWorkCard
import com.example.placementprojectmp.ui.components.SkillsCard
import com.example.placementprojectmp.ui.components.SocialPlatform
import com.example.placementprojectmp.ui.components.SocialPlatformRow
import com.example.placementprojectmp.viewmodel.StudentViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.emptyList

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
    onIntroCardClick: (() -> Unit)? = null,
    onNavigateToAcademic: () -> Unit = {}
) {

    val userViewModel : UserViewModel = koinViewModel()
    val studentViewModel : StudentViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        println("FETCH CALLED")
        userViewModel.fetchUsers()
        studentViewModel.fetchStudentProfile(3)
    }
    val currentUser = userViewModel.students.firstOrNull()
    val currentStudentProfile  = studentViewModel.profile
    val profileUser = currentStudentProfile?.user

    val resolvedUserName = profileUser?.name?.takeIf { it.isNotBlank() }
        ?: currentUser?.name?.takeIf { it.isNotBlank() }
        ?: "User"
    val resolvedRole = profileUser?.roles?.firstOrNull()?.roleName?.takeIf { it.isNotBlank() }
        ?: currentUser?.role?.takeIf { it.isNotBlank() }
        ?: "Student"
    val resolvedHandle = currentUser?.username
        ?.takeIf { it.isNotBlank() }
        ?.let { if (it.startsWith("@")) it else "@$it" }
        ?: "@user"

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
//        item {
//            Column(modifier = Modifier.padding(20.dp)) {
//                when {
//                    userViewModel.isLoading || studentViewModel.isLoading ->
//                        Text(text = "Loading profile...")
//                    userViewModel.errorMessage != null ->
//                        Text(text = userViewModel.errorMessage!!)
//                    userViewModel.students.isEmpty() ->
//                        Text(text = "No data found OR API failed")
//                    else -> {
//                        userViewModel.students.forEach { student ->
//                            Text(text = student.name)
//                        }
//                    }
//                }
//                if (studentViewModel.errorMessage != null) {
//                    Text(text = studentViewModel.errorMessage!!)
//                }
//                currentStudentProfile?.bio?.takeIf { it.isNotBlank() }?.let { bio ->
//                    Text(text = bio)
//                }
//            }
//        }
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Bio",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = currentStudentProfile?.bio?.takeIf { it.isNotBlank() }
                            ?: "Add a short bio to tell others about your background and goals.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                if (!studentViewModel.isLoading && currentStudentProfile == null) {
                    Text(
                        text = "Profile details are unavailable right now.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                OutlinedButton(
                    onClick = onNavigateToAcademic,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Academic Performance")
                }
            }
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
            val platforms = currentStudentProfile?.links?.map {
                SocialPlatform(name = it.name)
            } ?: emptyList()

            if (platforms.isNotEmpty()) {
                SocialPlatformRow(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    platforms = platforms,
                    onPlatformClick = {}
                )
            }
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
