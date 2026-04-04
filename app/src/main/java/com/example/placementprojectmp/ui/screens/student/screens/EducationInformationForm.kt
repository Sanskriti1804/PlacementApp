package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.AcademicGapSection
import com.example.placementprojectmp.ui.components.BacklogCounter
import com.example.placementprojectmp.ui.components.BacklogSubjectFields
import com.example.placementprojectmp.ui.components.CourseYearSelector
import com.example.placementprojectmp.ui.components.FormField
import com.example.placementprojectmp.ui.components.ToggleRow

/**
 * Education Form: university, course, year selector, percentages, divider, backlogs toggle, academic gap toggle.
 * Shown when Education tab is selected; lives inside the form container in LazyColumn.
 */
@Composable
fun EducationInformationForm(
    modifier: Modifier = Modifier
) {
    var university by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf<Int?>(null) }
    var class12Percent by remember { mutableStateOf("") }
    var class10Percent by remember { mutableStateOf("") }
    var activeBacklogsEnabled by remember { mutableStateOf(false) }
    var backlogCount by remember { mutableStateOf(1) }
    val backlogSubjects = remember { mutableStateListOf<String>().apply { add("") } }
    var academicGapEnabled by remember { mutableStateOf(false) }
    var gapYears by remember { mutableStateOf("") }
    var gapExplanation by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        FormField(
            label = "University",
            value = university,
            onValueChange = { university = it },
            placeholder = "University or college name"
        )
        FormField(
            label = "Course",
            value = course,
            onValueChange = { course = it },
            placeholder = "e.g. BCA, MCA, BTech, MTech, BBA, MBA"
        )
        CourseYearSelector(
            course = course,
            selectedYear = selectedYear,
            onYearSelected = { selectedYear = it }
        )
        FormField(
            label = "Class 12th Percentage",
            value = class12Percent,
            onValueChange = { class12Percent = it.filter { c -> c.isDigit() || c == '.' } },
            placeholder = "e.g. 85",
            keyboardType = KeyboardType.Decimal
        )
        FormField(
            label = "Class 10th Percentage",
            value = class10Percent,
            onValueChange = { class10Percent = it.filter { c -> c.isDigit() || c == '.' } },
            placeholder = "e.g. 90",
            keyboardType = KeyboardType.Decimal
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )

        ToggleRow(
            label = "Active Backlogs",
            checked = activeBacklogsEnabled,
            onCheckedChange = { activeBacklogsEnabled = it }
        )
        AnimatedVisibility(
            visible = activeBacklogsEnabled,
            enter = expandVertically(),
            exit = shrinkVertically(),
            label = "backlog_section"
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    BacklogCounter(
                        value = backlogCount,
                        onDecrement = {
                            if (backlogCount > 1) {
                                backlogCount--
                                if (backlogSubjects.size > backlogCount) backlogSubjects.removeAt(backlogSubjects.lastIndex)
                            }
                        },
                        onIncrement = {
                            backlogCount++
                            while (backlogSubjects.size < backlogCount) backlogSubjects.add("")
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(top = 4.dp)
                ) {
                    BacklogSubjectFields(
                        count = backlogCount,
                        values = backlogSubjects,
                        onValueChange = { index, value ->
                            while (backlogSubjects.size <= index) backlogSubjects.add("")
                            if (index < backlogSubjects.size) backlogSubjects[index] = value
                        }
                    )
                }
            }
        }

        ToggleRow(
            label = "Academic Gap",
            checked = academicGapEnabled,
            onCheckedChange = { academicGapEnabled = it }
        )
        AnimatedVisibility(
            visible = academicGapEnabled,
            enter = expandVertically(),
            exit = shrinkVertically(),
            label = "academic_gap_section"
        ) {
            AcademicGapSection(
                gapYears = gapYears,
                onGapYearsChange = { gapYears = it },
                explanation = gapExplanation,
                onExplanationChange = { gapExplanation = it },
                modifier = Modifier.animateContentSize()
            )
        }
    }
}
