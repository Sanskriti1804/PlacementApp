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
import androidx.compose.runtime.collectAsState
import com.example.placementprojectmp.viewmodel.StudentPersonalDraftViewModel
import org.koin.androidx.compose.koinViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import com.example.placementprojectmp.data.model.JobEntryDraft


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
    val draftViewModel: StudentPersonalDraftViewModel = koinViewModel()
    val draft by draftViewModel.draft.collectAsState()

    var hasWorkExperience by remember(draft.hasWorkExperience) { mutableStateOf(draft.hasWorkExperience) }

    val initialJobEntries = try {
        if (draft.jobEntriesJson.isNotBlank()) 
            Json.decodeFromString<List<JobEntryDraft>>(draft.jobEntriesJson).map { draftEntry ->
                JobEntryState(
                    companyName = draftEntry.companyName,
                    jobType = com.example.placementprojectmp.ui.components.JobType.entries.find { it.name == draftEntry.jobTypeTitle },
                    location = draftEntry.location,
                    fromDay = draftEntry.fromDay,
                    fromMonth = draftEntry.fromMonth,
                    fromYear = draftEntry.fromYear,
                    toDay = draftEntry.toDay,
                    toMonth = draftEntry.toMonth,
                    toYear = draftEntry.toYear,
                    roleDescription = draftEntry.roleDescription
                )
            }
        else emptyList()
    } catch (e: Exception) { emptyList() }

    val jobEntries = remember(draft.jobEntriesJson) { 
        mutableStateListOf<JobEntryState>().apply { addAll(initialJobEntries) } 
    }

    fun updateStore() {
        val drafts = jobEntries.map { state ->
            JobEntryDraft(
                companyName = state.companyName,
                jobTypeTitle = state.jobType?.name ?: "",
                location = state.location,
                fromDay = state.fromDay,
                fromMonth = state.fromMonth,
                fromYear = state.fromYear,
                toDay = state.toDay,
                toMonth = state.toMonth,
                toYear = state.toYear,
                roleDescription = state.roleDescription
            )
        }
        draftViewModel.updateJobEntriesJson(Json.encodeToString(drafts))
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ToggleRow(
            label = "Work Experience",
            checked = hasWorkExperience,
            onCheckedChange = { enabled ->
                hasWorkExperience = enabled
                draftViewModel.updateHasWorkExperience(enabled)
                if (enabled && jobEntries.isEmpty()) {
                    jobEntries.add(JobEntryState.empty())
                    updateStore()
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
                        onCompanyNameChange = { 
                            jobEntries[index] = entry.copy(companyName = it) 
                            updateStore()
                        },
                        jobType = entry.jobType,
                        onJobTypeChange = { 
                            jobEntries[index] = entry.copy(jobType = it) 
                            updateStore()
                        },
                        location = entry.location,
                        onLocationChange = { 
                            jobEntries[index] = entry.copy(location = it) 
                            updateStore()
                        },
                        fromDay = entry.fromDay,
                        fromMonth = entry.fromMonth,
                        fromYear = entry.fromYear,
                        onFromDateChange = { d, m, y ->
                            jobEntries[index] = entry.copy(fromDay = d, fromMonth = m, fromYear = y)
                            updateStore()
                        },
                        toDay = entry.toDay,
                        toMonth = entry.toMonth,
                        toYear = entry.toYear,
                        onToDateChange = { d, m, y ->
                            jobEntries[index] = entry.copy(toDay = d, toMonth = m, toYear = y)
                            updateStore()
                        },
                        roleDescription = entry.roleDescription,
                        onRoleDescriptionChange = { 
                            jobEntries[index] = entry.copy(roleDescription = it) 
                            updateStore()
                        }
                    )
                }
                AddExperienceButton(
                    onClick = { 
                        jobEntries.add(JobEntryState.empty()) 
                        updateStore()
                    }
                )
            }
        }
    }
}
