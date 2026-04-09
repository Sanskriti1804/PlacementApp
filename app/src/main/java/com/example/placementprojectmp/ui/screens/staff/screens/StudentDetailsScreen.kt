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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Color
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
    var pendingApplicationStatuses by remember { mutableStateOf(selectedApplicationStatuses) }
    var pendingPassingYears by remember { mutableStateOf(selectedPassingYears) }
    var pendingBacklogStatuses by remember { mutableStateOf(selectedBacklogStatuses) }
    var pendingFavoritesOnly by remember { mutableStateOf(favoritesOnly) }
    var pendingCgpaPoint by remember { mutableStateOf(cgpaRange.endInclusive) }
    var showAddTagDialog by remember { mutableStateOf(false) }
    var newTagText by remember { mutableStateOf("") }
    var allTags by remember {
        mutableStateOf(
            listOf(
                "Top Performer" to Color(0xFF5E35B1),
                "Needs Follow-up" to Color(0xFFF4511E)
            )
        )
    }
    var selectedTagDraft by remember { mutableStateOf<Pair<String, Color>?>(null) }
    val tagsByStudent = remember { mutableMapOf<String, List<Pair<String, Color>>>() }
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
                    val searchExpand by androidx.compose.animation.core.animateFloatAsState(
                        targetValue = if (searchQuery.isNotEmpty()) 1.03f else 1f,
                        animationSpec = androidx.compose.animation.core.tween(220),
                        label = "staff_search_expand"
                    )
                    SearchBar(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .graphicsLayer {
                                scaleX = searchExpand
                                scaleY = searchExpand
                            },
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
                                tags = tagsByStudent[student.id].orEmpty(),
                                selected = student.id in selectedIds,
                                isFavorite = student.id in favoriteIds,
                                onSelectionChange = { checked ->
                                    if (checked) selectedIds.add(student.id) else selectedIds.remove(student.id)
                                },
                                onFavoriteToggle = {
                                    if (student.id in favoriteIds) favoriteIds.remove(student.id) else favoriteIds.add(student.id)
                                },
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
                                        tags = tagsByStudent[student.id].orEmpty(),
                                        selected = student.id in selectedIds,
                                        isFavorite = student.id in favoriteIds,
                                        onSelectionChange = { checked ->
                                            if (checked) selectedIds.add(student.id) else selectedIds.remove(student.id)
                                        },
                                        onFavoriteToggle = {
                                            if (student.id in favoriteIds) favoriteIds.remove(student.id) else favoriteIds.add(student.id)
                                        },
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
                onAddTag = {
                    selectedTagDraft = null
                    newTagText = ""
                    showAddTagDialog = true
                },
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
                favoritesOnly = pendingFavoritesOnly,
                onFavoritesOnlyChange = { pendingFavoritesOnly = it },
                selectedStatuses = pendingApplicationStatuses,
                onStatusesChange = { pendingApplicationStatuses = it },
                selectedPassingYears = pendingPassingYears,
                onPassingYearsChange = { pendingPassingYears = it },
                selectedBacklogs = pendingBacklogStatuses,
                onBacklogsChange = { pendingBacklogStatuses = it },
                cgpaRange = (cgpaRange.start..pendingCgpaPoint),
                onCgpaRangeChange = { pendingCgpaPoint = it.endInclusive },
                onApply = {
                    favoritesOnly = pendingFavoritesOnly
                    selectedApplicationStatuses = pendingApplicationStatuses
                    selectedPassingYears = pendingPassingYears
                    selectedBacklogStatuses = pendingBacklogStatuses
                    cgpaRange = cgpaRange.start..pendingCgpaPoint
                    currentPage = 1
                    showAdvancedFilters = false
                },
                onReset = {
                    pendingFavoritesOnly = false
                    pendingApplicationStatuses = emptySet()
                    pendingPassingYears = emptySet()
                    pendingBacklogStatuses = emptySet()
                    pendingCgpaPoint = 10f
                },
                onDismiss = { showAdvancedFilters = false }
            )
        }
        if (showAddTagDialog) {
            AddTagDialog(
                currentTags = allTags,
                newTagText = newTagText,
                onNewTagTextChange = { newTagText = it },
                selectedTag = selectedTagDraft,
                onSelectTag = { selectedTagDraft = it },
                onDismiss = { showAddTagDialog = false },
                onAddNewTag = {
                    if (allTags.size >= 6) {
                        scope.launch { snackbarHostState.showSnackbar("Cannot make any more tags") }
                    } else if (newTagText.isNotBlank()) {
                        val palette = listOf(
                            Color(0xFF5E35B1),
                            Color(0xFFD81B60),
                            Color(0xFF00897B),
                            Color(0xFF1E88E5),
                            Color(0xFFF4511E),
                            Color(0xFF6D4C41)
                        )
                        val color = palette[allTags.size % palette.size]
                        val newTag = newTagText.trim() to color
                        allTags = allTags + newTag
                        selectedTagDraft = newTag
                        newTagText = ""
                    }
                },
                onApply = {
                    selectedTagDraft?.let { tag ->
                        selectedIds.forEach { id ->
                            val current = tagsByStudent[id].orEmpty()
                            val updated = (current + tag).distinctBy { it.first }.take(3)
                            tagsByStudent[id] = updated
                        }
                    }
                    selectedIds.clear()
                    showAddTagDialog = false
                }
            )
        }

        NeonGlassToastHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
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
    onApply: () -> Unit = {},
    onReset: () -> Unit = {},
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var selectedSortBy by remember { mutableStateOf("Highest Package") }
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Filter Student Profile",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = "Favorite Student",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
            FilterChip(
                selected = favoritesOnly,
                onClick = { onFavoritesOnlyChange(!favoritesOnly) },
                label = { Text("Favorite Students", style = MaterialTheme.typography.labelMedium) },
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
            Text(
                text = "Sort By",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Highest Package", "Name (A → Z)", "Package", "CGPA").forEach { option ->
                    SortOptionBox(
                        modifier = Modifier.weight(1f),
                        label = option,
                        selected = selectedSortBy == option,
                        onClick = { selectedSortBy = option }
                    )
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
            AdvancedChipGroup(
                label = "Status",
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
                options = listOf("Active Backlogs", "Cleared Backlogs"),
                selected = selectedBacklogs,
                onChange = onBacklogsChange
            )
            Text(
                text = "CGPA Range: ${"%.1f".format(cgpaRange.start)} - ${"%.1f".format(cgpaRange.endInclusive)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Slider(
                value = cgpaRange.endInclusive,
                onValueChange = { onCgpaRangeChange(cgpaRange.start..it) },
                valueRange = 0f..10f,
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onReset,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) { Text("Reset") }
                Button(
                    onClick = onApply,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) { Text("Apply") }
            }
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
        if (label.isNotBlank()) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
        }
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
                            shape = RoundedCornerShape(100.dp),
                            label = {
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
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
private fun AddTagDialog(
    currentTags: List<Pair<String, Color>>,
    newTagText: String,
    onNewTagTextChange: (String) -> Unit,
    selectedTag: Pair<String, Color>?,
    onSelectTag: (Pair<String, Color>) -> Unit,
    onDismiss: () -> Unit,
    onAddNewTag: () -> Unit,
    onApply: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = NeonBlue.copy(alpha = 0.18f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Add Tag", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                OutlinedTextField(
                    value = newTagText,
                    onValueChange = onNewTagTextChange,
                    placeholder = { Text("Enter custom tag") },
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable(onClick = onAddNewTag),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add tag",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    currentTags.take(6).forEach { tag ->
                        val selected = selectedTag?.first == tag.first
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selected) tag.second.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface)
                                .border(1.dp, tag.second, RoundedCornerShape(12.dp))
                                .clickable { onSelectTag(tag) }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = tag.first,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) tag.second else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss, shape = RoundedCornerShape(10.dp)) { Text("Cancel") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onApply, shape = RoundedCornerShape(10.dp)) { Text("Apply") }
                }
            }
        }
    }
}

@Composable
private fun SortOptionBox(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            )
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        )
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
