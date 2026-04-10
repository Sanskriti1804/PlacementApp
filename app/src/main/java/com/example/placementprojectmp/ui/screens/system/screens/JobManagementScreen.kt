package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.JobCard
import com.example.placementprojectmp.ui.screens.staff.components.SectionHeader

@Composable
fun JobManagementScreen(
    modifier: Modifier = Modifier
) {
    var sortExpanded by remember { mutableStateOf(false) }
    var selectedSort by remember { mutableStateOf("Recent") }
    val selectedFilters = remember { mutableStateOf(setOf("Active")) }
    val jobs = listOf(
        Triple("Nexora Systems", "Android Engineer", "2 hours ago"),
        Triple("Veltrix Labs", "Backend Developer", "5 hours ago"),
        Triple("OrbitX", "QA Analyst", "1 day ago")
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { SectionHeader("Job Management") }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Active", "Closed", "Internship", "Full-time").forEach { filter ->
                    AssistChip(
                        onClick = {
                            selectedFilters.value = if (filter in selectedFilters.value) {
                                selectedFilters.value - filter
                            } else {
                                selectedFilters.value + filter
                            }
                        },
                        label = { Text(filter) }
                    )
                }
            }
        }
        item {
            Card(
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Sort: $selectedSort",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Change Sort",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .then(Modifier)
                            .let { it }
                    )
                    TextButton(onClick = { sortExpanded = true }) {
                        Text("Select sorting")
                    }
                    DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Recent") },
                            onClick = { selectedSort = "Recent"; sortExpanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Applications count") },
                            onClick = { selectedSort = "Applications count"; sortExpanded = false }
                        )
                    }
                }
            }
        }
        items(jobs) { (company, role, time) ->
            JobCard(
                companyName = company,
                roleTitle = role,
                timeAgo = time,
                onDismiss = {}
            )
        }
    }
}
