package com.example.placementprojectmp.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.placementprojectmp.data.model.StudentDraft
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.personalDraftDataStore by preferencesDataStore(name = "student_personal_draft")

class StudentPersonalDraftStore(private val context: Context) {
    // Personal fields
    private val fullNameKey = stringPreferencesKey("full_name")
    private val usernameKey = stringPreferencesKey("username")
    private val emailKey = stringPreferencesKey("email")
    private val roleKey = stringPreferencesKey("role")
    private val phoneKey = stringPreferencesKey("phone")
    private val addressKey = stringPreferencesKey("address")
    private val cityKey = stringPreferencesKey("city")
    private val stateKey = stringPreferencesKey("state")
    private val countryKey = stringPreferencesKey("country")
    private val pinCodeKey = stringPreferencesKey("pin_code")
    private val dayKey = stringPreferencesKey("dob_day")
    private val monthKey = stringPreferencesKey("dob_month")
    private val yearKey = stringPreferencesKey("dob_year")
    private val profileImageUriKey = stringPreferencesKey("profile_image_uri")
    private val connectorLinksJsonKey = stringPreferencesKey("connector_links_json")

    // Education fields
    private val universityKey = stringPreferencesKey("university")
    private val courseKey = stringPreferencesKey("course")
    private val selectedYearKey = stringPreferencesKey("selected_year")
    private val class12PercentKey = stringPreferencesKey("class12_percent")
    private val school12NameKey = stringPreferencesKey("school12_name")
    private val passYear12Key = stringPreferencesKey("pass_year_12")
    private val class10PercentKey = stringPreferencesKey("class10_percent")
    private val school10NameKey = stringPreferencesKey("school10_name")
    private val passYear10Key = stringPreferencesKey("pass_year_10")
    private val gradCgpaKey = stringPreferencesKey("grad_cgpa")
    private val gradPassYearKey = stringPreferencesKey("grad_pass_year")
    private val activeBacklogsEnabledKey = booleanPreferencesKey("active_backlogs_enabled")
    private val backlogCountKey = intPreferencesKey("backlog_count")
    private val backlogSubjectsKey = stringPreferencesKey("backlog_subjects")
    private val academicGapEnabledKey = booleanPreferencesKey("academic_gap_enabled")
    private val gapYearsKey = stringPreferencesKey("gap_years")
    private val gapExplanationKey = stringPreferencesKey("gap_explanation")

    // Skills fields
    private val languagesSelectedKey = stringSetPreferencesKey("languages_selected")
    private val frameworksSelectedKey = stringSetPreferencesKey("frameworks_selected")
    private val toolsSelectedKey = stringSetPreferencesKey("tools_selected")
    private val softSkillsSelectedKey = stringSetPreferencesKey("soft_skills_selected")

    // Experience fields
    private val hasWorkExperienceKey = booleanPreferencesKey("has_work_experience")
    private val jobEntriesJsonKey = stringPreferencesKey("job_entries_json")

    // Projects fields
    private val projectNameKey = stringPreferencesKey("project_name")
    private val projectImageUriKey = stringPreferencesKey("project_image_uri")
    private val projectLinkKey = stringPreferencesKey("project_link")
    private val projectDescriptionKey = stringPreferencesKey("project_description")
    private val technologiesSelectedKey = stringSetPreferencesKey("technologies_selected")
    private val githubRepoKey = stringPreferencesKey("github_repo")
    private val liveDemoKey = stringPreferencesKey("live_demo")
    private val teamSizeKey = intPreferencesKey("team_size")
    private val teamMembersJsonKey = stringPreferencesKey("team_members_json")

