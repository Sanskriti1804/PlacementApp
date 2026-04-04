package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Project form content. Shown when Projects tab is selected (no separate screen).
 * Project name, image card, link, description, technologies, GitHub, live demo, team size, team members.
 */

private val TECHNOLOGY_OPTIONS = listOf(
    "Kotlin", "Java", "Jetpack Compose", "Android SDK", "Firebase", "REST API",
    "Retrofit", "Room DB", "SQLite", "Node.js", "Express", "MongoDB",
    "React", "Tailwind", "Bootstrap", "Docker", "AWS", "Git"
)

@Composable
fun ProjectsFormContent(
    modifier: Modifier = Modifier
) {
    var projectName by remember { mutableStateOf("") }
    var projectLink by remember { mutableStateOf("") }
    var projectDescription by remember { mutableStateOf("") }
    var technologiesExpanded by remember { mutableStateOf(false) }
    var technologiesSelected by remember { mutableStateOf<Set<String>>(emptySet()) }
    var githubRepo by remember { mutableStateOf("") }
    var liveDemo by remember { mutableStateOf("") }
    var teamSize by remember { mutableStateOf(0) }
    val teamMembers = remember { mutableStateListOf<Pair<String, String>>() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        FormField(
            label = "Project Name",
            value = projectName,
            onValueChange = { projectName = it },
            placeholder = "Project name"
        )

        ProjectImageUploadCard()

        FormField(
            label = "Project Link",
            value = projectLink,
            onValueChange = { projectLink = it },
            required = false,
            placeholder = "Hosted project, demo, or documentation URL"
        )

        FormField(
            label = "Project Description",
            value = projectDescription,
            onValueChange = { projectDescription = it },
            placeholder = "Purpose, features, implementation, achievements",
            singleLine = false
        )

        SkillSelectionField(
            label = "Technologies Used",
            placeholder = "Select Technologies",
            options = TECHNOLOGY_OPTIONS,
            selected = technologiesSelected,
            expanded = technologiesExpanded,
            onExpandToggle = { technologiesExpanded = true },
            onSelectionChange = { technologiesSelected = it }
        )

        FormField(
            label = "GitHub Repository",
            value = githubRepo,
            onValueChange = { githubRepo = it },
            placeholder = "https://github.com/..."
        )

        FormField(
            label = "Live Demo",
            value = liveDemo,
            onValueChange = { liveDemo = it },
            required = false,
            placeholder = "Live hosted demo URL"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Team Size",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TeamSizeCounter(
                value = teamSize,
                onDecrement = {
                    if (teamSize > 0) {
                        teamSize--
                        if (teamMembers.size > teamSize) teamMembers.removeAt(teamMembers.lastIndex)
                    }
                },
                onIncrement = {
                    teamSize++
                    while (teamMembers.size < teamSize) teamMembers.add("" to "")
                }
            )
        }

        AnimatedVisibility(
            visible = teamSize > 0,
            enter = expandVertically(),
            exit = shrinkVertically(),
            label = "team_members_section"
        ) {
            Column(
                modifier = Modifier.animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                (0 until teamSize).forEach { index ->
                    val current = teamMembers.getOrElse(index) { "" to "" }
                    TeamMemberRow(
                        memberIndex = index + 1,
                        memberName = current.first,
                        onMemberNameChange = { newName ->
                            while (teamMembers.size <= index) teamMembers.add("" to "")
                            teamMembers[index] = newName to teamMembers[index].second
                        },
                        role = current.second,
                        onRoleChange = { newRole ->
                            while (teamMembers.size <= index) teamMembers.add("" to "")
                            teamMembers[index] = teamMembers[index].first to newRole
                        }
                    )
                }
            }
        }
    }
}
