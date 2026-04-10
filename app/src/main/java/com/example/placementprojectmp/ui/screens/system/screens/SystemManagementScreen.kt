package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.SearchBar
import com.example.placementprojectmp.ui.components.StudentViewMode
import com.example.placementprojectmp.ui.screens.staff.components.InfoRow
import com.example.placementprojectmp.ui.screens.staff.components.SectionHeader
import com.example.placementprojectmp.ui.screens.staff.components.StaffViewModeSelector

@Composable
fun SystemManagementScreen(
    modifier: Modifier = Modifier,
    onJobManagementClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var viewMode by remember { mutableStateOf(StudentViewMode.List) }
    var sortBy by remember { mutableStateOf("Name (A-Z)") }
    var selectedRoles by remember { mutableStateOf(setOf<String>()) }
    var selectedDepartments by remember { mutableStateOf(setOf<String>()) }
    var selectedCompanies by remember { mutableStateOf(setOf<String>()) }
    var selectedStatus by remember { mutableStateOf(setOf<String>()) }
    var selectedAssignment by remember { mutableStateOf(setOf<String>()) }
    var showAddSheet by remember { mutableStateOf(false) }
    var selectedIds by remember { mutableStateOf(setOf<String>()) }
    var showTagDialogFor by remember { mutableStateOf<String?>(null) }
    var showRoleDialogFor by remember { mutableStateOf<String?>(null) }
    var newTagText by remember { mutableStateOf("") }

    val users = remember {
        mutableStateOf(
            listOf(
                ManagedUser("u1", "Aarav Sharma", "Student", "CSE", "N/A", "Active", listOf("Priority"), listOf("Candidate"), assigned = true),
                ManagedUser("u2", "Priya Menon", "Staff", "Placement Cell", "N/A", "Active", listOf("Verified"), listOf("Drive Manager"), assigned = true),
                ManagedUser("u3", "Rohit Verma", "Recruiter", "Talent", "Nexora Systems", "Inactive", listOf("Top Recruiter"), listOf("Recruiter"), assigned = false),
                ManagedUser("u4", "Sneha Iyer", "HR", "Human Resources", "OrbitX", "Active", emptyList(), listOf("HR"), assigned = true),
                ManagedUser("u5", "Karthik Das", "Teacher", "ECE", "N/A", "Active", listOf("Needs Follow-up"), listOf("Placement Coordinator"), assigned = true)
            )
        )
    }
    val allRoleOptions = listOf("Student", "Staff", "Recruiter", "HR", "Teacher")
    val allDepartmentOptions = listOf("CSE", "ECE", "IT", "Placement Cell", "Talent", "Human Resources")
    val allCompanyOptions = listOf("Nexora Systems", "OrbitX", "Veltrix Labs")

    val filteredUsers = users.value
        .filter { user ->
            val q = searchQuery.trim()
            val queryMatch = q.isBlank() ||
                user.name.contains(q, true) ||
                user.role.contains(q, true) ||
                user.department.contains(q, true) ||
                user.company.contains(q, true)
            val roleMatch = selectedRoles.isEmpty() || user.role in selectedRoles
            val deptMatch = selectedDepartments.isEmpty() || user.department in selectedDepartments
            val companyMatch = selectedCompanies.isEmpty() || user.company in selectedCompanies
            val statusMatch = selectedStatus.isEmpty() || user.status in selectedStatus
            val assignmentMatch = selectedAssignment.isEmpty() ||
                (("Assigned" in selectedAssignment && user.assigned) || ("Not Assigned" in selectedAssignment && !user.assigned))
            queryMatch && roleMatch && deptMatch && companyMatch && statusMatch && assignmentMatch
        }
        .let { list ->
            when (sortBy) {
                "Recently Added" -> list.reversed()
                "Active First" -> list.sortedByDescending { it.status == "Active" }
                else -> list.sortedBy { it.name }
            }
        }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            SectionHeader("User Management")
                            Text(
                                text = "Manage students, staff, recruiters, HRs, and teachers",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        StaffViewModeSelector(
                            currentMode = viewMode,
                            onModeSelected = { viewMode = it }
                        )
                    }
                }
                item {
                    SearchBar(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        placeholder = "Search by name, role, department, company...",
                        query = searchQuery,
                        onQueryChange = { searchQuery = it }
                    )
                }
                item {
                    FilterRow(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        label = "Roles",
                        options = allRoleOptions,
                        selected = selectedRoles,
                        onToggle = { option ->
                            selectedRoles = if (option in selectedRoles) selectedRoles - option else selectedRoles + option
                        }
                    )
                }
                item {
                    FilterRow(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        label = "Departments",
                        options = allDepartmentOptions,
                        selected = selectedDepartments,
                        onToggle = { option ->
                            selectedDepartments = if (option in selectedDepartments) selectedDepartments - option else selectedDepartments + option
                        }
                    )
                }
                item {
                    FilterRow(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        label = "Company / Status / Assignment",
                        options = allCompanyOptions + listOf("Active", "Inactive", "Assigned", "Not Assigned"),
                        selected = selectedCompanies + selectedStatus + selectedAssignment,
                        onToggle = { option ->
                            when {
                                option in allCompanyOptions -> {
                                    selectedCompanies = if (option in selectedCompanies) selectedCompanies - option else selectedCompanies + option
                                }
                                option in listOf("Active", "Inactive") -> {
                                    selectedStatus = if (option in selectedStatus) selectedStatus - option else selectedStatus + option
                                }
                                else -> {
                                    selectedAssignment = if (option in selectedAssignment) selectedAssignment - option else selectedAssignment + option
                                }
                            }
                        }
                    )
                }
                item {
                    SortRow(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        selectedSort = sortBy,
                        onSortSelected = { sortBy = it }
                    )
                }

                item {
                    AnimatedContent(targetState = viewMode, transitionSpec = { fadeIn() togetherWith fadeOut() }, label = "system_user_mode") { mode ->
                        when (mode) {
                            StudentViewMode.Grid -> {
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    filteredUsers.chunked(2).forEach { row ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 20.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            row.forEach { user ->
                                                UserManagementCard(
                                                    modifier = Modifier.weight(1f),
                                                    user = user,
                                                    compact = true,
                                                    selected = user.id in selectedIds,
                                                    onToggleSelected = {
                                                        selectedIds = if (user.id in selectedIds) selectedIds - user.id else selectedIds + user.id
                                                    },
                                                    onToggleStatus = {
                                                        users.value = users.value.map {
                                                            if (it.id == user.id) it.copy(status = if (it.status == "Active") "Inactive" else "Active") else it
                                                        }
                                                    },
                                                    onAssignRole = { showRoleDialogFor = user.id },
                                                    onAssignDepartment = {
                                                        users.value = users.value.map { if (it.id == user.id) it.copy(department = "Placement Cell") else it }
                                                    },
                                                    onAssignCompany = {
                                                        users.value = users.value.map { if (it.id == user.id) it.copy(company = "Nexora Systems") else it }
                                                    },
                                                    onAddTag = { showTagDialogFor = user.id }
                                                )
                                            }
                                            repeat(2 - row.size) { Box(modifier = Modifier.weight(1f)) }
                                        }
                                    }
                                }
                            }
                            else -> {
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    filteredUsers.forEach { user ->
                                        UserManagementCard(
                                            modifier = Modifier.padding(horizontal = 20.dp),
                                            user = user,
                                            compact = false,
                                            selected = user.id in selectedIds,
                                            onToggleSelected = {
                                                selectedIds = if (user.id in selectedIds) selectedIds - user.id else selectedIds + user.id
                                            },
                                            onToggleStatus = {
                                                users.value = users.value.map {
                                                    if (it.id == user.id) it.copy(status = if (it.status == "Active") "Inactive" else "Active") else it
                                                }
                                            },
                                            onAssignRole = { showRoleDialogFor = user.id },
                                            onAssignDepartment = {
                                                users.value = users.value.map { if (it.id == user.id) it.copy(department = "Placement Cell") else it }
                                            },
                                            onAssignCompany = {
                                                users.value = users.value.map { if (it.id == user.id) it.copy(company = "Nexora Systems") else it }
                                            },
                                            onAddTag = { showTagDialogFor = user.id }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    OutlinedButton(
                        onClick = onJobManagementClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text("Open Job Management")
                    }
                }
            }
            if (selectedIds.isNotEmpty()) {
                BulkActionBar(
                    selectedCount = selectedIds.size,
                    onActivateAll = {
                        users.value = users.value.map { if (it.id in selectedIds) it.copy(status = "Active") else it }
                        selectedIds = emptySet()
                    },
                    onDeactivateAll = {
                        users.value = users.value.map { if (it.id in selectedIds) it.copy(status = "Inactive") else it }
                        selectedIds = emptySet()
                    },
                    onAssignRoleAll = {
                        users.value = users.value.map { if (it.id in selectedIds) it.copy(assignedRoles = (it.assignedRoles + "Drive Manager").distinct()) else it }
                        selectedIds = emptySet()
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { showAddSheet = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp),
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp, pressedElevation = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add user")
        }
    }

    if (showAddSheet) {
        ManagementFabSheet(
            onDismiss = { showAddSheet = false },
            onAddUser = {
                users.value = users.value + ManagedUser(
                    id = "u${users.value.size + 1}",
                    name = "New User ${users.value.size + 1}",
                    role = "Student",
                    department = "CSE",
                    company = "N/A",
                    status = "Active",
                    tags = emptyList(),
                    assignedRoles = listOf("Candidate"),
                    assigned = false
                )
                showAddSheet = false
            },
            onAssignRole = { showAddSheet = false },
            onBulkAction = { showAddSheet = false }
        )
    }

    showRoleDialogFor?.let { userId ->
        SelectionDialog(
            title = "Assign Role",
            options = listOf("Placement Coordinator", "Drive Manager", "Reviewer"),
            onDismiss = { showRoleDialogFor = null },
            onSelect = { role ->
                users.value = users.value.map {
                    if (it.id == userId) it.copy(assignedRoles = (it.assignedRoles + role).distinct(), assigned = true) else it
                }
                showRoleDialogFor = null
            }
        )
    }

    showTagDialogFor?.let { userId ->
        TagDialog(
            tagText = newTagText,
            onTagTextChange = { newTagText = it },
            onDismiss = {
                showTagDialogFor = null
                newTagText = ""
            },
            onApply = {
                if (newTagText.isNotBlank()) {
                    users.value = users.value.map {
                        if (it.id == userId) it.copy(tags = (it.tags + newTagText.trim()).distinct().take(3)) else it
                    }
                }
                showTagDialogFor = null
                newTagText = ""
            }
        )
    }
}

@Composable
private fun UserManagementCard(
    modifier: Modifier = Modifier,
    user: ManagedUser,
    compact: Boolean,
    selected: Boolean,
    onToggleSelected: () -> Unit,
    onToggleStatus: () -> Unit,
    onAssignRole: () -> Unit,
    onAssignDepartment: () -> Unit,
    onAssignCompany: () -> Unit,
    onAddTag: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(if (compact) 38.dp else 44.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Column {
                        Text(user.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                        Text(user.role, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(
                            if (user.status == "Active") Color(0xFF2E7D32).copy(alpha = 0.14f)
                            else MaterialTheme.colorScheme.error.copy(alpha = 0.14f)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = user.status,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (user.status == "Active") Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                    )
                }
            }

            InfoRow("Department", user.department)
            InfoRow("Company", user.company)
            InfoRow("Assignment", if (user.assigned) "Assigned" else "Not Assigned")
            if (!compact) {
                InfoRow("Roles", user.assignedRoles.joinToString(", "))
            }

            if (user.tags.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    user.tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(tag, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            ActionRow(
                onActivateDeactivate = onToggleStatus,
                onAssignRole = onAssignRole,
                onAssignDepartment = onAssignDepartment,
                onAssignCompany = onAssignCompany,
                onAddTag = onAddTag,
                onToggleSelected = onToggleSelected,
                active = user.status == "Active"
            )
        }
    }
}

@Composable
private fun ActionRow(
    onActivateDeactivate: () -> Unit,
    onAssignRole: () -> Unit,
    onAssignDepartment: () -> Unit,
    onAssignCompany: () -> Unit,
    onAddTag: () -> Unit,
    onToggleSelected: () -> Unit,
    active: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onActivateDeactivate) {
                Icon(
                    imageVector = if (active) Icons.Default.ToggleOff else Icons.Default.ToggleOn,
                    contentDescription = "Toggle",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(if (active) "Deactivate" else "Activate")
            }
            OutlinedButton(onClick = onAssignRole) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Assign", modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Assign Role")
            }
        }
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Assign Department") },
                    onClick = { expanded = false; onAssignDepartment() }
                )
                DropdownMenuItem(
                    text = { Text("Assign Company") },
                    onClick = { expanded = false; onAssignCompany() }
                )
                DropdownMenuItem(
                    text = { Text("Add Tag") },
                    onClick = { expanded = false; onAddTag() }
                )
                DropdownMenuItem(
                    text = { Text("Select for Bulk") },
                    onClick = { expanded = false; onToggleSelected() }
                )
            }
        }
    }
}

