package com.example.placementprojectmp.ui.screens.student.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.ApplicationsSection
import com.example.placementprojectmp.ui.components.ApplicationItem
import com.example.placementprojectmp.ui.components.CourseCarousel
import com.example.placementprojectmp.ui.components.DomainChipRow
import com.example.placementprojectmp.ui.components.DriveItem
import com.example.placementprojectmp.ui.components.DriveSection
import com.example.placementprojectmp.ui.components.FeatureCard
import com.example.placementprojectmp.ui.components.JobItem
import com.example.placementprojectmp.ui.components.JobSection
import com.example.placementprojectmp.ui.components.SearchBar
import androidx.compose.material3.MaterialTheme
import com.example.placementprojectmp.viewmodel.EducationViewModel
import com.example.placementprojectmp.viewmodel.StudentViewModel
import com.example.placementprojectmp.viewmodel.UserViewModel
import kotlinx.coroutines.delay
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
    var showJobsAnimation by remember { mutableStateOf(false) }
    var isFabExpanded by remember { mutableStateOf(true) }
    val featureItems = listOf(
        "Resume" to Icons.Default.Description,
        "Preparation" to Icons.Default.MenuBook,
        "Chatbot" to Icons.Default.Chat,
        "Resources" to Icons.Default.Folder
    )

    val currentUser = userViewModel.students.firstOrNull()
    val profileUser = studentViewModel.profile?.user
    val resolvedUserName = profileUser?.name?.takeIf { it.isNotBlank() }
        ?: currentUser?.name?.takeIf { it.isNotBlank() }
        ?: "User"
    val courses = educationViewModel.courses
    val courseDomains = educationViewModel.domains
    LaunchedEffect(Unit) {
        showJobsAnimation = true
    }
    LaunchedEffect(Unit) {
        delay(2500)
        isFabExpanded = false
    }
    val fabSize by animateDpAsState(
        targetValue = if (isFabExpanded) 59.dp else 56.dp,
        animationSpec = tween(durationMillis = 300),
        label = "fab_size"
    )

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
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.pfp_user),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Hi, $resolvedUserName",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        item {
            SearchBar(
                modifier = Modifier.padding(horizontal = 20.dp),
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }
        item {
            CourseCarousel(
                modifier = Modifier.padding(horizontal = 20.dp),
                courses = courses,
                onCourseClick = { course ->
                    runCatching {
                        educationViewModel.fetchDomains(course)
                        selectedDomains = emptySet()
                    }.onFailure { e ->
                        Log.e(tag, "Failed to fetch domains for course=$course", e)
                        selectedDomains = emptySet()
                    }
                }
            )
        }
        if (!educationViewModel.isLoading && courses.isEmpty()) {
            item {
                Text(
                    text = "Course information is unavailable right now.",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            DomainChipRow(
                modifier = Modifier.padding(horizontal = 20.dp),
                domains = courseDomains,
                selectedDomains = selectedDomains,
                onDomainToggle = { domain ->
                    selectedDomains = if (domain in selectedDomains)
                        selectedDomains - domain
                    else
                        selectedDomains + domain
                }
            )
        }
        if (!educationViewModel.isLoading && courseDomains.isEmpty()) {
            item {
                Text(
                    text = "Select a course to view domains.",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            AnimatedVisibility(
                visible = showJobsAnimation,
                enter = fadeIn(animationSpec = spring()) + scaleIn(
                    animationSpec = spring(dampingRatio = 0.85f, stiffness = 450f),
                    initialScale = 0.96f
                )
            ) {
                JobSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    jobs = jobs,
                    onDismissJob = { job -> jobs = jobs.filter { it.id != job.id } }
                )
            }
        }
        item {
            DriveSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                drives = drives
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Feature Tools",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(featureItems.size) { index ->
                        val (label, icon) = featureItems[index]
                        FeatureCard(
                            modifier = Modifier.width(100.dp),
                            label = label,
                            imageVector = icon,
                            onClick = {
                                if (label == "Resources") onNavigateToPreparation()
                            }
                        )
                    }
                }
            }
        }
        item {
            ApplicationsSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                applications = applications
            )
        }
        }
        FloatingActionButton(
            onClick = onNavigateToChatbot,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp)
                .size(fabSize),
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_aii),
                contentDescription = "AIDA Chatbot",
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
