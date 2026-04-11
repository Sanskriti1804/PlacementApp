package com.example.placementprojectmp.ui.screens.system.component

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/** One statistic cell for [CountGrid]. */
data class CountGridStat(
    val label: String,
    val value: String
)

/** Default dummy stats for system dashboards (6 items, 2×3 grid). */
object CountGridDefaults {
    val stats: List<CountGridStat> = listOf(
        CountGridStat(label = "Registered Students", value = "582"),
        CountGridStat(label = "Active Faculty Members", value = "47"),
        CountGridStat(label = "Hiring Partners", value = "126"),
        CountGridStat(label = "Placement Drives", value = "34"),
        CountGridStat(label = "Departments Managed", value = "18"),
        CountGridStat(label = "Active Job Postings", value = "215")
    )
}

/**
 * Compact 2-column × 3-row stat grid for system-level dashboards.
 * Each cell: thin label (top-start, max 2 lines) and value (bottom-end, medium weight).
 */
@Composable
fun CountGrid(
    modifier: Modifier = Modifier,
    stats: List<CountGridStat> = CountGridDefaults.stats
) {
    require(stats.size == 6) { "CountGrid expects exactly 6 stats (2×3 grid), got ${stats.size}" }

    val rows = listOf(
        stats.subList(0, 2),
        stats.subList(2, 4),
        stats.subList(4, 6)
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rows.forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                pair.forEach { stat ->
                    CountGridCell(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = CountGridCellMinHeight),
                        stat = stat
                    )
                }
            }
        }
    }
}

private val CountGridCellMinHeight = 100.dp

@Composable
private fun CountGridCell(
    modifier: Modifier = Modifier,
    stat: CountGridStat
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
