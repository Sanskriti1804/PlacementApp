package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.CertificationList
import com.example.placementprojectmp.ui.components.CollapsibleInfoCard
import com.example.placementprojectmp.ui.components.CompanyHeader
import com.example.placementprojectmp.ui.components.EducationSection
import com.example.placementprojectmp.ui.components.PlatformLinksRow
import com.example.placementprojectmp.ui.components.ResumeButton
import com.example.placementprojectmp.ui.components.SkillsSection

/**
 * Application screen: company header, profile preview, contact, platform links,
 * resume button, skills, education, collapsible additional info, certifications.
 * All content in a LazyColumn; uses existing AppTopBar and theme.
 */
@Composable
fun ApplicationScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
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

        item {
            CompanyHeader(
                modifier = Modifier.padding(horizontal = 20.dp),
                companyName = "Google",
                location = "Bangalore, India",
                role = "Android Developer Intern",
                onRefillConfirm = { /* Refill form from profile */ }
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Basic Information Block
        item {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Rahul Sharma",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "rahul.dev",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Android Developer",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Career Objective (max 2 lines)
        item {
            Text(
                text = "Passionate Android developer with experience in Jetpack Compose and modern mobile architectures.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        // Contact Information Row 50/50
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "rahul@email.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Phone",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "+91 9876543210",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Platform Links Row (center aligned, horizontal margins)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                PlatformLinksRow()
            }
        }

        // Resume PDF Button
        item {
            ResumeButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                fileName = "Resume.pdf",
                onClick = { /* Open resume preview/download */ }
            )
        }

        // Divider
        item {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                thickness = 1.dp
            )
        }

        // Skills Section
        item {
            SkillsSection(modifier = Modifier.padding(horizontal = 20.dp))
        }

        // Education Section
        item {
            EducationSection(modifier = Modifier.padding(horizontal = 20.dp))
        }

        // Additional Information (Collapsible)
        item {
            CollapsibleInfoCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                title = "Additional Information",
                expandedContent = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column {
                            Text(
                                "Internship",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "Android Developer Intern",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "Tech Solutions Pvt Ltd",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                            )
                        }
                        Column {
                            Text(
                                "Academic Gap",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "1 Year – Personal Development",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            )
        }

        // Certifications & Achievements
        item {
            CertificationList(modifier = Modifier.padding(horizontal = 20.dp))
        }
    }
}
