package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.viewmodel.UserViewModel
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.ui.screens.shared.component.PerformanceButton
import com.example.placementprojectmp.ui.components.ProfileCompletionCard
import com.example.placementprojectmp.ui.components.ProfileCompletionItem
import com.example.placementprojectmp.ui.components.ProfileHeader
import com.example.placementprojectmp.ui.components.RecentWorkCard
import com.example.placementprojectmp.ui.components.SkillsCard
import com.example.placementprojectmp.ui.components.SocialPlatform
import com.example.placementprojectmp.ui.components.SocialPlatformRow
import com.example.placementprojectmp.viewmodel.StudentViewModel
import com.example.placementprojectmp.viewmodel.StudentPersonalDraftViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlin.collections.emptyList
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.widget.Toast
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
    onNavigateToAcademic: () -> Unit = {},
    onNavigateToApplication: () -> Unit = {},
    onNavigateToResume: () -> Unit = {}
) {

    val userViewModel : UserViewModel = koinViewModel()
    val studentViewModel : StudentViewModel = koinViewModel()
    val personalDraftViewModel: StudentPersonalDraftViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        println("FETCH CALLED")
        userViewModel.fetchUsers()
        studentViewModel.fetchStudentProfile(3)
    }
    val currentUser = userViewModel.students.firstOrNull()
    val currentStudentProfile  = studentViewModel.profile
    val profileUser = currentStudentProfile?.user

    val personalDraft by personalDraftViewModel.draft.collectAsState()

    val resolvedUserName = personalDraft.fullName.takeIf { it.isNotBlank() } ?: "User"
    val resolvedRole = personalDraft.role.takeIf { it.isNotBlank() } ?: "Android Developer"
    val resolvedHandle = personalDraft.username.takeIf { it.isNotBlank() }?.let { "@$it" } ?: "@user"
    val dummyBio = "Android developer focused on Kotlin and Jetpack Compose, building clean, scalable apps with strong attention to UI performance."
    val profileBio = currentStudentProfile?.bio?.takeIf { it.isNotBlank() } ?: dummyBio
    val dummySkills = listOf(
        "Android",
        "Kotlin",
        "Jetpack Compose",
        "Firebase",
        "REST APIs",
        "MVVM",
        "Coroutines"
    )
    val profileSkills = currentStudentProfile?.skills?.map { it.name }?.takeIf { it.isNotEmpty() } ?: dummySkills

    val context = LocalContext.current
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: android.net.Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                personalDraftViewModel.updateResumePdfUri(it.toString())
                personalDraftViewModel.save()
                Toast.makeText(context, "PDF uploaded", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                AppTopBar(
                    onMenuClick = onMenuClick,
                    onNotificationClick = onNotificationClick
                )
                ProfileHeader(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    userName = resolvedUserName,
                    role = resolvedRole,
                    handle = resolvedHandle,
                    onClick = onIntroCardClick
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-8).dp)
                        .padding(horizontal = 20.dp)
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
                        text = profileBio,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = {
                                if (personalDraft.resumePdfUri.isNotBlank()) {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            setDataAndType(android.net.Uri.parse(personalDraft.resumePdfUri), "application/pdf")
                                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                                        }
                                        context.startActivity(Intent.createChooser(intent, "Open PDF"))
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "No app found to open PDF", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    pdfPickerLauncher.launch(arrayOf("application/pdf"))
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("RESUME")
                        }
                        Button(
                            onClick = onNavigateToApplication,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("PROFILE")
                        }
                    }
                    PerformanceButton(onClick = onNavigateToAcademic)
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
            val completedMedia = listOf(
                R.drawable.pic_linkedin,
                R.drawable.pic_github,
                R.drawable.pic_leetcode,
                R.drawable.pic_resume,
                R.drawable.pic_saved,
                R.drawable.pic_portfolio,
                R.drawable.pic_dribble,
                R.drawable.pic_behance
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(completedMedia) { mediaRes ->
                    Card(
                        modifier = Modifier
                            .fillParentMaxWidth(0.20f)
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = mediaRes),
                                contentDescription = "Completed profile media",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
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
                    skills = profileSkills,
                    visibleSkillCount = 4
                )
            }
        }
    }
}
