package com.example.placementprojectmp.ui.screens.shared.cards

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.theme.NeonBlue
import com.example.placementprojectmp.viewmodel.CompanyUiModel
import com.example.placementprojectmp.viewmodel.Status

@Composable
fun CompanyCard(
    modifier: Modifier,
    company: CompanyUiModel,
    onWebsiteClick: (String) -> Unit,
    onClick: () -> Unit
) {
    var overlayExpanded by remember { mutableStateOf(false) }
    val cardShape = RoundedCornerShape(18.dp)

    Card(
        modifier = modifier,
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = !overlayExpanded,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current
                    ) { onClick() }
                    .padding(14.dp)
                    .padding(bottom = 22.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(companyStatusColor(company.status))
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    LogoImage(logoResId = company.logoResId, containerSize = 48.dp)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = company.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = company.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CompanyField(label = "Industry", value = company.industry.name.lowercase(), modifier = Modifier.weight(1f))
                    CompanyField(label = "Type", value = company.companyType, modifier = Modifier.weight(1f))
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = LocalIndication.current
                        ) { onWebsiteClick(company.website) }
                    ) {
                        Text(
                            text = company.website.removePrefix("https://").removePrefix("http://"),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowOutward,
                            contentDescription = "Open website",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    if (company.companyEmail.isNotBlank()) {
                        Text(
                            text = company.companyEmail,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                )
                if (company.hrName.isNotBlank() || company.hrEmail.isNotBlank() || company.hrPhone.isNotBlank()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        if (company.hrName.isNotBlank()) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "HR",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = company.hrName,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        if (company.hrEmail.isNotBlank()) {
                            Text(
                                text = company.hrEmail,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (company.hrPhone.isNotBlank()) {
                            Text(
                                text = company.hrPhone,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 12.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current
                    ) { overlayExpanded = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Expand details",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = overlayExpanded,
                modifier = Modifier.matchParentSize(),
                enter = fadeIn(animationSpec = tween(220)),
                exit = fadeOut(animationSpec = tween(180))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(cardShape)
                        .background(NeonBlue)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { overlayExpanded = false },
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Text(
                            text = company.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = companyOverlayDescription(company),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black.copy(alpha = 0.92f),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun companyStatusColor(status: Status): Color = when (status) {
    Status.OPEN -> Color(0xFF2E7D32)
    Status.UPCOMING -> Color(0xFFFFC107)
    Status.CLOSED -> Color(0xFF1976D2)
}

private fun companyOverlayDescription(company: CompanyUiModel): String =
    "${company.name} partners with academic institutions for campus hiring, internships, and early-career programs.\n" +
        "Teams span engineering, product, data, and cloud with structured onboarding and mentor-led reviews.\n" +
        "Recruiters use shared dashboards for scheduling, candidate analytics, and outcome tracking.\n" +
        "Delivery is hybrid-first with hubs in major cities and remote-friendly squads where applicable.\n" +
        "Visit the careers portal for open roles, eligibility, and upcoming drive timelines."

@Composable
private fun CompanyField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
