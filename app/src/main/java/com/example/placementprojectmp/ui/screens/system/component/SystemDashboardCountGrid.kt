package com.example.placementprojectmp.ui.screens.system.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * System Dashboard only: 3 cards × 2 rows, outlined cells (no filled background), same spacing as [CountGrid].
 * Reuses [CountGridStat] / [CountGridDefaults] for data only.
 */
@Composable
fun SystemDashboardCountGrid(
    modifier: Modifier = Modifier,
    stats: List<CountGridStat> = CountGridDefaults.stats
) {
    require(stats.size == 6) { "SystemDashboardCountGrid expects exactly 6 stats (3×2 grid), got ${stats.size}" }

    val rows = listOf(
        stats.subList(0, 3),
        stats.subList(3, 6)
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rows.forEach { rowOfThree ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowOfThree.forEach { stat ->
                    SystemDashboardOutlinedCountCell(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = SystemDashboardCountCellMinHeight),
                        stat = stat
                    )
                }
            }
        }
    }
}

private val SystemDashboardCountCellMinHeight = 100.dp

@Composable
private fun SystemDashboardOutlinedCountCell(
    modifier: Modifier = Modifier,
    stat: CountGridStat
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.65f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Text(
                text = stat.label,
                modifier = Modifier.align(Alignment.TopStart),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Light,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )
            Text(
                text = stat.value,
                modifier = Modifier.align(Alignment.BottomEnd),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End
            )
        }
    }
}
