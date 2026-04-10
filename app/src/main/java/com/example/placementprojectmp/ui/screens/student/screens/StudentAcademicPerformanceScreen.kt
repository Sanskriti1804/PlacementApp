package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.ui.components.PerformanceMetricCard
import com.example.placementprojectmp.ui.components.SemesterSection
import com.example.placementprojectmp.ui.components.SemesterSelector
import com.example.placementprojectmp.ui.components.SubjectCard

/** Dummy subject data for one semester. */
private data class SubjectData(
    val name: String,
    val professor: String,
    val displayValue: String,
    val details: List<Pair<String, String>>
)

/** Dummy semester data. */
private fun dummySemesterData(semesterTitle: String): List<SubjectData> {
    val professors = listOf("Prof. Sharma", "Prof. Mehta", "Prof. Gupta")
    val subjects = listOf(
        "Data Structures" to 88,
        "Operating Systems" to 82,
        "Database Systems" to 90,
        "Computer Networks" to 85,
        "Software Engineering" to 87
    )
    return subjects.mapIndexed { i, (name, pct) ->
        SubjectData(
            name = name,
            professor = professors[i % professors.size],
            displayValue = "$pct%",
            details = listOf(
                "CI 1" to "18 / 20",
                "CI 2" to "17 / 20",
                "CI 3" to "19 / 20",
                "Midterm" to "36 / 40",
                "Mock Test" to "28 / 30",
                "Assignment" to "10 / 10",
                "Attendance" to "92%"
            )
        )
    }
}

@Composable
fun AcademicPerdormanceScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    var selectedSemesterIndex by remember { mutableStateOf(0) }
    var expandedSubjectKey by remember { mutableStateOf<String?>(null) }
    val semesterOptions = listOf("All", "Semester 1", "Semester 2", "Semester 3", "Semester 4")

    val semestersToShow = if (selectedSemesterIndex == 0) {
        listOf(1, 2, 3, 4)
    } else {
        listOf(selectedSemesterIndex)
    }

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PerformanceMetricCard(
                    modifier = Modifier.weight(0.4f),
                    percentage = "85%",
                    cgpa = "8.6",
                    grade = "A+"
                )
                SemesterSelector(
                    modifier = Modifier.weight(0.6f),
                    options = semesterOptions,
                    selectedIndex = selectedSemesterIndex,
                    onSelect = { selectedSemesterIndex = it }
                )
            }
        }
        semestersToShow.forEach { semIndex ->
            val title = "Semester $semIndex"
            val subjects = dummySemesterData(title)
            item {
                SemesterSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = title
                ) {
                    subjects.forEach { sub ->
                        val key = "${title}_${sub.name}"
                        SubjectCard(
                            modifier = Modifier.fillMaxWidth(),
                            subjectName = sub.name,
                            professorName = sub.professor,
                            displayValue = sub.displayValue,
                            details = sub.details,
                            expanded = expandedSubjectKey == key,
                            onToggle = {
                                expandedSubjectKey = if (expandedSubjectKey == key) null else key
                            }
                        )
                    }
                }
            }
        }
    }
}
