package com.example.placementprojectmp.ui.screens.staff.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.staff.StaffStudentPortraitIds
import com.example.placementprojectmp.ui.components.NeonGlassToastHost
import com.example.placementprojectmp.ui.components.SearchBar
import com.example.placementprojectmp.ui.components.StudentViewMode
import com.example.placementprojectmp.ui.theme.NeonBlue
import com.example.placementprojectmp.ui.screens.staff.components.StaffFilterCapsule
import com.example.placementprojectmp.ui.screens.staff.components.StaffPaginationControls
import com.example.placementprojectmp.ui.screens.staff.components.StaffStudentCardGrid
import com.example.placementprojectmp.ui.screens.staff.components.StaffStudentCardList
import com.example.placementprojectmp.ui.screens.staff.components.StaffViewModeSelector
import kotlinx.coroutines.launch

private data class StudentItem(
    val id: String,
    val name: String,
    val email: String,
    val rollNumber: String
)

private fun dummyStudents(): List<StudentItem> = listOf(
    StudentItem("1", "Rahul Sharma", "rahul.sharma@email.com", "CSE-BT-2101"),
    StudentItem("2", "Priya Mehta", "priya.mehta@email.com", "ECE-BT-2104"),
    StudentItem("3", "Amit Kumar", "amit.kumar@email.com", "ME-BT-2112"),
    StudentItem("4", "Sneha Patel", "sneha.patel@email.com", "CE-BT-2117"),
    StudentItem("5", "Vikram Singh", "vikram.singh@email.com", "EE-BT-2121"),
    StudentItem("6", "Anjali Gupta", "anjali.gupta@email.com", "CSE-MT-2203"),
    StudentItem("7", "Rohan Verma", "rohan.verma@email.com", "IT-BT-2108"),
    StudentItem("8", "Kavita Reddy", "kavita.reddy@email.com", "MCA-PG-2302")
)

