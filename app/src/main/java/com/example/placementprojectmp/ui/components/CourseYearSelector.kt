package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Returns the number of year options for the given course (dummy mapping).
 * BCA→3, MCA→2, BTech→4, MTech→2, BBA→3, MBA→2; default 4.
 */
fun yearsForCourse(course: String): Int {
    val normalized = course.trim().lowercase()
    return when {
        normalized == "bca" -> 3
        normalized == "mca" -> 2
        normalized == "btech" -> 4
        normalized == "mtech" -> 2
        normalized == "bba" -> 3
        normalized == "mba" -> 2
        else -> 4
    }
}

/**
 * Current Year selector: label + horizontal row of YearCards.
 * Number of years updates dynamically based on selected course.
 */
@Composable
fun CourseYearSelector(
    course: String,
    selectedYear: Int?,
    onYearSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val yearCount = yearsForCourse(course)
    val years = (1..yearCount).toList()

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Current Year *",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        AnimatedContent(
            targetState = yearCount,
            transitionSpec = {
                fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(220))
            },
            label = "year_selector_content"
        ) { count ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                (1..count).forEach { year ->
                    YearCard(
                        year = year,
                        selected = selectedYear == year,
                        onClick = { onYearSelected(year) }
                    )
                }
            }
        }
    }
}
