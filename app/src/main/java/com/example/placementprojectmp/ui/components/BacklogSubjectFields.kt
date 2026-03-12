package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Dynamic subject fields based on count: "Enter Subject 1", "Enter Subject 2", ...
 */
@Composable
fun BacklogSubjectFields(
    count: Int,
    values: List<String>,
    onValueChange: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        (1..count).forEach { index ->
            AnimatedVisibility(
                visible = true,
                enter = expandVertically(),
                exit = shrinkVertically(),
                label = "backlog_field_$index"
            ) {
                FormField(
                    label = "Enter Subject $index",
                    value = values.getOrElse(index - 1) { "" },
                    onValueChange = { onValueChange(index - 1, it) },
                    modifier = Modifier.padding(top = if (index == 1) 0.dp else 0.dp),
                    placeholder = "Subject name"
                )
            }
        }
    }
}
