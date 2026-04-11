package com.example.placementprojectmp.ui.screens.system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * System-level analytics card: placement success rate, trend vs prior period, and Jobs / Applicants / Selections.
 * Uses [MaterialTheme] only — no hardcoded palette values.
 */
enum class ReportCardTrend {
    /** Performance improved — trend icon uses theme “positive” emphasis ([ColorScheme.primary]). */
    UP,

    /** Performance declined — trend icon uses [ColorScheme.error]. */
    DOWN
}

@Composable
fun ReportCard(
    modifier: Modifier = Modifier,
    primaryMetricTitle: String = "Placement Success Rate",
    placementSuccessPercent: Int,
    trend: ReportCardTrend,
    jobsTotalLabel: String = "Jobs",
    jobsTotalValue: String,
    applicantsTotalLabel: String = "Applicants",
    applicantsTotalValue: String,
    selectionsTotalLabel: String = "Selections",
    selectionsTotalValue: String
) {
    val pct = placementSuccessPercent.coerceIn(0, 100)
    val trendTint = when (trend) {
        ReportCardTrend.UP -> MaterialTheme.colorScheme.primary
        ReportCardTrend.DOWN -> MaterialTheme.colorScheme.error
    }
    val trendIcon = when (trend) {
        ReportCardTrend.UP -> Icons.AutoMirrored.Filled.TrendingUp
        ReportCardTrend.DOWN -> Icons.AutoMirrored.Filled.TrendingDown
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.48f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = primaryMetricTitle,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "$pct%",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
                Icon(
                    imageVector = trendIcon,
                    contentDescription = null,
                    tint = trendTint,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                ReportMetricBlock(
                    modifier = Modifier.weight(1f),
                    label = jobsTotalLabel,
                    value = jobsTotalValue
                )
                ReportMetricBlock(
                    modifier = Modifier.weight(1f),
                    label = applicantsTotalLabel,
                    value = applicantsTotalValue
                )
                ReportMetricBlock(
                    modifier = Modifier.weight(1f),
                    label = selectionsTotalLabel,
                    value = selectionsTotalValue
                )
            }
        }
    }
}

@Composable
private fun ReportMetricBlock(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    val blockShape = RoundedCornerShape(8.dp)
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.72f),
                shape = blockShape
            )
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }
    }
}