@Composable
fun StudentDetailsScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var viewMode by remember { mutableStateOf(StudentViewMode.List) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedBranch by remember { mutableStateOf<String?>(null) }
    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var selectedDomain by remember { mutableStateOf<String?>(null) }
    var showAdvancedFilters by remember { mutableStateOf(false) }
    var moreMenuExpanded by remember { mutableStateOf(false) }
    val students = remember { mutableStateListOf<StudentItem>().apply { addAll(dummyStudents()) } }
    val selectedIds = remember { mutableStateListOf<String>() }
    val favoriteIds = remember { mutableStateListOf<String>() }
    var selectedApplicationStatuses by remember { mutableStateOf(setOf<String>()) }
    var selectedPassingYears by remember { mutableStateOf(setOf<String>()) }
    var selectedBacklogStatuses by remember { mutableStateOf(setOf<String>()) }
    var favoritesOnly by remember { mutableStateOf(false) }
    var cgpaRange by remember { mutableStateOf(5.0f..10.0f) }
    var currentPage by remember { mutableStateOf(1) }
    val totalPages = 28
    val pageSize = 10
    val startIndex = (currentPage - 1) * pageSize
    val portraitByStudentId = remember(students) {
        students.associate { it.id to StaffStudentPortraitIds.all.random() }
    }
    val branchOptions = remember { listOf("CSE", "ECE", "ME", "CE", "EE") }
    val courseOptions = remember { listOf("BTech", "MTech", "BCA", "MCA") }
    val domainOptions = remember { listOf("Android", "Web", "Backend", "AI/ML", "Data") }
    val metaByStudentId = remember(students) {
        students.associate { s ->
            s.id to Triple(
                branchOptions.random(),
                courseOptions.random(),
                domainOptions.random()
            )
        }
    }
    val filteredStudents = remember(students, selectedBranch, selectedCourse, selectedDomain, metaByStudentId) {
        students.filter { s ->
            val (b, c, d) = metaByStudentId.getValue(s.id)
            (selectedBranch == null || b == selectedBranch) &&
                (selectedCourse == null || c == selectedCourse) &&
                (selectedDomain == null || d == selectedDomain)
        }
    }
    val advancedFilteredStudents = remember(
        filteredStudents,
        favoritesOnly,
        favoriteIds,
        selectedApplicationStatuses,
        selectedPassingYears,
        selectedBacklogStatuses,
        cgpaRange
    ) {
        filteredStudents.filter { student ->
            val statusBucket = when ((student.id.toIntOrNull() ?: 1) % 6) {
                0 -> "Applied"
                1 -> "Not Applied"
                2 -> "Shortlisted"
                3 -> "Rejected"
                4 -> "Selected"
                else -> "Placed"
            }
            val passingYearBucket = when ((student.id.toIntOrNull() ?: 1) % 7) {
                0 -> "Before 2022"
                1 -> "2022"
                2 -> "2023"
                3 -> "2024"
                4 -> "2025"
                5 -> "2026"
                else -> "Ongoing"
            }
            val backlogBucket = when ((student.id.toIntOrNull() ?: 1) % 3) {
                0 -> "Active Backlogs"
                1 -> "No Backlogs"
                else -> "Cleared Backlogs"
            }
            val cgpa = 6.0f + ((student.id.toIntOrNull() ?: 1) % 5) * 0.8f

            val matchesFavorites = !favoritesOnly || student.id in favoriteIds
            val matchesStatus = selectedApplicationStatuses.isEmpty() || statusBucket in selectedApplicationStatuses
            val matchesPassingYear = selectedPassingYears.isEmpty() || passingYearBucket in selectedPassingYears
            val matchesBacklog = selectedBacklogStatuses.isEmpty() || backlogBucket in selectedBacklogStatuses
            val matchesCgpa = cgpa in cgpaRange.start..cgpaRange.endInclusive
            matchesFavorites && matchesStatus && matchesPassingYear && matchesBacklog && matchesCgpa
        }
    }
    val queryTrimmed = searchQuery.trim()
    val queryFilteredStudents = remember(advancedFilteredStudents, queryTrimmed) {
        if (queryTrimmed.isBlank()) advancedFilteredStudents
        else advancedFilteredStudents.filter {
            it.name.contains(queryTrimmed, ignoreCase = true) ||
                it.email.contains(queryTrimmed, ignoreCase = true) ||
                it.rollNumber.contains(queryTrimmed, ignoreCase = true)
        }
    }
    val paginatedStudents = queryFilteredStudents.drop(startIndex).take(pageSize).ifEmpty { queryFilteredStudents }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 80.dp),
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
                            androidx.compose.material3.Text(
                                text = "Student Details",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            androidx.compose.material3.Text(
                                text = "Manage student profile and actions",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
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
                        query = searchQuery,
                        placeholder = "Search by name, email or roll number...",
                        onQueryChange = {
                            searchQuery = it
                            currentPage = 1
                        },
                        onFilterClick = { showAdvancedFilters = true }
                    )
                }
                item {
                    StaffFilterCapsule(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        branchOptions = branchOptions,
                        courseOptions = courseOptions,
                        domainOptions = domainOptions,
                        selectedBranch = selectedBranch,
                        selectedCourse = selectedCourse,
                        selectedDomain = selectedDomain,
                        onBranchSelect = { selectedBranch = it; currentPage = 1 },
                        onCourseSelect = { selectedCourse = it; currentPage = 1 },
                        onDomainSelect = { selectedDomain = it; currentPage = 1 }
                    )
                }
                item {
                    AdvancedFilteringStrip(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        onClick = { showAdvancedFilters = true }
                    )
                }
                when (viewMode) {
                    StudentViewMode.List, StudentViewMode.Expanded -> {
                        itemsIndexed(paginatedStudents) { _, student ->
                            StaffStudentCardList(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                studentName = student.name,
                                studentEmail = student.email,
                                studentRollNumber = student.rollNumber,
                                searchQuery = queryTrimmed,
                                profileImageResId = portraitByStudentId.getValue(student.id),
                                selected = student.id in selectedIds,
                                isFavorite = student.id in favoriteIds,
                                onSelectionChange = { checked ->
                                    if (checked) selectedIds.add(student.id) else selectedIds.remove(student.id)
                                },
                                onFavoriteToggle = {
                                    if (student.id in favoriteIds) favoriteIds.remove(student.id) else favoriteIds.add(student.id)
                                },
                                onMenuClick = { }
                            )
                        }
                    }
                    StudentViewMode.Grid -> {
                        itemsIndexed(paginatedStudents.chunked(2)) { _, rowStudents ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowStudents.forEach { student ->
                                    StaffStudentCardGrid(
                                        modifier = Modifier.weight(1f),
                                        studentName = student.name,
                                        studentEmail = student.email,
                                        studentRollNumber = student.rollNumber,
                                        searchQuery = queryTrimmed,
                                        profileImageResId = portraitByStudentId.getValue(student.id),
                                        selected = student.id in selectedIds,
                                        isFavorite = student.id in favoriteIds,
                                        onSelectionChange = { checked ->
                                            if (checked) selectedIds.add(student.id) else selectedIds.remove(student.id)
                                        },
                                        onFavoriteToggle = {
                                            if (student.id in favoriteIds) favoriteIds.remove(student.id) else favoriteIds.add(student.id)
                                        },
                                        onMenuClick = { }
                                    )
                                }
                                repeat(2 - rowStudents.size) { Box(modifier = Modifier.weight(1f)) }
                            }
                        }
                    }
                }
                item {
                    StaffPaginationControls(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        currentPage = currentPage,
                        totalPages = totalPages,
                        onPageSelected = { currentPage = it }
                    )
                }
            }
            StaffBulkActionBar(
                modifier = Modifier.fillMaxWidth(),
                visible = selectedIds.isNotEmpty(),
                selectedCount = selectedIds.size,
                onDelete = {
                    val idsToDelete = selectedIds.toSet()
                    students.removeAll { it.id in idsToDelete }
                    favoriteIds.removeAll { it in idsToDelete }
                    selectedIds.clear()
                    scope.launch { snackbarHostState.showSnackbar("Student profile deleted") }
                },
                onFavorite = {
                    selectedIds.forEach { id -> if (id !in favoriteIds) favoriteIds.add(id) }
                    selectedIds.clear()
                },
                onAddTag = { selectedIds.clear() },
                onMoreClick = { moreMenuExpanded = true },
                moreMenuExpanded = moreMenuExpanded,
                onMoreDismiss = { moreMenuExpanded = false },
                onShareDetails = { moreMenuExpanded = false },
                onExportDetails = { moreMenuExpanded = false },
                onBulkEmail = { moreMenuExpanded = false },
                onBulkNotification = { moreMenuExpanded = false }
            )
        }

        if (showAdvancedFilters) {
            AdvancedFiltersBottomSheet(
                favoritesOnly = favoritesOnly,
                onFavoritesOnlyChange = { favoritesOnly = it; currentPage = 1 },
                selectedStatuses = selectedApplicationStatuses,
                onStatusesChange = { selectedApplicationStatuses = it; currentPage = 1 },
                selectedPassingYears = selectedPassingYears,
                onPassingYearsChange = { selectedPassingYears = it; currentPage = 1 },
                selectedBacklogs = selectedBacklogStatuses,
                onBacklogsChange = { selectedBacklogStatuses = it; currentPage = 1 },
                cgpaRange = cgpaRange,
                onCgpaRangeChange = { cgpaRange = it; currentPage = 1 },
                onDismiss = { showAdvancedFilters = false }
            )
        }

        NeonGlassToastHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun AdvancedFilteringStrip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Open advanced filters",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Advanced Filtering",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Open advanced filters",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdvancedFiltersBottomSheet(
    favoritesOnly: Boolean,
    onFavoritesOnlyChange: (Boolean) -> Unit,
    selectedStatuses: Set<String>,
    onStatusesChange: (Set<String>) -> Unit,
    selectedPassingYears: Set<String>,
    onPassingYearsChange: (Set<String>) -> Unit,
    selectedBacklogs: Set<String>,
    onBacklogsChange: (Set<String>) -> Unit,
    cgpaRange: ClosedFloatingPointRange<Float>,
    onCgpaRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Advanced Filters",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            FilterChip(
                selected = favoritesOnly,
                onClick = { onFavoritesOnlyChange(!favoritesOnly) },
                label = { Text("Favorite Students") },
                leadingIcon = {
                    Icon(
                        imageVector = if (favoritesOnly) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Favorite",
                        tint = if (favoritesOnly) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
            AdvancedChipGroup(
                label = "Application Status",
                options = listOf("Applied", "Not Applied", "Shortlisted", "Rejected", "Selected", "Placed"),
                selected = selectedStatuses,
                onChange = onStatusesChange
            )
            AdvancedChipGroup(
                label = "Passing Year",
                options = listOf("Before 2022", "2022", "2023", "2024", "2025", "2026", "Ongoing"),
                selected = selectedPassingYears,
                onChange = onPassingYearsChange
            )
            AdvancedChipGroup(
                label = "Backlog Status",
                options = listOf("Active Backlogs", "No Backlogs", "Cleared Backlogs"),
                selected = selectedBacklogs,
                onChange = onBacklogsChange
            )
            Text(
                text = "CGPA Range: ${"%.1f".format(cgpaRange.start)} - ${"%.1f".format(cgpaRange.endInclusive)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            RangeSlider(
                value = cgpaRange.start..cgpaRange.endInclusive,
                onValueChange = { onCgpaRangeChange(it.start..it.endInclusive) },
                valueRange = 0f..10f
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun AdvancedChipGroup(
    label: String,
    options: List<String>,
    selected: Set<String>,
    onChange: (Set<String>) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            options.chunked(3).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { option ->
                        val active = option in selected
                        FilterChip(
                            selected = active,
                            onClick = { onChange(if (active) selected - option else selected + option) },
                            label = { Text(option) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StaffBulkActionBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    selectedCount: Int,
    onDelete: () -> Unit,
    onFavorite: () -> Unit,
    onAddTag: () -> Unit,
    onMoreClick: () -> Unit,
    moreMenuExpanded: Boolean,
    onMoreDismiss: () -> Unit,
    onShareDetails: () -> Unit,
    onExportDetails: () -> Unit,
    onBulkEmail: () -> Unit,
    onBulkNotification: () -> Unit
) {
    if (!visible) return
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$selectedCount Selected",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Delete",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable(onClick = onDelete)
        )
        Text(
            text = "Favorite",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(onClick = onFavorite)
        )
        Text(
            text = "Add Tag",
            color = NeonBlue,
            modifier = Modifier.clickable(onClick = onAddTag)
        )
        Box {
            Text(
                text = "More",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = onMoreClick)
            )
            DropdownMenu(
                expanded = moreMenuExpanded,
                onDismissRequest = onMoreDismiss
            ) {
                DropdownMenuItem(text = { Text("Share Details") }, onClick = onShareDetails)
                DropdownMenuItem(text = { Text("Export Details") }, onClick = onExportDetails)
                DropdownMenuItem(text = { Text("Bulk Email") }, onClick = onBulkEmail)
                DropdownMenuItem(text = { Text("Bulk Notification") }, onClick = onBulkNotification)
            }
        }
    }
}
