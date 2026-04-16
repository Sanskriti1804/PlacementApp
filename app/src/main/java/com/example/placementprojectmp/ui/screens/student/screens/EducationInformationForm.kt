package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

import androidx.compose.runtime.collectAsState
import com.example.placementprojectmp.viewmodel.StudentPersonalDraftViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Education Form: university, course, year selector, percentages, divider, backlogs toggle, academic gap toggle.
 * Shown when Education tab is selected; lives inside the form container in LazyColumn.
 */
@Composable
fun EducationInformationForm(
    modifier: Modifier = Modifier
) {
    val draftViewModel: StudentPersonalDraftViewModel = koinViewModel()
    val draft by draftViewModel.draft.collectAsState()

    var university by remember(draft.university) { mutableStateOf(draft.university) }
    var course by remember(draft.course) { mutableStateOf(draft.course) }
    var selectedYear by remember(draft.selectedYear) { mutableStateOf(draft.selectedYear.toIntOrNull()) }
    var class12Percent by remember(draft.class12Percent) { mutableStateOf(draft.class12Percent) }
    var class10Percent by remember(draft.class10Percent) { mutableStateOf(draft.class10Percent) }
    var school12Name by remember(draft.school12Name) { mutableStateOf(draft.school12Name) }
    var passYear12 by remember(draft.passYear12) { mutableStateOf(draft.passYear12) }
    var school10Name by remember(draft.school10Name) { mutableStateOf(draft.school10Name) }
    var passYear10 by remember(draft.passYear10) { mutableStateOf(draft.passYear10) }
    var gradCgpa by remember(draft.gradCgpa) { mutableStateOf(draft.gradCgpa) }
    var gradPassYear by remember(draft.gradPassYear) { mutableStateOf(draft.gradPassYear) }
    
    var activeBacklogsEnabled by remember(draft.activeBacklogsEnabled) { mutableStateOf(draft.activeBacklogsEnabled) }
    var backlogCount by remember(draft.backlogCount) { mutableStateOf(if (draft.backlogCount < 1) 1 else draft.backlogCount) }
    
    val initialBacklogs = draft.backlogSubjects.split(",").filter { it.isNotBlank() }
    val backlogSubjects = remember(draft.backlogSubjects) { 
        mutableStateListOf<String>().apply { 
            if (initialBacklogs.isEmpty()) add("") else addAll(initialBacklogs) 
        } 
    }
    
    var academicGapEnabled by remember(draft.academicGapEnabled) { mutableStateOf(draft.academicGapEnabled) }
    var gapYears by remember(draft.gapYears) { mutableStateOf(draft.gapYears) }
    var gapExplanation by remember(draft.gapExplanation) { mutableStateOf(draft.gapExplanation) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        androidx.compose.material3.Text("Class 10th Details", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        FormField(
            label = "Class 10th School Name",
            value = school10Name,
            onValueChange = { 
                school10Name = it 
                draftViewModel.updateSchool10Name(it)
            },
            placeholder = "Enter your school name"
        )
        androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FormField(
                modifier = Modifier.weight(1f),
                label = "Class 10th Percentage",
                value = class10Percent,
                onValueChange = { 
                    class10Percent = it.filter { c -> c.isDigit() || c == '.' } 
                    draftViewModel.updateClass10Percent(class10Percent)
                },
                placeholder = "e.g. 90",
                keyboardType = KeyboardType.Decimal
            )
            FormField(
                modifier = Modifier.weight(1f),
                label = "Passing Year (10th)",
                value = passYear10,
                onValueChange = { 
                    passYear10 = it.filter { c -> c.isDigit() } 
                    draftViewModel.updatePassYear10(passYear10)
                },
                placeholder = "YYYY",
                keyboardType = KeyboardType.Number
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

        androidx.compose.material3.Text("Class 12th Details", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        FormField(
            label = "Class 12th School Name",
            value = school12Name,
            onValueChange = { 
                school12Name = it 
                draftViewModel.updateSchool12Name(it)
            },
            placeholder = "Enter your school name"
        )
        androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FormField(
                modifier = Modifier.weight(1f),
                label = "Class 12th Percentage",
                value = class12Percent,
                onValueChange = { 
                    class12Percent = it.filter { c -> c.isDigit() || c == '.' } 
                    draftViewModel.updateClass12Percent(class12Percent)
                },
                placeholder = "e.g. 85",
                keyboardType = KeyboardType.Decimal
            )
            FormField(
                modifier = Modifier.weight(1f),
                label = "Passing Year (12th)",
                value = passYear12,
                onValueChange = { 
                    passYear12 = it.filter { c -> c.isDigit() } 
                    draftViewModel.updatePassYear12(passYear12)
                },
                placeholder = "YYYY",
                keyboardType = KeyboardType.Number
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

        androidx.compose.material3.Text("Graduation Details", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        FormField(
            label = "Graduation University Name",
            value = university,
            onValueChange = { 
                university = it 
                draftViewModel.updateUniversity(it)
            },
            placeholder = "University or college name"
        )
        FormField(
            label = "Course",
            value = course,
            onValueChange = { 
                course = it 
                draftViewModel.updateCourse(it)
            },
            placeholder = "e.g. BCA, MCA, BTech, MTech"
        )
        androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FormField(
                modifier = Modifier.weight(1f),
                label = "Enter CGPA",
                value = gradCgpa,
                onValueChange = { 
                    gradCgpa = it.filter { c -> c.isDigit() || c == '.' } 
                    draftViewModel.updateGradCgpa(gradCgpa)
                },
                placeholder = "e.g. 8.5",
                keyboardType = KeyboardType.Decimal
            )
            FormField(
                modifier = Modifier.weight(1f),
                label = "Passing Year (Graduation)",
                value = gradPassYear,
                onValueChange = { 
                    gradPassYear = it.filter { c -> c.isDigit() } 
                    draftViewModel.updateGradPassYear(gradPassYear)
                },
                placeholder = "YYYY",
                keyboardType = KeyboardType.Number
            )
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )

        ToggleRow(
            label = "Active Backlogs",
            checked = activeBacklogsEnabled,
            onCheckedChange = { 
                activeBacklogsEnabled = it 
                draftViewModel.updateActiveBacklogsEnabled(it)
            }
        )
        AnimatedVisibility(
            visible = activeBacklogsEnabled,
            enter = expandVertically(),
            exit = shrinkVertically(),
            label = "backlog_section"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                BacklogCounter(
                    value = backlogCount,
                    onDecrement = {
                        if (backlogCount > 1) {
                            backlogCount--
                            draftViewModel.updateBacklogCount(backlogCount)
                            if (backlogSubjects.size > backlogCount) backlogSubjects.removeAt(backlogSubjects.lastIndex)
                            draftViewModel.updateBacklogSubjects(backlogSubjects.joinToString(","))
                        }
                    },
                    onIncrement = {
                        backlogCount++
                        draftViewModel.updateBacklogCount(backlogCount)
                        while (backlogSubjects.size < backlogCount) backlogSubjects.add("")
                        draftViewModel.updateBacklogSubjects(backlogSubjects.joinToString(","))
                    }
                )
                BacklogSubjectFields(
                    count = backlogCount,
                    values = backlogSubjects,
                    onValueChange = { index, value ->
                        while (backlogSubjects.size <= index) backlogSubjects.add("")
                        if (index < backlogSubjects.size) backlogSubjects[index] = value
                        draftViewModel.updateBacklogSubjects(backlogSubjects.joinToString(","))
                    },
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        ToggleRow(
            label = "Academic Gap",
            checked = academicGapEnabled,
            onCheckedChange = { 
                academicGapEnabled = it 
                draftViewModel.updateAcademicGapEnabled(it)
            }
        )
        AnimatedVisibility(
            visible = academicGapEnabled,
            enter = expandVertically(),
            exit = shrinkVertically(),
            label = "academic_gap_section"
        ) {
            AcademicGapSection(
                gapYears = gapYears,
                onGapYearsChange = { 
                    gapYears = it 
                    draftViewModel.updateGapYears(it)
                },
                explanation = gapExplanation,
                onExplanationChange = { 
                    gapExplanation = it 
                    draftViewModel.updateGapExplanation(it)
                },
                modifier = Modifier.animateContentSize()
            )
        }
    }
}
