package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.staff.components.InfoRow
import com.example.placementprojectmp.ui.screens.staff.components.SectionHeader

@Composable
fun SystemManagementScreen(
    modifier: Modifier = Modifier,
    onJobManagementClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { SectionHeader("System Management") }
        item {
            ManagementItemCard(
                title = "User Management",
                subtitle = "Students, Staff, and role assignments"
            )
        }
        item {
            ManagementItemCard(
                title = "Company Management",
                subtitle = "Company profile status and ownership"
            )
        }
        item {
            ManagementItemCard(
                title = "Drive Management",
                subtitle = "Schedule, status, and drive actions"
            )
        }
        item {
            ManagementItemCard(
                title = "Job Management",
                subtitle = "All jobs across the platform",
                onClick = onJobManagementClick
            )
        }
    }
}

@Composable
private fun ManagementItemCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ActionMenu()
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Open",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            InfoRow(label = "Description", value = subtitle)
        }
    }
}

@Composable
private fun ActionMenu() {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Actions"
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("View") }, onClick = { expanded = false })
            DropdownMenuItem(text = { Text("Edit") }, onClick = { expanded = false })
            DropdownMenuItem(text = { Text("Archive") }, onClick = { expanded = false })
        }
    }
}
