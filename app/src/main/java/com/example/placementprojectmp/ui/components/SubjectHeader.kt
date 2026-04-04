package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Subject header: subject name and faculty entries (Teacher, Mentor).
 * Clean typography; consistent with theme.
 */
@Composable
fun SubjectHeader(
    modifier: Modifier = Modifier,
    subjectName: String = "Data Structures",
    teacher: Pair<String, String> = "Prof. Sharma" to "sharma@college.edu",
    mentor: Pair<String, String> = "Prof. Mehta" to "mehta@college.edu"
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = subjectName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Teacher",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f)
        )
        FacultyInfoRow(
            name = teacher.first,
            email = teacher.second
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Mentor",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f)
        )
        FacultyInfoRow(
            name = mentor.first,
            email = mentor.second
        )
    }
}
