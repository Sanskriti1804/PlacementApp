package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.ui.components.ApplicationStatusStage
import com.example.placementprojectmp.ui.screens.student.component.UserInfoTabs
import com.example.placementprojectmp.viewmodel.StudentPersonalDraftViewModel
import org.koin.androidx.compose.koinViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import com.example.placementprojectmp.data.model.JobEntryDraft
import android.content.Intent
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import com.example.placementprojectmp.ui.components.ProfilePlatform

class ApplicationOverrideViewModel(val savedStateHandle: SavedStateHandle) : ViewModel()

/**
 * Application screen: company header, profile preview, contact, platform links,
 * resume button, skills, education, collapsible additional info, certifications.
 * All content in a LazyColumn; uses existing AppTopBar and theme.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ApplicationScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    val personalDraftViewModel: StudentPersonalDraftViewModel = koinViewModel()
    val personalDraft by personalDraftViewModel.draft.collectAsState()
    val context = LocalContext.current

    val connectorLinks = try {
        if (personalDraft.connectorLinksJson.isNotBlank() && personalDraft.connectorLinksJson != "{}") {
            Json.decodeFromString<Map<ProfilePlatform, String>>(personalDraft.connectorLinksJson)
        } else emptyMap()
    } catch(e: Exception) { emptyMap() }

    val overrideViewModel: ApplicationOverrideViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: androidx.lifecycle.viewmodel.CreationExtras): T {
                val savedStateHandle = extras.createSavedStateHandle()
                return ApplicationOverrideViewModel(savedStateHandle) as T
            }
        }
    )
    val overrideName = overrideViewModel.savedStateHandle.get<String>("name")
    val overrideEmail = overrideViewModel.savedStateHandle.get<String>("email")
    val overrideDept = overrideViewModel.savedStateHandle.get<String>("department")

    val resolvedName = overrideName?.takeIf { it.isNotBlank() } ?: personalDraft.fullName.takeIf { it.isNotBlank() } ?: "RAHUL SHARMA"
    val resolvedHandle = personalDraft.username.takeIf { it.isNotBlank() }?.let { "@$it" } ?: "@rahuldev"
    val manualDob = listOf(personalDraft.day, personalDraft.month, personalDraft.year).filter { it.isNotBlank() }.joinToString(" ")
    val resolvedDob = manualDob.takeIf { it.isNotBlank() } ?: "12 Sep 2002"
    val resolvedEmail = overrideEmail?.takeIf { it.isNotBlank() } ?: personalDraft.email.takeIf { it.isNotBlank() } ?: "rahul@email.com"
    val resolvedRole = overrideDept?.takeIf { it.isNotBlank() } ?: personalDraft.role.takeIf { it.isNotBlank() } ?: "Android Developer"

    val mergedSkills = (personalDraft.languagesSelected + personalDraft.toolsSelected + personalDraft.frameworksSelected).toList()
    val skills = mergedSkills.takeIf { it.isNotEmpty() }?.take(3) 
        ?: listOf("Kotlin", "Java", "Jetpack Compose", "Spring Boot", "Firebase", "Git", "Postman", "REST APIs", "Retrofit", "Room DB", "Docker", "Figma").take(3)

    data class EducationItem(
        val title: String,
        val subtitle: String,
        val score: String
    )
    
    val educationItems = mutableListOf<EducationItem>()

    val school10 = personalDraft.school10Name.takeIf { it.isNotBlank() } ?: "Delhi Public School"
    val year10 = personalDraft.passYear10.takeIf { it.isNotBlank() } ?: "2018"
    val percent10 = personalDraft.class10Percent.takeIf { it.isNotBlank() }?.let { "$it%" } ?: "92%"
    educationItems.add(EducationItem("10th", "$school10 • $year10", percent10))

    val school12 = personalDraft.school12Name.takeIf { it.isNotBlank() } ?: "St. Xavier Senior Secondary"
    val year12 = personalDraft.passYear12.takeIf { it.isNotBlank() } ?: "2020"
    val percent12 = personalDraft.class12Percent.takeIf { it.isNotBlank() }?.let { "$it%" } ?: "89%"
    educationItems.add(EducationItem("12th", "$school12 • $year12", percent12))

    val universityGrad = personalDraft.university.takeIf { it.isNotBlank() } ?: "ABC Institute of Technology"
    val passYearGrad = personalDraft.gradPassYear.takeIf { it.isNotBlank() } ?: "2025"
    val cgpaGrad = personalDraft.gradCgpa.takeIf { it.isNotBlank() }?.let { "$it CGPA" } ?: "8.6 CGPA"
    educationItems.add(EducationItem("Graduation", "$universityGrad • $passYearGrad", cgpaGrad))
    
    educationItems.add(EducationItem("Post Graduation", "ABC Institute of Technology • 2025 - 2027", "—"))
    val certCards = listOf(
        "Google Android",
        "Hackathon 2025",
        "AWS Cloud",
        "Open Source",
        "",
        ""
    )
    val certColors = listOf(
        ApplicationStatusStage.Applied.color,
        ApplicationStatusStage.ApplicationReviewed.color,
        ApplicationStatusStage.InterviewScheduled.color,
        ApplicationStatusStage.Offer.color,
        ApplicationStatusStage.Shortlisted.color,
        ApplicationStatusStage.Hired.color
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationClick = onNotificationClick
            )
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(0.8f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = resolvedName,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = resolvedHandle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = resolvedRole,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Card(
                    modifier = Modifier
                        .weight(0.3f)
                        .height(116.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.pfp_user),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        item {
            Text(
                text = "Building high-performance Android apps with a strong focus on clean architecture and intuitive user experiences.\nFocused on reliability and scalable architecture for production-ready mobile platforms.",
                maxLines = 3,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
        item {
            UserInfoTabs(
                name = resolvedName,
                date = resolvedDob,
                email = resolvedEmail
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(
                    R.drawable.pic_linkedin to "LinkedIn",
                    R.drawable.pic_github to "GitHub",
                    R.drawable.pic_leetcode to "LeetCode",
                    R.drawable.pic_portfolio to "Projects"
                ).forEach { (iconRes, label) ->
                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Card(
                            modifier = Modifier.clickable {
                                val url = when (label) {
                                    "LinkedIn" -> connectorLinks[ProfilePlatform.LinkedIn]
                                    "GitHub" -> connectorLinks[ProfilePlatform.GitHub]
                                    "LeetCode" -> connectorLinks[ProfilePlatform.LeetCode]
                                    else -> null
                                }
                                if (!url.isNullOrBlank()) {
                                    try {
                                        var parsedUrl = url
                                        if (!parsedUrl.startsWith("http://") && !parsedUrl.startsWith("https://")) {
                                            parsedUrl = "https://$parsedUrl"
                                        }
                                        val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(parsedUrl)).apply {
                                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        // Ignore
                                    }
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                        ) {
                            Image(
                                painter = painterResource(iconRes),
                                contentDescription = label,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(30.dp)
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 42.dp)
                    .clickable {
                        if (personalDraft.resumePdfUri.isNotBlank()) {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setDataAndType(android.net.Uri.parse(personalDraft.resumePdfUri), "application/pdf")
                                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                context.startActivity(Intent.createChooser(intent, "Open PDF"))
                            } catch (e: Exception) {
                                Toast.makeText(context, "No app found to open PDF", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(MaterialTheme.colorScheme.onPrimary))
                    Text("resume.pdf", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(MaterialTheme.colorScheme.onPrimary))
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Technical Skills", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    skills.forEach { skill ->
                        Text(
                            text = skill,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Education", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
                educationItems.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = androidx.compose.ui.Alignment.Top
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1f)) {
                            Text(item.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                            Text(item.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            if (item.title == "Post Graduation") {
                                Text(
                                    text = "Currently Pursuing",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Text(item.score, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Light, color = MaterialTheme.colorScheme.onSurface)
                    }
                    if (index == 1 && personalDraft.academicGapEnabled) {
                        val gapStr = personalDraft.gapYears.takeIf { it.isNotBlank() } ?: "1"
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
                        ) {
                            Text(
                                text = "$gapStr-year gap",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                val jobEntries = try {
                    if (personalDraft.jobEntriesJson.isNotBlank() && personalDraft.jobEntriesJson != "[]") 
                        Json.decodeFromString<List<JobEntryDraft>>(personalDraft.jobEntriesJson)
                    else emptyList()
                } catch (e: Exception) { emptyList() }
                
                if (jobEntries.isNotEmpty() && personalDraft.hasWorkExperience) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Experience", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                        jobEntries.forEach { entry ->
                            val role = entry.jobTypeTitle.takeIf { it.isNotBlank() } ?: "Role"
                            val company = entry.companyName.takeIf { it.isNotBlank() } ?: "Company"
                            val start = listOf(entry.fromMonth, entry.fromYear).filter { it.isNotBlank() }.joinToString("-")
                            val end = listOf(entry.toMonth, entry.toYear).filter { it.isNotBlank() }.joinToString("-")
                            val duration = if (start.isNotBlank() && end.isNotBlank()) "$start - $end" else if (start.isNotBlank()) start else "Unknown"
                            
                            Text(role, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("$company • $duration", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Experience", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                        Text("Android Developer Intern", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Tech Solutions Pvt Ltd • Jan 2025 - Present", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
        item {
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text("ACADEMIC PERFORMANCE")
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Certifications & Achievements", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    maxItemsInEachRow = 3
                ) {
                    certCards.forEachIndexed { index, value ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.31f)
                                .aspectRatio(1.9f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = certColors[index].copy(alpha = 0.9f))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                contentAlignment = Alignment.TopStart
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.onPrimary)
                                )
                                if (value.isBlank()) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add",
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.BottomEnd
                                    ) {
                                        Text(
                                            text = value,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
