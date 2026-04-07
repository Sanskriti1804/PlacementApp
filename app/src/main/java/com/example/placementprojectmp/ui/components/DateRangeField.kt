package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Date range field with vertically stacked full-width From/To selectors.
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
        SegmentedDateInput(
            day = fromDay,
            month = fromMonth,
            year = fromYear,
            onDayChange = onFromDayChange,
            onMonthChange = onFromMonthChange,
            onYearChange = onFromYearChange,
            modifier = Modifier.fillMaxWidth()
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