    val draftFlow: Flow<StudentDraft> = context.personalDraftDataStore.data.map { prefs: Preferences ->
        StudentDraft(
            fullName = prefs[fullNameKey].orEmpty(),
            username = prefs[usernameKey].orEmpty(),
            email = prefs[emailKey].orEmpty(),
            role = prefs[roleKey].orEmpty(),
            phone = prefs[phoneKey].orEmpty(),
            address = prefs[addressKey].orEmpty(),
            city = prefs[cityKey].orEmpty(),
            state = prefs[stateKey].orEmpty(),
            country = prefs[countryKey].orEmpty(),
            pinCode = prefs[pinCodeKey].orEmpty(),
            day = prefs[dayKey].orEmpty(),
            month = prefs[monthKey].orEmpty(),
            year = prefs[yearKey].orEmpty(),
            profileImageUri = prefs[profileImageUriKey].orEmpty(),
            connectorLinksJson = prefs[connectorLinksJsonKey] ?: "{}",

            university = prefs[universityKey].orEmpty(),
            course = prefs[courseKey].orEmpty(),
            selectedYear = prefs[selectedYearKey].orEmpty(),
            class12Percent = prefs[class12PercentKey].orEmpty(),
            school12Name = prefs[school12NameKey].orEmpty(),
            passYear12 = prefs[passYear12Key].orEmpty(),
            class10Percent = prefs[class10PercentKey].orEmpty(),
            school10Name = prefs[school10NameKey].orEmpty(),
            passYear10 = prefs[passYear10Key].orEmpty(),
            gradCgpa = prefs[gradCgpaKey].orEmpty(),
            gradPassYear = prefs[gradPassYearKey].orEmpty(),
            activeBacklogsEnabled = prefs[activeBacklogsEnabledKey] ?: false,
            backlogCount = prefs[backlogCountKey] ?: 1,
            backlogSubjects = prefs[backlogSubjectsKey].orEmpty(),
            academicGapEnabled = prefs[academicGapEnabledKey] ?: false,
            gapYears = prefs[gapYearsKey].orEmpty(),
            gapExplanation = prefs[gapExplanationKey].orEmpty(),

            languagesSelected = prefs[languagesSelectedKey] ?: emptySet(),
            frameworksSelected = prefs[frameworksSelectedKey] ?: emptySet(),
            toolsSelected = prefs[toolsSelectedKey] ?: emptySet(),
            softSkillsSelected = prefs[softSkillsSelectedKey] ?: emptySet(),

            hasWorkExperience = prefs[hasWorkExperienceKey] ?: false,
            jobEntriesJson = prefs[jobEntriesJsonKey] ?: "[]",

            projectName = prefs[projectNameKey].orEmpty(),
            projectImageUri = prefs[projectImageUriKey].orEmpty(),
            projectLink = prefs[projectLinkKey].orEmpty(),
            projectDescription = prefs[projectDescriptionKey].orEmpty(),
            technologiesSelected = prefs[technologiesSelectedKey] ?: emptySet(),
            githubRepo = prefs[githubRepoKey].orEmpty(),
            liveDemo = prefs[liveDemoKey].orEmpty(),
            teamSize = prefs[teamSizeKey] ?: 0,
            teamMembersJson = prefs[teamMembersJsonKey] ?: "[]"
        )
    }

    suspend fun saveDraft(draft: StudentDraft) {
        context.personalDraftDataStore.edit { prefs ->
            prefs[fullNameKey] = draft.fullName
            prefs[usernameKey] = draft.username
            prefs[emailKey] = draft.email
            prefs[roleKey] = draft.role
            prefs[phoneKey] = draft.phone
            prefs[addressKey] = draft.address
            prefs[cityKey] = draft.city
            prefs[stateKey] = draft.state
            prefs[countryKey] = draft.country
            prefs[pinCodeKey] = draft.pinCode
            prefs[dayKey] = draft.day
            prefs[monthKey] = draft.month
            prefs[yearKey] = draft.year
            prefs[profileImageUriKey] = draft.profileImageUri
            prefs[connectorLinksJsonKey] = draft.connectorLinksJson

            prefs[universityKey] = draft.university
            prefs[courseKey] = draft.course
            prefs[selectedYearKey] = draft.selectedYear
            prefs[class12PercentKey] = draft.class12Percent
            prefs[school12NameKey] = draft.school12Name
            prefs[passYear12Key] = draft.passYear12
            prefs[class10PercentKey] = draft.class10Percent
            prefs[school10NameKey] = draft.school10Name
            prefs[passYear10Key] = draft.passYear10
            prefs[gradCgpaKey] = draft.gradCgpa
            prefs[gradPassYearKey] = draft.gradPassYear
            prefs[activeBacklogsEnabledKey] = draft.activeBacklogsEnabled
            prefs[backlogCountKey] = draft.backlogCount
            prefs[backlogSubjectsKey] = draft.backlogSubjects
            prefs[academicGapEnabledKey] = draft.academicGapEnabled
            prefs[gapYearsKey] = draft.gapYears
            prefs[gapExplanationKey] = draft.gapExplanation

            prefs[languagesSelectedKey] = draft.languagesSelected
            prefs[frameworksSelectedKey] = draft.frameworksSelected
            prefs[toolsSelectedKey] = draft.toolsSelected
            prefs[softSkillsSelectedKey] = draft.softSkillsSelected

            prefs[hasWorkExperienceKey] = draft.hasWorkExperience
            prefs[jobEntriesJsonKey] = draft.jobEntriesJson

            prefs[projectNameKey] = draft.projectName
            prefs[projectImageUriKey] = draft.projectImageUri
            prefs[projectLinkKey] = draft.projectLink
            prefs[projectDescriptionKey] = draft.projectDescription
            prefs[technologiesSelectedKey] = draft.technologiesSelected
            prefs[githubRepoKey] = draft.githubRepo
            prefs[liveDemoKey] = draft.liveDemo
            prefs[teamSizeKey] = draft.teamSize
            prefs[teamMembersJsonKey] = draft.teamMembersJson
        }
    }
}
