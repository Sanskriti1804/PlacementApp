package com.example.placementprojectmp.ui.screens.student.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.placementprojectmp.ui.components.SubjectHeader

@Composable
fun SubjectInforSelector(
    modifier: Modifier = Modifier,
    subjectName: String,
    teacher: Pair<String, String>,
    mentor: Pair<String, String>,
    subjects: List<String>,
    onSubjectSelected: (String) -> Unit
) {
    SubjectHeader(
        modifier = modifier,
        subjectName = subjectName,
        teacher = teacher,
        mentor = mentor,
        subjects = subjects,
        onSubjectSelected = onSubjectSelected
    )
}
