package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Experience form content: Work Experience toggle; when ON, job entry cards + Add Another Experience.
 * Used only inside Student Profile Form when the Experience tab is selected (no separate screen).
 */

private data class JobEntryState(
    val companyName: String,
    val jobType: JobType?,
    val location: String,
    val fromDay: String,
    val fromMonth: String,
    val fromYear: String,
    val toDay: String,
    val toMonth: String,
    val toYear: String,
    val roleDescription: String
) {
    companion object {
        fun empty() = JobEntryState("", null, "", "", "", "", "", "", "", "")
    }
}

@Composable
fun ExperienceFormContent(
    modifier: Modifier = Modifier
) {
    var hasWorkExperience by remember { mutableStateOf(false) }
    val jobEntries = remember { mutableStateListOf<JobEntryState>() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ToggleRow(
            label = "Work Experience",
            checked = hasWorkExperience,
            onCheckedChange = { enabled ->
                hasWorkExperience = enabled
                if (enabled && jobEntries.isEmpty()) {
                    jobEntries.add(JobEntryState.empty())
                }
            }
        )

        AnimatedVisibility(
            visible = hasWorkExperience,
            enter = expandVertically(),
            exit = shrinkVertically(),
            label = "experience_section"
        ) {
            Column(
                modifier = Modifier.animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                jobEntries.forEachIndexed { index, entry ->
                    JobExperienceCard(
                        jobIndex = index + 1,
                        companyName = entry.companyName,
                        onCompanyNameChange = { jobEntries[index] = entry.copy(companyName = it) },
                        jobType = entry.jobType,
                        onJobTypeChange = { jobEntries[index] = entry.copy(jobType = it) },
                        location = entry.location,
                        onLocationChange = { jobEntries[index] = entry.copy(location = it) },
                        fromDay = entry.fromDay,
                        fromMonth = entry.fromMonth,
                        fromYear = entry.fromYear,
                        onFromDateChange = { d, m, y ->
                            jobEntries[index] = entry.copy(fromDay = d, fromMonth = m, fromYear = y)
                        },
                        toDay = entry.toDay,
                        toMonth = entry.toMonth,
                        toYear = entry.toYear,
                        onToDateChange = { d, m, y ->
                            jobEntries[index] = entry.copy(toDay = d, toMonth = m, toYear = y)
                        },
                        roleDescription = entry.roleDescription,
                        onRoleDescriptionChange = { jobEntries[index] = entry.copy(roleDescription = it) }
                    )
                }
                AddExperienceButton(
                    onClick = { jobEntries.add(JobEntryState.empty()) }
                )
            }
        }
    }
}
