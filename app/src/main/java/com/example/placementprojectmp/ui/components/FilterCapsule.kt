package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Full-width capsule with filter dropdowns: Course, Stream, Domain.
 * Dummy options; onSelect callbacks for filtering.
 */
@Composable
fun FilterCapsule(
    modifier: Modifier = Modifier,
    courseOptions: List<String> = listOf("BCA", "MCA", "BBA", "MBA", "BTech", "MTech", "BAJMC"),
    streamOptions: List<String> = listOf(
        "Computer Science",
        "Marketing",
        "Finance",
        "Artificial Intelligence",
        "Data Science"
    ),
    domainOptions: List<String> = listOf(
        "Computer Science",
        "Marketing",
        "Finance",
        "Artificial Intelligence",
        "Data Science"
    ),
    selectedCourse: String? = null,
    selectedStream: String? = null,
    selectedDomain: String? = null,
    onCourseSelect: (String) -> Unit = {},
    onStreamSelect: (String) -> Unit = {},
    onDomainSelect: (String) -> Unit = {}
) {
    var courseExpanded by remember { mutableStateOf(false) }
    var streamExpanded by remember { mutableStateOf(false) }
    var domainExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { courseExpanded = true }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedCourse ?: "Course",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            DropdownMenu(expanded = courseExpanded, onDismissRequest = { courseExpanded = false }) {
                courseOptions.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface) },
                        onClick = { onCourseSelect(opt); courseExpanded = false }
                    )
                }
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { streamExpanded = true }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedStream ?: "Stream",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            DropdownMenu(expanded = streamExpanded, onDismissRequest = { streamExpanded = false }) {
                streamOptions.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface) },
                        onClick = { onStreamSelect(opt); streamExpanded = false }
                    )
                }
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { domainExpanded = true }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedDomain ?: "Domain",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            DropdownMenu(expanded = domainExpanded, onDismissRequest = { domainExpanded = false }) {
                domainOptions.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface) },
                        onClick = { onDomainSelect(opt); domainExpanded = false }
                    )
                }
            }
        }
    }
}
