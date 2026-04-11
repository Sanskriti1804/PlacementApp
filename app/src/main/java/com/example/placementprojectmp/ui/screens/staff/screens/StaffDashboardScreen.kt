package com.example.placementprojectmp.ui.screens.staff.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.screens.shared.component.AppSearchBar
import com.example.placementprojectmp.ui.screens.shared.component.ApplicationItem
import com.example.placementprojectmp.ui.screens.shared.component.DashboardUserProfile
import com.example.placementprojectmp.ui.screens.shared.component.JobItem
import com.example.placementprojectmp.ui.screens.shared.component.JobSection
import com.example.placementprojectmp.ui.screens.shared.component.TopPerformerCard
import com.example.placementprojectmp.ui.screens.student.component.CourseDomainMappingFilter
import com.example.placementprojectmp.viewmodel.EducationViewModel
import com.example.placementprojectmp.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "StaffDashboard"
private const val educationProfileId = 3L

private data class TopPerformerCardData(
    val stateKey: Int,
    val topTitle: String,
    val bottomTitle: String,
    val roleLines: List<String>,
    val imageResIds: List<Int>,
    val performerNames: List<String>,
    val performerSubtitles: List<String>
)

private fun staffRecentActivityJobItems(): List<JobItem> = listOf(
    JobItem(
        id = "sa-1",
        companyName = "Google",
        roleTitle = "10 students applied to Google SQL screening",
        timeAgo = "2 hours ago"
    ),
    JobItem(
        id = "sa-2",
        companyName = "Infosys Drive",
        roleTitle = "Results declared for Infosys Drive — publish shortlist",
        timeAgo = "Yesterday"
    ),
    JobItem(
        id = "sa-3",
        companyName = "Workshop: Aptitude Boost",
        roleTitle = "5 students registered for workshop (Batch A)",
        timeAgo = "3 days ago"
    ),
    JobItem(
        id = "sa-4",
        companyName = "Riya Sharma",
        roleTitle = "Placement coordinator flagged incomplete profile documents",
        timeAgo = "5 days ago"
    )
)

private fun staffDeadlineApplicationItems(): List<ApplicationItem> = listOf(
    ApplicationItem(
        companyName = "Nexora Systems Drive",
        role = "Drive results declaration window closes in 48 hours",
        status = "Action required"
    ),
    ApplicationItem(
        companyName = "Amazon SDE II",
        role = "Job posting closes for campus applications",
        status = "Closing soon"
    ),
    ApplicationItem(
        companyName = "Eligible pool",
        role = "Students not applied: 14 still pending for FinEdge Analytics drive",
        status = "Follow up"
    )
)

/** Staff-only mirror of the deadlines section; status line uses theme error (red) instead of primary (blue). */
@Composable
private fun StaffDashboardDeadlinesSection(
    modifier: Modifier = Modifier,
    title: String,
    applications: List<ApplicationItem>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            .padding(20.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            applications.forEach { app ->
                StaffDashboardDeadlineStatusCard(
                    companyName = app.companyName,
                    role = app.role,
                    status = app.status
                )
            }
        }
    }
}

@Composable
private fun StaffDashboardDeadlineStatusCard(
    modifier: Modifier = Modifier,
    companyName: String,
    role: String,
    status: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(14.dp)
    ) {
        Text(
            text = companyName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = role,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = status,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}

private fun staffTopPerformerCards(): List<TopPerformerCardData> {
    val pool = listOf(R.drawable.std_1, R.drawable.std_2, R.drawable.std_3, R.drawable.std_4)
    val names = listOf("Aarav", "Meera", "Riya", "Kabir", "Dev", "Sana")
    val subs = listOf("2025", "2025", "2025", "2025", "2025", "2025")
    val six = List(6) { pool[it % pool.size] }
    return listOf(
        TopPerformerCardData(
            stateKey = 0,
            topTitle = "Engineering",
            bottomTitle = "2025",
            roleLines = listOf("• SDE", "• QA", "• DevOps"),
            imageResIds = six,
            performerNames = names,
            performerSubtitles = subs
        ),
        TopPerformerCardData(
            stateKey = 1,
            topTitle = "Analytics",
            bottomTitle = "2025",
            roleLines = listOf("• Data Analyst", "• BI Intern"),
            imageResIds = six,
            performerNames = listOf("Dev", "Sana", "Kabir", "Meera", "Aarav", "Riya"),
            performerSubtitles = subs
        )
    )
}

/**
 * Staff home dashboard: mirrors [com.example.placementprojectmp.ui.screens.student.screens.StudentDashboardScreen]
 * composition (profile, search, course/domain filter spacing) using the same shared building blocks; staff-only data
 * and FAB quick-add sheet live in this file only.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffDashboardScreen(
    modifier: Modifier = Modifier
) {
    val userViewModel: UserViewModel = koinViewModel()
    val educationViewModel: EducationViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        runCatching { userViewModel.fetchUsers() }
            .onFailure { Log.e(TAG, "Failed to fetch users", it) }
        runCatching { educationViewModel.fetchEducation(educationProfileId) }
            .onFailure { Log.e(TAG, "Failed to fetch education profile", it) }
        runCatching { educationViewModel.fetchCourses() }
            .onFailure { Log.e(TAG, "Failed to fetch courses", it) }
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedDomains by remember { mutableStateOf(setOf<String>()) }
    var recentActivities by remember { mutableStateOf(staffRecentActivityJobItems()) }
    val deadlineItems = remember { staffDeadlineApplicationItems() }
    val topPerformerCards = remember { staffTopPerformerCards() }

    var showFabMenu by remember { mutableStateOf(false) }
    val fabSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val currentUser = userViewModel.students.firstOrNull()
    val resolvedUserName = currentUser?.name?.takeIf { it.isNotBlank() } ?: "Staff"
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
                        Log.e(TAG, "Failed to fetch domains for course=$course", e)
                        selectedDomains = emptySet()
                    }
                },
                onDomainToggle = { domain ->
                    selectedDomains = if (domain in selectedDomains) {
                        selectedDomains - domain
                    } else {
                        selectedDomains + domain
                    }
                }
            )
            item {
                JobSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Recent Activities",
                    jobs = recentActivities,
                    onDismissJob = { job -> recentActivities = recentActivities.filter { it.id != job.id } }
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "Top Performers",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed(topPerformerCards, key = { _, c -> c.stateKey }) { _, card ->
                            TopPerformerCard(
                                modifier = Modifier.width(280.dp),
                                stateKey = card.stateKey,
                                topTitle = card.topTitle,
                                bottomTitle = card.bottomTitle,
                                roleLines = card.roleLines,
                                imageResIds = card.imageResIds,
                                performerNames = card.performerNames,
                                performerSubtitles = card.performerSubtitles
                            )
                        }
                    }
                }
            }
            item {
                StaffDashboardDeadlinesSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Deadlines & alerts",
                    applications = deadlineItems
                )
            }
        }

        FloatingActionButton(
            onClick = { showFabMenu = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Quick add"
            )
        }
    }

    if (showFabMenu) {
        ModalBottomSheet(
            onDismissRequest = { showFabMenu = false },
            sheetState = fabSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Quick add",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                val options = listOf(
                    "Add Drive",
                    "Add Company",
                    "Add Job",
                    "Add Student",
                    "Add Tutorial Class",
                    "Add Workshop"
                )
                options.forEach { label ->
                    TextButton(
                        onClick = { showFabMenu = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
