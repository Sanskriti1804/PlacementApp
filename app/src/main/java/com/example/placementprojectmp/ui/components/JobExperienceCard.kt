package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * One job entry card: Job N header, divider, company, job type, location, date range, role description.
 */
@Composable
fun JobExperienceCard(
    jobIndex: Int,
    companyName: String,
    onCompanyNameChange: (String) -> Unit,
    jobType: JobType?,
    onJobTypeChange: (JobType) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    fromDay: String,
    fromMonth: String,
    fromYear: String,
    onFromDateChange: (String, String, String) -> Unit,
    toDay: String,
    toMonth: String,
    toYear: String,
    onToDateChange: (String, String, String) -> Unit,
    roleDescription: String,
    onRoleDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Job $jobIndex",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
            FormField(
                label = "Company Name",
                value = companyName,
                onValueChange = onCompanyNameChange,
                placeholder = "Company or organization"
            )
            Column {
                Text(
                    text = "Job Type *",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                JobTypeChipGroup(
                    selected = jobType,
                    onSelect = onJobTypeChange
                )
            }
            FormField(
                label = "Location",
                value = location,
                onValueChange = onLocationChange,
                placeholder = "e.g. Remote, Delhi, Bangalore"
            )
            DateRangeField(
                fromDay = fromDay,
                fromMonth = fromMonth,
                fromYear = fromYear,
                onFromDayChange = { onFromDateChange(it, fromMonth, fromYear) },
                onFromMonthChange = { onFromDateChange(fromDay, it, fromYear) },
                onFromYearChange = { onFromDateChange(fromDay, fromMonth, it) },
                toDay = toDay,
                toMonth = toMonth,
                toYear = toYear,
                onToDayChange = { onToDateChange(it, toMonth, toYear) },
                onToMonthChange = { onToDateChange(toDay, it, toYear) },
                onToYearChange = { onToDateChange(toDay, toMonth, it) }
            )
            FormField(
                label = "Role Description",
                value = roleDescription,
                onValueChange = onRoleDescriptionChange,
                placeholder = "Responsibilities, technologies, achievements",
                singleLine = false
            )
        }
    }
}
