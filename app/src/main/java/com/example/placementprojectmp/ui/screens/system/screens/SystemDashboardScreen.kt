package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.DomainChipRow
import com.example.placementprojectmp.ui.components.DriveItem
import com.example.placementprojectmp.ui.components.DriveSection
import com.example.placementprojectmp.ui.components.FeatureCard
import com.example.placementprojectmp.ui.components.JobItem
import com.example.placementprojectmp.ui.components.JobSection
import com.example.placementprojectmp.ui.components.SearchBar
import com.example.placementprojectmp.ui.screens.staff.components.SectionHeader
import com.example.placementprojectmp.ui.screens.staff.components.StatCard

@Composable
fun SystemDashboardScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilters by remember { mutableStateOf(setOf<String>()) }
    var jobs by remember {
        mutableStateOf(
            listOf(
                JobItem("1", "Nexora Systems", "Android Engineer", "2 hours ago"),
                JobItem("2", "OrbitX", "Backend Developer", "5 hours ago"),
                JobItem("3", "Veltrix Labs", "QA Analyst", "1 day ago"),
                JobItem("4", "VertexAI", "Intern - Data Platform", "1 day ago")
            )
        )
    }

    val filterChips = listOf(
        "CSE", "ECE", "IT", "MBA",
        "Product", "Service", "AI", "Data",
        "Internship", "Full-time", "Drive: Ongoing", "Drive: Upcoming"
    )
    val departments = listOf("Engineering", "Management", "Operations", "Training", "Analytics")
    val branches = listOf("CSE", "ECE", "IT", "ME", "CE")
    val drives = listOf(
        DriveItem("Nexora Systems", "10:00 AM", "Ongoing"),
        DriveItem("Veltrix Labs", "2:30 PM", "Upcoming"),
        DriveItem("OrbitX", "11:00 AM", "Completed")
    )
    val activities = listOf(
        "New staff account approved",
        "128 students applied to Android Engineer role at Nexora Systems",
        "Teacher Priya S assigned to Veltrix Labs Drive",
        "Results announced for OrbitX QA Drive",
        "Drive at Nexora Systems is currently active",
        "New company added: Quantum Edge"
    )
    val alerts = listOf(
        "2 failed background sync jobs",
        "One company record has incomplete metadata",
        "Drive publication pending approval for 1 event"
    )
    val toolItems = listOf(
        "Manage Students" to Icons.Default.School,
        "Manage Staff" to Icons.Default.Groups,
        "Add Company" to Icons.Default.Business,
        "Create Drive" to Icons.Default.Work,
        "Post Job" to Icons.Default.PostAdd
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
                SearchBar(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    placeholder = "Search students, staff, companies, jobs, drives...",
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )
            }
            item {
                DomainChipRow(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    domains = filterChips,
                    selectedDomains = selectedFilters,
                    onDomainToggle = { chip ->
                        selectedFilters = if (chip in selectedFilters) {
                            selectedFilters - chip
                        } else {
                            selectedFilters + chip
                        }
                    }
                )
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    SectionHeader("System Overview")
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) { StatCard("Total Students", "1,142", Icons.Default.School) }
                    Column(modifier = Modifier.weight(1f)) { StatCard("Total Staff", "146", Icons.Default.People) }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) { StatCard("Total Companies", "84", Icons.Default.Business) }
                    Column(modifier = Modifier.weight(1f)) { StatCard("Total Drives", "23", Icons.Default.Work) }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) { StatCard("Total Jobs", "67", Icons.Default.Work) }
                    Column(modifier = Modifier.weight(1f)) { StatCard("Internships", "29", Icons.Default.Domain) }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) { StatCard("Departments", "18", Icons.Default.Group) }
                    Column(modifier = Modifier.weight(1f)) { StatCard("Branches", "12", Icons.Default.Engineering) }
                }
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    SectionHeader("Activity & Insights")
                }
            }
            items(activities) { activity ->
                ActivityItem(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "System Insight",
                    text = activity,
                    time = "Just now"
                )
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    SectionHeader("Alerts")
                }
            }
            items(alerts) { alert ->
                Card(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = alert,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    SectionHeader("Departments / Branches")
                    Spacer(modifier = Modifier.height(12.dp))
                    DomainChipRow(
                        domains = departments,
                        selectedDomains = emptySet(),
                        onDomainToggle = {}
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    DomainChipRow(
                        domains = branches,
                        selectedDomains = emptySet(),
                        onDomainToggle = {}
                    )
                }
            }
            item {
                JobSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Jobs & Internships",
                    jobs = jobs,
                    onDismissJob = { job -> jobs = jobs.filter { it.id != job.id } }
                )
            }
            item {
                DriveSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Drives",
                    drives = drives
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    SectionHeader("Feature Tools")
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(toolItems.size) { index ->
                            val (label, icon) = toolItems[index]
                            FeatureCard(
                                modifier = Modifier.width(120.dp),
                                label = label,
                                imageVector = icon,
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp),
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add action",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ActivityItem(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    time: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            StatusTag(label = "Live", icon = Icons.Default.Settings)
        }
    }
}

@Composable
private fun StatusTag(
    label: String,
    icon: ImageVector
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
