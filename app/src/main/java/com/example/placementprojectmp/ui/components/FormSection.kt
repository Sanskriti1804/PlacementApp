package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.student.screens.EducationInformationForm
import com.example.placementprojectmp.ui.screens.student.screens.PersonalInformationForm
import kotlinx.coroutines.launch

/**
 * Form content area for profile form tabs.
 * Personal / Education / Skills / Experience / Projects show respective form content inline.
 */
@Composable
fun FormSection(
    selectedTab: ProfileFormTab,
    onTabSelected: (ProfileFormTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = ProfileFormTab.entries
    val pagerState = rememberPagerState(
        initialPage = selectedTab.ordinal,
        pageCount = { tabs.size }
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedTab) {
        if (pagerState.currentPage != selectedTab.ordinal) {
            pagerState.animateScrollToPage(selectedTab.ordinal)
        }
    }
    LaunchedEffect(pagerState.currentPage) {
        val currentTab = tabs[pagerState.currentPage]
        if (currentTab != selectedTab) onTabSelected(currentTab)
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth()
    ) { page ->
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.TopStart
            ) {
                when (tabs[page]) {
                    ProfileFormTab.Personal -> PersonalInformationForm(modifier = Modifier.fillMaxWidth())
                    ProfileFormTab.Education -> EducationInformationForm(modifier = Modifier.fillMaxWidth())
                    ProfileFormTab.Skills -> SkillsFormContent(modifier = Modifier.fillMaxWidth())
                    ProfileFormTab.Experience -> ExperienceFormContent(modifier = Modifier.fillMaxWidth())
                    ProfileFormTab.Projects -> ProjectsFormContent(modifier = Modifier.fillMaxWidth())
                }
            }
            Button(
                onClick = {
                    if (page < tabs.lastIndex) {
                        scope.launch { pagerState.animateScrollToPage(page + 1) }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
