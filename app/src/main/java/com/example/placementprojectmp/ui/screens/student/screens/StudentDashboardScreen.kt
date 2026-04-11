package com.example.placementprojectmp.ui.screens.student.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.ui.screens.shared.component.ApplicationsSection
import com.example.placementprojectmp.ui.screens.shared.component.ApplicationItem
import com.example.placementprojectmp.ui.components.DriveItem
import com.example.placementprojectmp.ui.components.DriveSection
import com.example.placementprojectmp.ui.screens.shared.component.JobItem
import com.example.placementprojectmp.ui.screens.shared.component.JobSection
import androidx.compose.material3.MaterialTheme
import com.example.placementprojectmp.ui.screens.shared.component.AppSearchBar
import com.example.placementprojectmp.ui.screens.student.component.AiChatButton
import com.example.placementprojectmp.ui.screens.student.component.CourseDomainMappingFilter
import com.example.placementprojectmp.ui.screens.student.component.FeatureTool
import com.example.placementprojectmp.ui.screens.student.component.FeatureTools
import com.example.placementprojectmp.ui.screens.shared.component.DashboardUserProfile
import com.example.placementprojectmp.viewmodel.EducationViewModel
import com.example.placementprojectmp.viewmodel.StudentViewModel
import com.example.placementprojectmp.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StudentDashboardScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigateToChatbot: () -> Unit = {},
    onNavigateToPreparation: () -> Unit = {}
) {
    val tag = "StudentDashboard"
    val studentId = 3L

    val userViewModel: UserViewModel = koinViewModel()
    val studentViewModel: StudentViewModel = koinViewModel()
    val educationViewModel: EducationViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        runCatching { userViewModel.fetchUsers() }
            .onFailure { Log.e(tag, "Failed to fetch user for greeting", it) }
        runCatching { studentViewModel.fetchStudentProfile(studentId) }
            .onFailure { Log.e(tag, "Failed to fetch student profile for greeting", it) }
        runCatching { educationViewModel.fetchEducation(studentId) }
            .onFailure { Log.e(tag, "Failed to fetch education profile", it) }
        runCatching { educationViewModel.fetchCourses() }
            .onFailure { Log.e(tag, "Failed to fetch courses", it) }
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedDomains by remember { mutableStateOf(setOf<String>()) }
    var jobs by remember {
        mutableStateOf(
            listOf(
                JobItem("1", "Google", "Software Engineer", "18 hours ago"),
                JobItem("2", "Microsoft", "Product Manager", "1 day ago"),
                JobItem("3", "Amazon", "Data Analyst", "2 days ago")
            )
        )
    }
    val drives = listOf(
        DriveItem("Google", "10:00 AM", "Today"),
        DriveItem("Microsoft", "2:00 PM", "Tomorrow"),
        DriveItem("Amazon", "11:00 AM", "Mar 10")
    )
    val applications = listOf(
        ApplicationItem("Google", "Software Engineer", "Shortlisted"),
        ApplicationItem("Microsoft", "Product Manager", "Applied"),
        ApplicationItem("Meta", "UX Designer", "Interview Scheduled")
    )
    val featureToolsItems = listOf(
        FeatureTool(label = "AI Resume Builder", imageVector = Icons.Default.Description),
        FeatureTool(label = "ATS Optimization Checker", imageVector = Icons.Default.MenuBook),
        FeatureTool(label = "Chatbot", imageVector = Icons.Default.Chat),
        FeatureTool(
            label = "Resources",
            imageVector = Icons.Default.Folder,
            onClick = { onNavigateToPreparation() }
        )
    )

    val currentUser = userViewModel.students.firstOrNull()
    val profileUser = studentViewModel.profile?.user
    val resolvedUserName = profileUser?.name?.takeIf { it.isNotBlank() }
        ?: currentUser?.name?.takeIf { it.isNotBlank() }
        ?: "User"
    val courses = educationViewModel.courses
    val courseDomains = educationViewModel.domains

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            contentPadding = PaddingValues(bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item {
            DashboardUserProfile(userName = resolvedUserName)
        }
        item {
            AppSearchBar(
                modifier = Modifier.padding(horizontal = 20.dp),
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }
        CourseDomainMappingFilter(
            courses = courses,
            courseDomains = courseDomains,
            selectedDomains = selectedDomains,
            isLoading = educationViewModel.isLoading,
            onCourseClick = { course ->
                runCatching {
                    educationViewModel.fetchDomains(course)
                    selectedDomains = emptySet()
                }.onFailure { e ->
                    Log.e(tag, "Failed to fetch domains for course=$course", e)
                    selectedDomains = emptySet()
                }
            },
            onDomainToggle = { domain ->
                selectedDomains = if (domain in selectedDomains)
                    selectedDomains - domain
                else
                    selectedDomains + domain
            }
        )
        item {
            JobSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                jobs = jobs,
                onDismissJob = { job -> jobs = jobs.filter { it.id != job.id } }
            )
        }
        item {
            DriveSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                drives = drives
            )
        }
        item {
            FeatureTools(featureTools = featureToolsItems)
        }
        item {
            ApplicationsSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                applications = applications
            )
        }
        }
        AiChatButton(
            onClick = onNavigateToChatbot,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
