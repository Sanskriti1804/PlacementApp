package com.example.placementprojectmp.ui.screens.staff.screens

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.screens.shared.component.AppSearchBar
import com.example.placementprojectmp.ui.screens.shared.component.ApplicationItem
import com.example.placementprojectmp.ui.screens.shared.component.DashboardUserProfile
import com.example.placementprojectmp.ui.screens.shared.component.JobItem
import com.example.placementprojectmp.ui.screens.shared.component.JobSection
import com.example.placementprojectmp.ui.screens.shared.component.SystemCTA
import com.example.placementprojectmp.ui.screens.student.component.CourseDomainMappingFilter
import com.example.placementprojectmp.ui.screens.student.component.FeatureTool
import com.example.placementprojectmp.ui.screens.student.component.FeatureTools
import com.example.placementprojectmp.ui.screens.shared.component.cards.UserIdCard
import com.example.placementprojectmp.ui.screens.staff.StaffStudentPortraitIds
import com.example.placementprojectmp.viewmodel.EducationViewModel
import com.example.placementprojectmp.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

private const val TAG = "StaffDashboard"
private const val educationProfileId = 3L

private val staffFeatureToolsItems = listOf(
    FeatureTool(label = "Company Documents", imageVector = Icons.Default.Business),
    FeatureTool(label = "Student Resumes", imageVector = Icons.Default.Article),
    FeatureTool(label = "Student Management", imageVector = Icons.Default.Groups),
    FeatureTool(label = "Department Management", imageVector = Icons.Default.AccountTree)
)

private data class StaffTopPerformerSectionCardData(
    val stateKey: Int,
    val header: String,
    val subheader: String,
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

private fun staffTopPerformerSectionCards(): List<StaffTopPerformerSectionCardData> {
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
        StaffTopPerformerSectionCardData(
            stateKey = 0,
            header = "Top Students",
            subheader = "Students with highest application, selection and activity in drives",
            imageResIds = sixFromPool(stdPool, seed = 701),
            performerNames = studentNames,
            performerSubtitles = studentSubs
        ),
        StaffTopPerformerSectionCardData(
            stateKey = 1,
            header = "Top Companies",
            subheader = "Companies with highest student hiring and engagement",
            imageResIds = sixFromPool(compPool, seed = 802),
            performerNames = companyNames,
            performerSubtitles = companySubs
        ),
        StaffTopPerformerSectionCardData(
            stateKey = 2,
            header = "Top Job Roles",
            subheader = "Jobs with highest number of student applications",
            imageResIds = sixFromPool(compPool, seed = 903),
            performerNames = roleNames,
            performerSubtitles = roleSubs
        ),
        StaffTopPerformerSectionCardData(
            stateKey = 3,
            header = "Top Drives",
            subheader = "Placement drives with maximum participation and successful outcomes",
            imageResIds = sixFromPool(compPool, seed = 604),
            performerNames = driveNames,
            performerSubtitles = driveSubs
        )
    )
}

/**
 * Staff home dashboard: mirrors [com.example.placementprojectmp.ui.screens.student.screens.StudentDashboardScreen]
 * composition (profile, search, course/domain filter spacing) using the same shared building blocks; staff-only data
 * lives in this file; quick-add FAB + sheet are provided by `SystemCTA` (shared component).
 */
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
    val topPerformerCards = remember { staffTopPerformerSectionCards() }

    val currentUser = userViewModel.students.firstOrNull()
    val resolvedUserName = currentUser?.name?.takeIf { it.isNotBlank() } ?: "Staff"
    val courses = educationViewModel.courses
    val courseDomains = educationViewModel.domains

    SystemCTA(modifier = modifier.fillMaxSize()) {
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
            if (selectedDomains.isNotEmpty()) {
                item {
                    val domainStudents = remember(selectedDomains) {
                        selectedDomains.flatMap { domain ->
                            listOf(
                                Triple("Rahul $domain", "rahul.${domain.lowercase().replace(" ", "")}@email.com", "CSE-BT-2101"),
                                Triple("Priya $domain", "priya.${domain.lowercase().replace(" ", "")}@email.com", "ECE-BT-2104")
                            )
                        }
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(domainStudents.size) { index ->
                            val (name, email, roll) = domainStudents[index]
                            UserIdCard(
                                modifier = Modifier.width(320.dp),
                                name = name,
                                email = email,
                                idText = roll,
                                profileImageResId = StaffStudentPortraitIds.all[index % StaffStudentPortraitIds.all.size],
                                departmentValue = selectedDomains.elementAt(index / 2),
                                secondaryAttributeLabel = "Hired",
                                secondaryAttributeValue = "NA",
                                optionalEndTag = null,
                                tags = emptyList(),
                                selected = false,
                                onSelectionChange = {},
                                isFavorite = false,
                                onFavoriteToggle = {}
                            )
                        }
                    }
                }
            }
            item {
                JobSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Recent Activities",
                    jobs = recentActivities,
                    onDismissJob = { job -> recentActivities = recentActivities.filter { it.id != job.id } }
                )
            }
            item {
                FeatureTools(featureTools = staffFeatureToolsItems)
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
                StaffDashboardDeadlinesSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Deadlines & alerts",
                    applications = deadlineItems
                )
            }
        }
    }
}
