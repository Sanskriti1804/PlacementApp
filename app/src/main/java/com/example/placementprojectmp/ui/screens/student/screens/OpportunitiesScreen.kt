package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.cards.DriveCard
import com.example.placementprojectmp.ui.screens.shared.cards.JobCard
import com.example.placementprojectmp.ui.screens.shared.component.AppScreenHeader
import com.example.placementprojectmp.ui.screens.shared.component.AppSearchBar
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.ui.screens.shared.component.FilterCapsuleRow
import com.example.placementprojectmp.viewmodel.DriveUiModel
import com.example.placementprojectmp.viewmodel.JobType
import com.example.placementprojectmp.viewmodel.JobUiModel
import com.example.placementprojectmp.viewmodel.StudentOpportunitiesViewModel
import com.example.placementprojectmp.viewmodel.WorkMode
import org.koin.androidx.compose.koinViewModel

private val jobTypeFilterOptions = listOf(
    "Internship",
    "Full-time",
    "Part-time",
    "Internship + PPO"
)

private val locationFilterOptions = listOf(
    "Remote",
    "Hybrid",
    "Onsite / Work from Office"
)

private val experienceFilterOptions = listOf(
    "Fresher",
    "Experienced",
    "Internship Experience Required"
)

internal fun studentOpportunitiesDummyJobs(): List<JobUiModel> =
    com.example.placementprojectmp.data.local.StudentOpportunitiesFallbackData.jobs

internal fun studentOpportunitiesDummyDrives(): List<DriveUiModel> =
    com.example.placementprojectmp.data.local.StudentOpportunitiesFallbackData.drives

internal fun studentDriveRegistrationUrl(driveId: String): String =
    com.example.placementprojectmp.data.local.StudentOpportunitiesFallbackData.registrationUrl(driveId)

private fun JobUiModel.matchesJobTypeFilter(selected: String?): Boolean {
    if (selected.isNullOrBlank()) return true
    return when (selected) {
        "Full-time" -> jobType == JobType.FULL_TIME
        "Internship" -> jobType == JobType.INTERNSHIP
        "Part-time" -> jobType == JobType.CONTRACT
        "Internship + PPO" -> jobType == JobType.INTERNSHIP || jobType == JobType.CONTRACT
        else -> true
    }
}

private fun JobUiModel.matchesLocationFilter(selected: String?): Boolean {
    if (selected.isNullOrBlank()) return true
    return when (selected) {
        "Remote" -> workMode == WorkMode.REMOTE
        "Hybrid" -> workMode == WorkMode.HYBRID
        "Onsite / Work from Office" -> workMode == WorkMode.ONSITE
        else -> true
    }
}

private fun JobUiModel.matchesExperienceFilter(selected: String?): Boolean {
    if (selected.isNullOrBlank()) return true
    return when (selected) {
        "Fresher" -> salaryLpa <= 8f || jobType == JobType.INTERNSHIP
        "Experienced" -> salaryLpa > 8f && jobType == JobType.FULL_TIME
        "Internship Experience Required" -> jobType == JobType.INTERNSHIP
        else -> true
    }
}

private fun DriveUiModel.matchesSearch(query: String): Boolean {
    if (query.isBlank()) return true
    return companyName.contains(query, ignoreCase = true) ||
        driveName.contains(query, ignoreCase = true)
}

/**
 * Same TabRow styling as [com.example.placementprojectmp.ui.screens.shared.component.AppTabSection];
 * list content is driven by tab index here because AppTabSection only supports text tab bodies.
 */
@Composable
private fun OpportunitiesJobsDrivesTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val titles = listOf("Jobs", "Drives")
    TabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedTab == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun OpportunitiesScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onJobClick: (jobId: String) -> Unit = {},
    onDriveClick: (driveId: String) -> Unit = {},
    onApplyClick: (jobId: String) -> Unit = {},
    onDriveRegisterClick: (driveId: String) -> Unit = {}
) {
    val opportunitiesVm: StudentOpportunitiesViewModel = koinViewModel()
    val oppState by opportunitiesVm.state.collectAsState()
    val allJobs = oppState.jobs
    val allDrives = oppState.drives

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedJobType by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedLocation by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedExperience by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    val q = searchQuery.trim()
    val filteredJobs = remember(
        allJobs,
        q,
        selectedJobType,
        selectedLocation,
        selectedExperience
    ) {
        allJobs.filter { job ->
            val textOk = q.isBlank() ||
                job.companyName.contains(q, ignoreCase = true) ||
                job.jobRole.contains(q, ignoreCase = true) ||
                job.location.contains(q, ignoreCase = true)
            textOk &&
                job.matchesJobTypeFilter(selectedJobType) &&
                job.matchesLocationFilter(selectedLocation) &&
                job.matchesExperienceFilter(selectedExperience)
        }
    }
    val filteredDrives = remember(allDrives, q) {
        allDrives.filter { it.matchesSearch(q) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AppTopBar(
                title = "Opportunities",
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item {
            AppScreenHeader(
                title = "Explore Opportunities",
                subtitle = "Browse, explore and apply"
            )
        }
        item {
            AppSearchBar(
                modifier = Modifier.padding(horizontal = 20.dp),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = "Search jobs and drives"
            )
        }
        item {
            FilterCapsuleRow(
                modifier = Modifier.padding(horizontal = 20.dp),
                branchOptions = jobTypeFilterOptions,
                courseOptions = locationFilterOptions,
                domainOptions = experienceFilterOptions,
                selectedBranch = selectedJobType ?: "Type",
                selectedCourse = selectedLocation ?: "Work Mode",
                selectedDomain = selectedExperience ?: "Experience",
                onBranchSelect = { selectedJobType = it },
                onCourseSelect = { selectedLocation = it },
                onDomainSelect = { selectedExperience = it }
            )
        }
        item {
            OpportunitiesJobsDrivesTabRow(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        if (selectedTab == 0) {
            items(
                items = filteredJobs,
                key = { j: JobUiModel -> j.id }
            ) { job: JobUiModel ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    JobCard(
                        job = job,
                        onApplyClick = { onApplyClick(job.id) },
                        onClick = { onJobClick(job.id) }
                    )
                }
            }
        } else {
            items(
                items = filteredDrives,
                key = { d: DriveUiModel -> d.id }
            ) { drive: DriveUiModel ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    DriveCard(
                        drive = drive,
                        onRegisterClick = { onDriveRegisterClick(drive.id) },
                        onClick = { onDriveClick(drive.id) }
                    )
                }
            }
        }
    }
}
