package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R

@Composable
fun TeacherProfileScreen(
    modifier: Modifier = Modifier,
    onShowMoreCompanies: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 20.dp,
            vertical = 16.dp
        )
    ) {
        item {
            ProfileHeaderSection()
        }

        item {
            TeacherStatusInfoCard()
        }

        item {
            TeacherContactSection()
        }

        item {
            TeacherAdditionalDetailsSection()
        }
    }
}

@Composable
private fun ProfileHeaderSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32))
                )
                Text(
                    text = "Aug 2018 - Present",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.width(110.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pfp_staff),
                        contentDescription = "Teacher profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(18.dp))
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Dr. Ananya Sharma",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Professor at XYZ College",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text(text = "Placement Incharge", style = MaterialTheme.typography.labelSmall) },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = Color(0xFF7A1F6A),
                            containerColor = Color(0xFF7A1F6A).copy(alpha = 0.14f)
                        )
                    )
                    Text(
                        text = "DIT_PX4",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun TeacherStatusInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(Color(0xFF1976D2), Color(0xFF8E24AA), Color(0xFF2E7D32)).forEach { dotColor ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Faculty Position - Professor", style = MaterialTheme.typography.bodySmall, color = Color(0xFF1976D2))
                Text(text = "Placement Responsibility - Placement Incharge", style = MaterialTheme.typography.bodySmall, color = Color(0xFF8E24AA))
                Text(text = "Department - Computer Science", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2E7D32))
            }
        }
    }
}

@Composable
private fun TeacherContactSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ContactInfoCard(modifier = Modifier.weight(1f), label = "Email", value = "ananya@xyz.edu")
            ContactInfoCard(modifier = Modifier.weight(1f), label = "Phone", value = "+91 98765 43210")
        }
        ContactInfoCard(modifier = Modifier.fillMaxWidth(), label = "LinkedIn", value = "linkedin.com/in/ananyasharma")
    }
}

@Composable
private fun ContactInfoCard(
    modifier: Modifier,
    label: String,
    value: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun TeacherAdditionalDetailsSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Additional Details",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        InfoRow(
            label = "Office Location",
            value = "Room 302, Placement Cell Building"
        )
        InfoRow(
            label = "Professional Information",
            value = "Professor at XYZ College"
        )
        InfoRow(
            label = "Qualification",
            value = "PhD in Artificial Intelligence"
        )
        InfoRow(
            label = "Specialization",
            value = "Machine Learning, Data Systems"
        )
        InfoRow(
            label = "Teaching Experience",
            value = "12+ years, Former Software Engineer at Nexora Systems"
        )
        InfoRow(
            label = "Subjects Taught",
            value = "Machine Learning, Data Structures, Artificial Intelligence"
        )
        InfoRow(
            label = "Current Role",
            value = "Placement Faculty Coordinator"
        )
        InfoRow(
            label = "Placement Responsibility",
            value = "Placement Incharge"
        )
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = if (label == "Professional Information" || label == "Placement Responsibility") {
                MaterialTheme.typography.bodyMedium
            } else {
                MaterialTheme.typography.bodySmall
            },
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PlacementResponsibilitiesSection(
    onShowMoreCompanies: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Placement Responsibilities",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        val cards = listOf(
            ResponsibilityCardData(
                title = "Departments Managed",
                subtitle = "4 Departments",
                type = ResponsibilityType.Departments
            ),
            ResponsibilityCardData(
                title = "Placement Drives",
                subtitle = "23 Drives",
                type = ResponsibilityType.Drives
            ),
            ResponsibilityCardData(
                title = "Companies",
                subtitle = "15 Companies",
                type = ResponsibilityType.Companies
            ),
            ResponsibilityCardData(
                title = "Students",
                subtitle = "320 Students",
                type = ResponsibilityType.Students
            )
        )

        var expandedCompanies by remember { mutableStateOf(false) }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ResponsibilityCard(
                    data = cards[0],
                    modifier = Modifier.weight(1f)
                )
                ResponsibilityCard(
                    data = cards[1],
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CompaniesResponsibilityCard(
                    data = cards[2],
                    expanded = expandedCompanies,
                    onToggleExpand = { expandedCompanies = !expandedCompanies },
                    onShowMoreCompanies = onShowMoreCompanies,
                    modifier = Modifier.weight(1f)
                )
                StudentsResponsibilityCard(
                    data = cards[3],
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

private data class ResponsibilityCardData(
    val title: String,
    val subtitle: String,
    val type: ResponsibilityType
)

private enum class ResponsibilityType {
    Departments, Drives, Companies, Students
}

@Composable
private fun ResponsibilityCard(
    data: ResponsibilityCardData,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 140, easing = FastOutSlowInEasing),
        label = "resp_card_scale"
    )

    Card(
        modifier = modifier
            .height(140.dp)
            .scale(scale),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        interactionSource = interactionSource,
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(-10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (data.type) {
                                ResponsibilityType.Departments -> "\uD83D\uDC68"
                                ResponsibilityType.Drives -> "\uD83D\uDCC5"
                                ResponsibilityType.Companies -> "\uD83D\uDCBC"
                                ResponsibilityType.Students -> "\uD83D\uDC64"
                            },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = data.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CompaniesResponsibilityCard(
    data: ResponsibilityCardData,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    onShowMoreCompanies: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 140, easing = FastOutSlowInEasing),
        label = "companies_card_scale"
    )

    Card(
        modifier = modifier
            .scale(scale)
            .animateContentSize(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        interactionSource = interactionSource,
        onClick = onToggleExpand
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(-10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "\uD83D\uDCBC",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = data.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (expanded) {
                val companies = listOf("Google", "Microsoft", "Amazon", "Adobe", "Infosys")
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    companies.take(4).forEach { name ->
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Show More →",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onShowMoreCompanies()
                            }
                    )
                }
            }
        }
    }
}

@Composable
private fun StudentsResponsibilityCard(
    data: ResponsibilityCardData,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 140, easing = FastOutSlowInEasing),
        label = "students_card_scale"
    )

    Card(
        modifier = modifier
            .scale(scale),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(-10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "\uD83D\uDC64",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = data.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(bottomStart = 10.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Placed Students",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

