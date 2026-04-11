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
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.placementprojectmp.ui.screens.shared.component.DashboardUserProfile
import com.example.placementprojectmp.ui.screens.shared.component.JobItem
import com.example.placementprojectmp.ui.screens.shared.component.JobSection
import com.example.placementprojectmp.ui.screens.shared.component.SystemCTA
import com.example.placementprojectmp.ui.screens.staff.screens.StaffDashboardTopPerformerCard
import com.example.placementprojectmp.ui.screens.student.component.FeatureTool
import com.example.placementprojectmp.ui.screens.student.component.FeatureTools
import com.example.placementprojectmp.ui.screens.system.component.SystemDashboardCountGrid
import com.example.placementprojectmp.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

private const val TAG = "SystemDashboard"

private data class SystemTopPerformerSectionCardData(
    val stateKey: Int,
    val header: String,
    val subheader: String,
    val imageResIds: List<Int>,
    val performerNames: List<String>,
    val performerSubtitles: List<String>
)

private fun systemTopPerformerSectionCards(): List<SystemTopPerformerSectionCardData> {
    val stdPool = listOf(R.drawable.std_1, R.drawable.std_2, R.drawable.std_3)
    val compPool = listOf(R.drawable.comp_1, R.drawable.comp_2, R.drawable.comp_3)
    fun sixFromPool(pool: List<Int>, seed: Int): List<Int> {
        val r = Random(seed)
        return List(6) { pool[r.nextInt(pool.size)] }
    }
    val studentNames = listOf("Aarav", "Meera", "Riya", "Kabir", "Dev", "Sana")
    val studentSubs = listOf("12 apps", "10 apps", "9 apps", "8 apps", "8 apps", "7 apps")
    val companyNames = listOf("Nexora", "FinEdge", "CloudBay", "ByteForge", "DataWorks", "ScaleUp")
    val companySubs = listOf("42 hires", "38 hires", "31 hires", "29 hires", "24 hires", "22 hires")
    val roleNames = listOf("SDE I", "Data Analyst", "QA", "DevOps", "ML Intern", "PM")
    val roleSubs = listOf("210 apps", "198 apps", "176 apps", "154 apps", "141 apps", "128 apps")
    val driveNames = listOf("Mega Drive Q1", "FinTech Week", "Product Blitz", "Analytics Day", "Core Eng", "Winter Sprint")
    val driveSubs = listOf("220 joined", "205 joined", "198 joined", "190 joined", "182 joined", "175 joined")
    return listOf(
        SystemTopPerformerSectionCardData(
            stateKey = 0,
            header = "Top Students",
            subheader = "Students with highest application, selection and activity in drives",
            imageResIds = sixFromPool(stdPool, seed = 701),
            performerNames = studentNames,
            performerSubtitles = studentSubs
        ),
        SystemTopPerformerSectionCardData(
            stateKey = 1,
            header = "Top Companies",
            subheader = "Companies with highest student hiring and engagement",
            imageResIds = sixFromPool(compPool, seed = 802),
            performerNames = companyNames,
            performerSubtitles = companySubs
        ),
        SystemTopPerformerSectionCardData(
            stateKey = 2,
            header = "Top Job Roles",
            subheader = "Jobs with highest number of student applications",
            imageResIds = sixFromPool(compPool, seed = 903),
            performerNames = roleNames,
            performerSubtitles = roleSubs
        ),
        SystemTopPerformerSectionCardData(
            stateKey = 3,
            header = "Top Drives",
            subheader = "Placement drives with maximum participation and successful outcomes",
            imageResIds = sixFromPool(compPool, seed = 604),
            performerNames = driveNames,
            performerSubtitles = driveSubs
        )
    )
}

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
 * System home dashboard: profile, search, KPI [SystemDashboardCountGrid], recent activity ([JobSection]), [FeatureTools]
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

    val topPerformerCards = remember { systemTopPerformerSectionCards() }

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
                SystemDashboardCountGrid(modifier = Modifier.padding(horizontal = 20.dp))
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
                        itemsIndexed(topPerformerCards, key = { _, c -> c.stateKey }) { _, card ->
                            StaffDashboardTopPerformerCard(
                                modifier = Modifier.width(280.dp),
                                stateKey = card.stateKey,
                                header = card.header,
                                subheader = card.subheader,
                                imageResIds = card.imageResIds,
                                performerNames = card.performerNames,
                                performerSubtitles = card.performerSubtitles
                            )
                        }
                    }
                }
            }
            item {
                SystemDashboardDeadlinesSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Deadlines & alerts",
                    applications = deadlineItems
                )
            }
        }
    }
}
