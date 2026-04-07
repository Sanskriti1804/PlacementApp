package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.ResourceBottomSheet
import com.example.placementprojectmp.ui.components.ResourceFolderRow
import com.example.placementprojectmp.ui.components.SubjectHeader

/** Dummy PDF resources per folder: (fileName, approvedByEmail). */
private fun dummyResourcesForFolder(folder: String): List<Pair<String, String>> = when (folder) {
    "Notes" -> listOf(
        "DS_Notes_Unit1.pdf" to "approved@college.edu",
        "Trees_Concepts.pdf" to "mentor@college.edu",
        "Sorting_Algorithms.pdf" to "faculty@college.edu"
    )
    "PYQ" -> listOf(
        "PYQ_2023.pdf" to "approved@college.edu",
        "PYQ_2022.pdf" to "mentor@college.edu"
    )
    "Resources" -> listOf(
        "DataStructures_Unit1.pdf" to "faculty@college.edu",
        "Reference_Guide.pdf" to "approved@college.edu"
    )
    "Cheat Codes" -> listOf(
        "Quick_Reference.pdf" to "mentor@college.edu"
    )
    "Preparation Sheets" -> listOf(
        "Topic_Checklist.pdf" to "approved@college.edu",
        "Revision_Sheet.pdf" to "faculty@college.edu"
    )
    "Aptitude Test" -> emptyList()
    else -> listOf(
        "Sample.pdf" to "approved@college.edu"
    )
}

private data class SubjectInfo(
    val name: String,
    val teacher: Pair<String, String>,
    val mentor: Pair<String, String>
)

@Composable
fun PreparationScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigateToPyqQuestions: (String) -> Unit = {},
    onNavigateToAptitudeTestDetails: (String) -> Unit = {}
) {
    var selectedFolder by remember { mutableStateOf<String?>(null) }
    val subjectItems = remember {
        listOf(
            SubjectInfo("Data Structures", "Prof. Sharma" to "sharma@college.edu", "Prof. Mehta" to "mehta@college.edu"),
            SubjectInfo("Operating Systems", "Prof. Verma" to "verma@college.edu", "Prof. Rao" to "rao@college.edu"),
            SubjectInfo("DBMS", "Prof. Singh" to "singh@college.edu", "Prof. Kapoor" to "kapoor@college.edu"),
            SubjectInfo("Computer Networks", "Prof. Iyer" to "iyer@college.edu", "Prof. Das" to "das@college.edu"),
            SubjectInfo("System Design", "Prof. Nair" to "nair@college.edu", "Prof. Sen" to "sen@college.edu"),
            SubjectInfo("Java", "Prof. Gupta" to "gupta@college.edu", "Prof. Jain" to "jain@college.edu")
        )
    }
    var selectedSubject by remember { mutableStateOf(subjectItems.first()) }
    val glassBackground = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(glassBackground)
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
            SubjectHeader(
                modifier = Modifier.padding(horizontal = 20.dp),
                subjectName = selectedSubject.name,
                teacher = selectedSubject.teacher,
                mentor = selectedSubject.mentor,
                subjects = subjectItems.map { it.name },
                onSubjectSelected = { selected ->
                    selectedSubject = subjectItems.firstOrNull { it.name == selected } ?: selectedSubject
                }
            )
        }
        item {
            ResourceFolderRow(
                modifier = Modifier.padding(horizontal = 20.dp),
                folders = listOf("Notes", "PYQ", "Resources", "Cheat Codes", "Aptitude Test"),
                onFolderClick = { folder -> selectedFolder = folder }
            )
        }
    }

    selectedFolder?.let { folder ->
        ResourceBottomSheet(
            folderTitle = folder,
            resources = dummyResourcesForFolder(folder),
            onDismissRequest = { selectedFolder = null },
            onPyqCompanyClick = { company ->
                selectedFolder = null
                onNavigateToPyqQuestions(company)
            },
            onAptitudeTestClick = { testId ->
                selectedFolder = null
                onNavigateToAptitudeTestDetails(testId)
            }
        )
    }
}
