package com.example.placementprojectmp.ui.screens.system.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CastForEducation
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.screens.shared.component.AppSearchBar
import com.example.placementprojectmp.ui.screens.shared.component.ApplicationItem
import com.example.placementprojectmp.ui.screens.shared.component.ApplicationsSection
import com.example.placementprojectmp.ui.screens.shared.component.DashboardUserProfile
import com.example.placementprojectmp.ui.screens.shared.component.JobItem
import com.example.placementprojectmp.ui.screens.shared.component.JobSection
import com.example.placementprojectmp.ui.screens.shared.component.SystemCTA
import com.example.placementprojectmp.ui.screens.shared.component.TopPerformerCard
import com.example.placementprojectmp.ui.screens.student.component.FeatureTool
import com.example.placementprojectmp.ui.screens.student.component.FeatureTools
import com.example.placementprojectmp.ui.screens.system.component.CountGrid
import com.example.placementprojectmp.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "SystemDashboard"

private val systemFeatureToolsItems = listOf(
    FeatureTool(label = "Student Management", imageVector = Icons.Default.School),
    FeatureTool(label = "Faculty Management", imageVector = Icons.Default.CastForEducation),
    FeatureTool(label = "Company Management", imageVector = Icons.Default.Business),
    FeatureTool(label = "System Settings", imageVector = Icons.Default.Settings)
)

private val systemFabMenuLabels = listOf(
    "Add Drive",
    "Add Company",
    "Add Job",
    "Add Student",
    "Add Department",
    "Add Workshop / Training"
)

private fun systemRecentActivityJobs(): List<JobItem> = listOf(
    JobItem(
        id = "sys-a1",
        companyName = "System",
        roleTitle = "New staff account approved",
        timeAgo = "Just now"
    ),
    JobItem(
        id = "sys-a2",
        companyName = "Applications",
        roleTitle = "128 students applied to Android Engineer role at Nexora Systems",
        timeAgo = "2 hours ago"
    ),
    JobItem(
        id = "sys-a3",
        companyName = "Assignments",
        roleTitle = "Teacher Priya S assigned to Veltrix Labs Drive",
        timeAgo = "Yesterday"
    ),
    JobItem(
        id = "sys-a4",
        companyName = "OrbitX",
        roleTitle = "Results announced for OrbitX QA Drive",
        timeAgo = "Yesterday"
    ),
    JobItem(
        id = "sys-a5",
        companyName = "Nexora Systems",
        roleTitle = "Drive at Nexora Systems is currently active",
        timeAgo = "3 days ago"
    ),
    JobItem(
        id = "sys-a6",
        companyName = "Directory",
        roleTitle = "New company added: Quantum Edge",
        timeAgo = "5 days ago"
    )
)

private fun systemDeadlineApplicationItems(): List<ApplicationItem> = listOf(
    ApplicationItem(
        companyName = "Background sync",
        role = "2 failed background sync jobs",
        status = "Action required"
    ),
    ApplicationItem(
        companyName = "Company records",
        role = "One company record has incomplete metadata",
        status = "Review"
    ),
    ApplicationItem(
        companyName = "Approvals",
        role = "Drive publication pending approval for 1 event",
        status = "Pending"
    )
)

/**
 * System home dashboard: profile, search, KPI [CountGrid], recent activity ([JobSection]), [FeatureTools]
 * (system-only labels/icons), top performers, alerts, and [SystemCTA] FAB + sheet (system menu labels).
 */
@Composable
fun SystemDashboardScreen(
    modifier: Modifier = Modifier
) {
    val userViewModel: UserViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        runCatching { userViewModel.fetchUsers() }
            .onFailure { Log.e(TAG, "Failed to fetch users for greeting", it) }
    }

    var searchQuery by remember { mutableStateOf("") }
    var recentJobs by remember { mutableStateOf(systemRecentActivityJobs()) }
    val deadlineItems = remember { systemDeadlineApplicationItems() }

    val currentUser = userViewModel.students.firstOrNull()
    val resolvedUserName = currentUser?.name?.takeIf { it.isNotBlank() } ?: "Administrator"

    val performerPool = listOf(R.drawable.std_1, R.drawable.std_2, R.drawable.std_3, R.drawable.std_4)
    val sixImages = remember(performerPool) { List(6) { performerPool[it % performerPool.size] } }
    val performerNames = remember { listOf("Aarav", "Meera", "Riya", "Kabir", "Dev", "Sana") }
    val performerSubs = remember { List(6) { "2025" } }

    SystemCTA(
        modifier = modifier.fillMaxSize(),
        menuOptionLabels = systemFabMenuLabels
    ) {
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
            item {
                CountGrid(modifier = Modifier.padding(horizontal = 20.dp))
            }
            item {
                JobSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Recent Activity",
                    jobs = recentJobs,
                    onDismissJob = { job -> recentJobs = recentJobs.filter { it.id != job.id } }
                )
            }
            item {
                FeatureTools(featureTools = systemFeatureToolsItems)
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
                        item {
                            TopPerformerCard(
                                modifier = Modifier.width(280.dp),
                                stateKey = 0,
                                topTitle = "Engineering",
                                bottomTitle = "2025",
                                roleLines = listOf("• SDE", "• QA", "• DevOps"),
                                imageResIds = sixImages,
                                performerNames = performerNames,
                                performerSubtitles = performerSubs
                            )
                        }
                        item {
                            TopPerformerCard(
                                modifier = Modifier.width(280.dp),
                                stateKey = 1,
                                topTitle = "Analytics",
                                bottomTitle = "2025",
                                roleLines = listOf("• Data Analyst", "• BI Intern"),
                                imageResIds = sixImages,
                                performerNames = listOf("Dev", "Sana", "Kabir", "Meera", "Aarav", "Riya"),
                                performerSubtitles = performerSubs
                            )
                        }
                    }
                }
            }
            item {
                ApplicationsSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Deadlines & alerts",
                    applications = deadlineItems
                )
            }
        }
    }
}
