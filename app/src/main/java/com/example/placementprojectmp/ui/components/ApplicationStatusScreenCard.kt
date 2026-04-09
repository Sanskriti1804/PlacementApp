package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R

/**
 * Data for one application status entry on ApplicationStatusScreen.
 */
data class ApplicationStatusScreenItem(
    val companyName: String,
    val location: String,
    val role: String,
    val appliedDate: String,
    val currentStage: ApplicationStatusStage,
    val interviewDate: String? = null,
    val interviewMode: String = "Offline"
)

/**
 * Full application status card: logo, company info, applied date pill, status indicators,
 * optional interview section, expand arrow for timeline. Press and expand animations.
 */
@Composable
fun ApplicationStatusScreenCard(
    modifier: Modifier = Modifier,
    item: ApplicationStatusScreenItem,
    logoResId: Int = R.drawable.comp_1,
    onReminderClick: () -> Unit = {},
    onCompanyClick: ((ApplicationStatusScreenItem) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var showReminderCard by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cardColor by animateColorAsState(
        targetValue = if (isPressed) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant,
        label = "card_bg"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (onCompanyClick != null) Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onCompanyClick(item) }
                            else Modifier
                        ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        val logoPainter = runCatching { painterResource(id = logoResId) }.getOrNull()
                        if (logoResId != 0 && logoPainter != null) {
                            Image(
                                painter = logoPainter,
                                contentDescription = "${item.companyName} logo",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(
                                text = item.companyName.take(1).uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = item.companyName,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = item.location,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = item.role,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand timeline",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { expanded = !expanded },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            AppliedDatePill(appliedDate = item.appliedDate)

            Spacer(modifier = Modifier.height(12.dp))

            StatusIndicatorRow(
                currentStage = item.currentStage,
                statusLabel = item.currentStage.label
            )

            if (item.interviewDate != null) {
                InterviewSection(
                    interviewDate = item.interviewDate,
                    mode = item.interviewMode,
                    companyName = item.companyName,
                    onReminderClick = { showReminderCard = true }
                )
                if (showReminderCard) {
                    Spacer(modifier = Modifier.height(12.dp))
                    ReminderCard(
                        companyName = item.companyName,
                        interviewDate = item.interviewDate,
                        onDismiss = { showReminderCard = false }
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    ApplicationTimeline(currentStage = item.currentStage)
                }
            }
        }
    }
}

@Composable
private fun AppliedDatePill(appliedDate: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = "Applied on $appliedDate",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
