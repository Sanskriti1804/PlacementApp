package com.example.placementprojectmp.ui.screens.student.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.CourseCarousel
import com.example.placementprojectmp.ui.components.DomainChipRow

fun LazyListScope.CourseDomainMappingFilter(
    courses: List<String>,
    courseDomains: List<String>,
    selectedDomains: Set<String>,
    isLoading: Boolean,
    onCourseClick: (String) -> Unit,
    onDomainToggle: (String) -> Unit
) {
    item {
        CourseCarousel(
            modifier = Modifier.padding(horizontal = 20.dp),
            courses = courses,
            onCourseClick = onCourseClick
        )
    }
    if (!isLoading && courses.isEmpty()) {
        item {
            Text(
                text = "Course information is unavailable right now.",
                modifier = Modifier.padding(horizontal = 20.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    item {
        DomainChipRow(
            modifier = Modifier.padding(horizontal = 20.dp),
            domains = courseDomains,
            selectedDomains = selectedDomains,
            onDomainToggle = onDomainToggle
        )
    }
    if (!isLoading && courseDomains.isEmpty()) {
        item {
            Text(
                text = "Select a course to view domains.",
                modifier = Modifier.padding(horizontal = 20.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
