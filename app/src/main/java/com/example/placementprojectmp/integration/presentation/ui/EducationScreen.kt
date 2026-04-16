package com.example.placementprojectmp.integration.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.placementprojectmp.integration.presentation.viewmodel.EducationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EducationScreen(
    studentId: Long,
    modifier: Modifier = Modifier
) {
    val vm: EducationViewModel = koinViewModel()
    val state by vm.ui.collectAsStateWithLifecycle()
    var backlogSubject by remember { mutableStateOf("") }
    var backlogSemester by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Field("University", state.university) { vm.patch { copy(university = it) } }
        Field("Branch", state.branch) { vm.patch { copy(branch = it) } }
        Field("Course", state.course) { vm.patch { copy(course = it) } }
        Field("Domain", state.domain) { vm.patch { copy(domain = it) } }
        Field("Current Year", state.currentYear) { vm.patch { copy(currentYear = it) } }
        Field("10th %", state.tenthPercentage) { vm.patch { copy(tenthPercentage = it) } }
        Field("12th %", state.twelfthPercentage) { vm.patch { copy(twelfthPercentage = it) } }
        Field("Current CGPA", state.currentCgpa) { vm.patch { copy(currentCgpa = it) } }
        Field("Gap Years", state.gapYears) { vm.patch { copy(gapYears = it) } }
        Field("Gap Reason", state.gapReason) { vm.patch { copy(gapReason = it) } }
        Field("10th School Name", state.tenthSchoolName) { vm.patch { copy(tenthSchoolName = it) } }
        Field("12th School Name", state.twelfthSchoolName) { vm.patch { copy(twelfthSchoolName = it) } }
        Field("Graduation College Name", state.graduationCollegeName) { vm.patch { copy(graduationCollegeName = it) } }
        Field("Post Graduation College Name", state.postGraduationCollegeName) { vm.patch { copy(postGraduationCollegeName = it) } }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = backlogSubject,
                onValueChange = { backlogSubject = it },
                label = { Text("Backlog Subject") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = backlogSemester,
                onValueChange = { backlogSemester = it },
                label = { Text("Semester") },
                modifier = Modifier.weight(1f)
            )
        }
        Button(onClick = {
            vm.addBacklog(backlogSubject, backlogSemester.toIntOrNull() ?: return@Button)
            backlogSubject = ""
            backlogSemester = ""
        }) { Text("Add Backlog") }

        state.backlogs.forEachIndexed { index, item ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${item.subject} - Sem ${item.semester}")
                Button(onClick = { vm.removeBacklog(index) }) { Text("Remove") }
            }
        }

        state.error?.let { Text(it) }
        Button(
            onClick = { vm.submit(studentId) },
            enabled = !state.loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.loading) "Saving..." else "Save Education")
        }
    }
}

@Composable
private fun Field(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

