package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.student.screens.EducationInformationForm
import com.example.placementprojectmp.ui.screens.student.screens.PersonalInformationForm

/**
 * Form content area for profile form tabs.
 * Personal / Education / Skills / Experience / Projects show respective form content inline.
 */
@Composable
fun FormSection(
    selectedTab: ProfileFormTab,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = selectedTab,
        transitionSpec = {
            fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(220))
        },
        label = "form_section_transition"
    ) { tab ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.TopStart
        ) {
            when (tab) {
                ProfileFormTab.Personal -> PersonalInformationForm(modifier = Modifier.fillMaxWidth())
                ProfileFormTab.Education -> EducationInformationForm(modifier = Modifier.fillMaxWidth())
                ProfileFormTab.Skills -> SkillsFormContent(modifier = Modifier.fillMaxWidth())
                ProfileFormTab.Experience -> ExperienceFormContent(modifier = Modifier.fillMaxWidth())
                ProfileFormTab.Projects -> ProjectsFormContent(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
