package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.staff.components.ExpandableCard
import com.example.placementprojectmp.ui.screens.staff.components.InfoRow
import com.example.placementprojectmp.ui.screens.staff.components.ProfileWithStatusRing
import com.example.placementprojectmp.ui.screens.staff.components.SectionHeader
import com.example.placementprojectmp.ui.screens.staff.components.StatCard
import com.example.placementprojectmp.ui.screens.staff.components.StatusChip
import com.example.placementprojectmp.ui.theme.colormap.ApplicationStatus
import com.example.placementprojectmp.ui.theme.colormap.ColorMapper
import com.example.placementprojectmp.viewmodel.StaffCandidateDetailViewModel
import com.example.placementprojectmp.viewmodel.StaffDriveDetailViewModel
import com.example.placementprojectmp.ui.screens.shared.screens.JobDetailScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaffDriveDetailScreen(
    modifier: Modifier = Modifier,
    driveId: String,
    viewModel: StaffDriveDetailViewModel = koinViewModel()
) {
    val state by viewModel.uiState
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        item { SectionHeader(title = "Drive Detail ($driveId)") }
        item {
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Nexora Systems", style = MaterialTheme.typography.titleMedium)
                    Text("Bengaluru", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        item { SectionHeader(title = "Key Info") }
        item {
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.data.forEach {
                        val pair = it.split(":")
                        if (pair.size >= 2) InfoRow(pair[0], pair.drop(1).joinToString(":").trim())
                    }
                }
            }
        }
        item {
            ExpandableCard(title = "Selection Rounds") {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    InfoRow("Round 1", "Aptitude - 128 students")
                    InfoRow("Round 2", "Technical - 48 students")
                    InfoRow("Round 3", "HR - 16 students")
                }
            }
        }
        item {
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionHeader("Eligibility")
                    StatusChip("CGPA 7.0+", ColorMapper.getColor(ApplicationStatus.APPLIED))
                    StatusChip("No active backlogs", ColorMapper.getColor(ApplicationStatus.SHORTLISTED))
                    StatusChip("CSE / IT / ECE", ColorMapper.getColor(ApplicationStatus.INTERVIEW_SCHEDULED))
                }
            }
        }
        item {
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionHeader("Assigned Faculty")
                    InfoRow("Name", "Dr. R. Menon")
                    InfoRow("Contact", "faculty.placement@college.edu")
                }
            }
        }
        item {
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionHeader("Candidate Stats")
                    InfoRow("Applied", "128")
                    InfoRow("Eligible but not applied", "43")
                }
            }
        }
    }
}

@Composable
fun StaffJobDetailScreen(
    modifier: Modifier = Modifier,
    jobId: String
) {
    JobDetailScreen(modifier = modifier, jobId = jobId)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffCandidateDetailScreen(
    modifier: Modifier = Modifier,
    sourceId: String,
    viewModel: StaffCandidateDetailViewModel = koinViewModel()
) {
    val state by viewModel.uiState
    val grouped = state.data.groupBy { it.status }
    val ordered = listOf(
        ApplicationStatus.APPLIED,
        ApplicationStatus.SHORTLISTED,
        ApplicationStatus.INTERVIEW_SCHEDULED,
        ApplicationStatus.SELECTED,
        ApplicationStatus.REJECTED
    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionHeader("Candidate Details ($sourceId)") }
        ordered.forEach { status ->
            val itemsForStatus = grouped[status].orEmpty()
            if (itemsForStatus.isNotEmpty()) {
                stickyHeader {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Text(
                            text = status.name.replace("_", " "),
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                items(itemsForStatus) { candidate ->
                    Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                ProfileWithStatusRing(
                                    initials = candidate.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
                                    status = candidate.status
                                )
                                Column {
                                    Text(candidate.name, style = MaterialTheme.typography.titleMedium)
                                    Text(candidate.roll, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Button(onClick = {}) { Text("Resume") }
                        }
                    }
                }
            }
        }
        item {
            SectionHeader("Eligible Candidates (Not Applied)")
        }
        items(listOf("CSE-2301", "ECE-2309", "IT-2313")) { roll ->
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Candidate $roll")
                    Crossfade(targetState = true, label = "eligible_badge") {
                        StatusChip("Eligible", ColorMapper.getColor(ApplicationStatus.OFFERED))
                    }
                }
            }
        }
    }
}
