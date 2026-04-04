package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Row: From (SegmentedDateInput) → arrow → To (SegmentedDateInput).
 */
@Composable
fun DateRangeField(
    fromDay: String,
    fromMonth: String,
    fromYear: String,
    onFromDayChange: (String) -> Unit,
    onFromMonthChange: (String) -> Unit,
    onFromYearChange: (String) -> Unit,
    toDay: String,
    toMonth: String,
    toYear: String,
    onToDayChange: (String) -> Unit,
    onToMonthChange: (String) -> Unit,
    onToYearChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SegmentedDateInput(
            day = fromDay,
            month = fromMonth,
            year = fromYear,
            onDayChange = onFromDayChange,
            onMonthChange = onFromMonthChange,
            onYearChange = onFromYearChange,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
        SegmentedDateInput(
            day = toDay,
            month = toMonth,
            year = toYear,
            onDayChange = onToDayChange,
            onMonthChange = onToMonthChange,
            onYearChange = onToYearChange,
            modifier = Modifier.weight(1f)
        )
    }
}
