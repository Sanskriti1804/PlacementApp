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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.screens.staff.StaffStudentPortraitIds
import com.example.placementprojectmp.ui.components.BulkActionBar
import com.example.placementprojectmp.ui.components.FilterCapsule
import com.example.placementprojectmp.ui.components.PaginationControls
import com.example.placementprojectmp.ui.components.StudentCard
import com.example.placementprojectmp.ui.components.StudentViewMode
import com.example.placementprojectmp.ui.components.ViewModeSelector

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
    var viewMode by remember { mutableStateOf(StudentViewMode.List) }
    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var selectedStream by remember { mutableStateOf<String?>(null) }
    var selectedDomain by remember { mutableStateOf<String?>(null) }
    val students = remember { dummyStudents() }
    val selectedIds = remember { mutableStateListOf<String>() }
    val favoriteIds = remember { mutableStateListOf<String>() }
    var currentPage by remember { mutableStateOf(1) }
    val totalPages = 28
    val pageSize = 10
    val startIndex = (currentPage - 1) * pageSize
    val paginatedStudents = students.drop(startIndex).take(pageSize).ifEmpty { students }
    val portraitByStudentId = remember(students) {
        students.associate { it.id to StaffStudentPortraitIds.all.random() }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppTopBar(
            onMenuClick = onMenuClick,
            onNotificationClick = onNotificationClick
        )
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
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Students",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    ViewModeSelector(
                        currentMode = viewMode,
                        onModeSelected = { viewMode = it }
                    )
                }
            }
            item {
                FilterCapsule(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    selectedCourse = selectedCourse,
                    selectedStream = selectedStream,
                    selectedDomain = selectedDomain,
                    onCourseSelect = { selectedCourse = it },
                    onStreamSelect = { selectedStream = it },
                    onDomainSelect = { selectedDomain = it }
                )
            }
            when (viewMode) {
                StudentViewMode.List, StudentViewMode.Expanded -> {
                    itemsIndexed(paginatedStudents) { _, student ->
                        StudentCard(
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
                            viewMode = viewMode
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
                                StudentCard(
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
                                    viewMode = StudentViewMode.Grid
                                )
                            }
                            if (rowStudents.size == 1) {
                                Box(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
            item {
                PaginationControls(
                    modifier = Modifier.fillMaxWidth(),
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
            onDelete = { selectedIds.clear() },
            onFavorite = { selectedIds.forEach { id -> if (id !in favoriteIds) favoriteIds.add(id) }; selectedIds.clear() },
            onMove = { },
            onMore = { }
        )
    }
}
