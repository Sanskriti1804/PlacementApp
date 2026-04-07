package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Date range field with stacked From/To date selectors.
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
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "From:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            SegmentedDateInput(
                day = fromDay,
                month = fromMonth,
                year = fromYear,
                onDayChange = onFromDayChange,
                onMonthChange = onFromMonthChange,
                onYearChange = onFromYearChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "To:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            SegmentedDateInput(
                day = toDay,
                month = toMonth,
                year = toYear,
                onDayChange = onToDayChange,
                onMonthChange = onToMonthChange,
                onYearChange = onToYearChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
