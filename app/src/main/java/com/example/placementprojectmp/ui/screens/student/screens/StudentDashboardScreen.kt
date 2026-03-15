package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.ApplicationsSection
import com.example.placementprojectmp.ui.components.ApplicationItem
import com.example.placementprojectmp.ui.components.CourseCarousel
import com.example.placementprojectmp.ui.components.DomainChipRow
import com.example.placementprojectmp.ui.components.DriveItem
import com.example.placementprojectmp.ui.components.DriveSection
import com.example.placementprojectmp.ui.components.FeatureCard
import com.example.placementprojectmp.ui.components.GreetingSection
import com.example.placementprojectmp.ui.components.JobItem
import com.example.placementprojectmp.ui.components.JobSection
import com.example.placementprojectmp.ui.components.SearchBar
import androidx.compose.material3.MaterialTheme

@Composable
fun StudentDashboardScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
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
    val featureItems = listOf(
        "Resume" to Icons.Default.Description,
        "Mock Interview" to Icons.Default.RecordVoiceOver,
        "Preparation" to Icons.Default.MenuBook,
        "Chatbot" to Icons.Default.Chat,
        "Resources" to Icons.Default.Folder
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item {
            GreetingSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                userName = "Alex"
            )
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
                onCourseClick = {}
            )
        }
        item {
            DomainChipRow(
                modifier = Modifier.padding(horizontal = 20.dp),
                selectedDomains = selectedDomains,
                onDomainToggle = { domain ->
                    selectedDomains = if (domain in selectedDomains)
                        selectedDomains - domain
                    else
                        selectedDomains + domain
                }
            )
        }
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
                            onClick = {}
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
}