@Composable
private fun FilterRow(
    modifier: Modifier = Modifier,
    label: String,
    options: List<String>,
    selected: Set<String>,
    onToggle: (String) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            options.take(5).forEach { option ->
                FilterChip(
                    selected = option in selected,
                    onClick = { onToggle(option) },
                    label = { Text(option, style = MaterialTheme.typography.labelSmall) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}

@Composable
private fun SortRow(
    modifier: Modifier = Modifier,
    selectedSort: String,
    onSortSelected: (String) -> Unit
) {
    val options = listOf("Name (A-Z)", "Recently Added", "Active First")
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Sort By", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { option ->
                FilterChip(
                    selected = selectedSort == option,
                    onClick = { onSortSelected(option) },
                    label = { Text(option, style = MaterialTheme.typography.labelSmall) }
                )
            }
        }
    }
}

@Composable
private fun BulkActionBar(
    selectedCount: Int,
    onActivateAll: () -> Unit,
    onDeactivateAll: () -> Unit,
    onAssignRoleAll: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$selectedCount selected", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Activate", color = MaterialTheme.colorScheme.primary, modifier = Modifier.clickable(onClick = onActivateAll))
            Text("Deactivate", color = MaterialTheme.colorScheme.error, modifier = Modifier.clickable(onClick = onDeactivateAll))
            Text("Assign Role", color = MaterialTheme.colorScheme.primary, modifier = Modifier.clickable(onClick = onAssignRoleAll))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManagementFabSheet(
    onDismiss: () -> Unit,
    onAddUser: () -> Unit,
    onAssignRole: () -> Unit,
    onBulkAction: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = onAddUser, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.PersonAdd, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Add User")
            }
            Button(onClick = onAssignRole, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.CheckCircle, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Assign Role")
            }
            Button(onClick = onBulkAction, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Groups, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Bulk Actions")
            }
        }
    }
}

@Composable
private fun SelectionDialog(
    title: String,
    options: List<String>,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                options.forEach { option ->
                    OutlinedButton(onClick = { onSelect(option) }, modifier = Modifier.fillMaxWidth()) {
                        Text(option)
                    }
                }
                OutlinedButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
private fun TagDialog(
    tagText: String,
    onTagTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Add Tag", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                TextField(
                    value = tagText,
                    onValueChange = onTagTextChange,
                    placeholder = { Text("e.g. Priority, Verified, Top Recruiter") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) { Text("Cancel") }
                    Button(onClick = onApply, modifier = Modifier.weight(1f)) { Text("Apply") }
                }
            }
        }
    }
}

private data class ManagedUser(
    val id: String,
    val name: String,
    val role: String,
    val department: String,
    val company: String,
    val status: String,
    val tags: List<String>,
    val assignedRoles: List<String>,
    val assigned: Boolean
)
