package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.ui.components.DomainHeader
import com.example.placementprojectmp.ui.screens.shared.component.NeonGlassToastHost
import com.example.placementprojectmp.ui.components.OpportunitiesFilterCapsule
import com.example.placementprojectmp.ui.components.OpportunityCard

private data class OpportunityItem(
    val id: String,
    val companyName: String,
    val jobTitle: String,
    val tags: List<String>
)

private fun dummyOpportunities(): List<OpportunityItem> = listOf(
    OpportunityItem("1", "TechCorp", "Java Developer", listOf("Offshore", "Salary 5LPA")),
    OpportunityItem("2", "SoftSolutions", "Android Developer", listOf("Remote", "3+ yrs")),
    OpportunityItem("3", "DataDrive", "Backend Engineer", listOf("Hybrid", "Salary 8LPA")),
    OpportunityItem("4", "AppWorks", "Full Stack Developer", listOf("Onsite", "Fresher")),
    OpportunityItem("5", "CloudNine", "Java Developer", listOf("Offshore", "5LPA")),
    OpportunityItem("6", "InnovateLabs", "Android Developer", listOf("Remote", "Salary 6LPA"))
)

@Composable
fun OpportunitiesScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    var domainText by remember { mutableStateOf("Java Developer") }
    var locationText by remember { mutableStateOf("") }
    var experienceText by remember { mutableStateOf("") }
    val opportunities = remember { dummyOpportunities() }
    val savedIds = remember { mutableStateListOf<String>() }
    val snackbarHostState = remember { SnackbarHostState() }
    var saveSnackbarTrigger by remember { mutableStateOf(0) }

    val displayDomain = domainText.ifEmpty { "Java Developer" }

    LaunchedEffect(saveSnackbarTrigger) {
        if (saveSnackbarTrigger > 0) snackbarHostState.showSnackbar("Job saved")
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item {
            OpportunitiesFilterCapsule(
                modifier = Modifier.padding(horizontal = 20.dp),
                domainText = domainText,
                onDomainChange = { domainText = it },
                locationText = locationText,
                onLocationChange = { locationText = it },
                experienceText = experienceText,
                onExperienceChange = { experienceText = it }
            )
        }
        item {
            DomainHeader(
                modifier = Modifier.padding(horizontal = 20.dp),
                domainName = displayDomain,
                resultCount = opportunities.size
            )
        }
        item { Spacer(modifier = Modifier.padding(8.dp)) }
        itemsIndexed(opportunities) { _, opp ->
            OpportunityCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                companyName = opp.companyName,
                jobTitle = opp.jobTitle,
                tags = opp.tags,
                saved = opp.id in savedIds,
                onSaveClick = {
                    if (opp.id in savedIds) savedIds.remove(opp.id) else {
                        savedIds.add(opp.id)
                        saveSnackbarTrigger++
                    }
                },
                onDetailsClick = { },
                onApplyClick = { }
            )
        }
    }

        NeonGlassToastHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}
