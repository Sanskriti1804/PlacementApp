package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Row: left 20% Gap (Years) numeric field, right 80% Explain Academic Gap multiline.
 */
@Composable
fun AcademicGapSection(
    gapYears: String,
    onGapYearsChange: (String) -> Unit,
    explanation: String,
    onExplanationChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FormField(
            label = "Gap (Years)",
            value = gapYears,
            onValueChange = onGapYearsChange,
            modifier = Modifier
                .weight(0.2f)
                .padding(end = 4.dp),
            placeholder = "1",
            keyboardType = KeyboardType.Number
        )
        FormField(
            label = "Explain Academic Gap",
            value = explanation,
            onValueChange = onExplanationChange,
            modifier = Modifier.weight(0.8f),
            placeholder = "Explain the reason for the gap",
            singleLine = false
        )
    }
}
