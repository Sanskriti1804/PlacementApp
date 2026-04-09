package com.example.placementprojectmp.ui.screens.staff.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.staff.StaffStudentPortraitIds
import com.example.placementprojectmp.ui.components.BulkActionBar
import com.example.placementprojectmp.ui.components.NeonGlassToastHost
import com.example.placementprojectmp.ui.components.StudentViewMode
import com.example.placementprojectmp.ui.screens.staff.components.StaffFilterCapsule
import com.example.placementprojectmp.ui.screens.staff.components.StaffPaginationControls
import com.example.placementprojectmp.ui.screens.staff.components.StaffStudentCardGrid
import com.example.placementprojectmp.ui.screens.staff.components.StaffStudentCardList
import com.example.placementprojectmp.ui.screens.staff.components.StaffViewModeSelector
import kotlinx.coroutines.launch

private data class StudentItem(
    val id: String,
    val name: String,
    val email: String
)

private fun dummyStudents(): List<StudentItem> = listOf(
    StudentItem("1", "Rahul Sharma", "rahul.sharma@email.com"),
    StudentItem("2", "Priya Mehta", "priya.mehta@email.com"),
    StudentItem("3", "Amit Kumar", "amit.kumar@email.com"),
    StudentItem("4", "Sneha Patel", "sneha.patel@email.com"),
    StudentItem("5", "Vikram Singh", "vikram.singh@email.com"),
    StudentItem("6", "Anjali Gupta", "anjali.gupta@email.com"),
    StudentItem("7", "Rohan Verma", "rohan.verma@email.com"),
    StudentItem("8", "Kavita Reddy", "kavita.reddy@email.com")
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
    var selectedBranch by remember { mutableStateOf<String?>(null) }
    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var selectedDomain by remember { mutableStateOf<String?>(null) }
    val students = remember { mutableStateListOf<StudentItem>().apply { addAll(dummyStudents()) } }
    val selectedIds = remember { mutableStateListOf<String>() }
    val favoriteIds = remember { mutableStateListOf<String>() }
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
    val paginatedStudents = filteredStudents.drop(startIndex).take(pageSize).ifEmpty { filteredStudents }

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
            BulkActionBar(
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
                onFavorite = { selectedIds.forEach { id -> if (id !in favoriteIds) favoriteIds.add(id) }; selectedIds.clear() },
                onMove = { },
                onMore = { }
            )
        }

        NeonGlassToastHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
