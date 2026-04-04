package com.example.placementprojectmp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Skills form content: Programming Languages, Frameworks, Tools, Soft Skills.
 * Used only inside Student Profile Form when the Skills tab is selected (no separate screen).
 */

private val PROGRAMMING_LANGUAGES = listOf(
    "Java", "Kotlin", "C++", "Python", "JavaScript", "TypeScript", "Go",
    "Swift", "Rust", "PHP", "Ruby", "C", "C#", "Dart"
)

private val FRAMEWORKS = listOf(
    "Android Development", "Web Development", "Spring Boot", "React", "Node.js",
    "Flutter", "Jetpack Compose", "Django", "Angular", "Vue.js", "Express.js",
    "ASP.NET", "Laravel", "Next.js"
)

private val TOOLS_AND_PLATFORMS = listOf(
    "Git", "GitHub", "GitLab", "Bitbucket", "Docker", "Kubernetes", "Postman", "Firebase",
    "AWS", "Google Cloud", "Azure", "Jenkins", "CI/CD", "Gradle",
    "Figma", "Adobe XD", "Linux", "Bash"
)

private val SOFT_SKILLS = listOf(
    "Communication", "Leadership", "Teamwork", "Problem Solving", "Critical Thinking",
    "Adaptability", "Time Management", "Conflict Resolution", "Public Speaking",
    "Creativity", "Analytical Thinking", "Decision Making", "Collaboration"
)

@Composable
fun SkillsFormContent(
    modifier: Modifier = Modifier
) {
    var languagesExpanded by remember { mutableStateOf(false) }
    var languagesSelected by remember { mutableStateOf<Set<String>>(emptySet()) }
    var frameworksExpanded by remember { mutableStateOf(false) }
    var frameworksSelected by remember { mutableStateOf<Set<String>>(emptySet()) }
    var toolsExpanded by remember { mutableStateOf(false) }
    var toolsSelected by remember { mutableStateOf<Set<String>>(emptySet()) }
    var softSkillsExpanded by remember { mutableStateOf(false) }
    var softSkillsSelected by remember { mutableStateOf<Set<String>>(emptySet()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SkillSelectionField(
            label = "Programming Languages",
            placeholder = "Select Programming Languages",
            options = PROGRAMMING_LANGUAGES,
            selected = languagesSelected,
            expanded = languagesExpanded,
            onExpandToggle = { languagesExpanded = true },
            onSelectionChange = { languagesSelected = it }
        )
        SkillSelectionField(
            label = "Frameworks",
            placeholder = "Select Frameworks",
            options = FRAMEWORKS,
            selected = frameworksSelected,
            expanded = frameworksExpanded,
            onExpandToggle = { frameworksExpanded = true },
            onSelectionChange = { frameworksSelected = it }
        )
        SkillSelectionField(
            label = "Tools & Platforms",
            placeholder = "Select Tools",
            options = TOOLS_AND_PLATFORMS,
            selected = toolsSelected,
            expanded = toolsExpanded,
            onExpandToggle = { toolsExpanded = true },
            onSelectionChange = { toolsSelected = it }
        )
        SkillSelectionField(
            label = "Soft Skills",
            placeholder = "Select Soft Skills",
            options = SOFT_SKILLS,
            selected = softSkillsSelected,
            expanded = softSkillsExpanded,
            onExpandToggle = { softSkillsExpanded = true },
            onSelectionChange = { softSkillsSelected = it }
        )
    }
}
