package com.example.placementprojectmp.ui.screens.shared.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @param title Primary value, shown bottom-end (bold, [MaterialTheme.typography.titleMedium]).
 * @param subtitle Secondary label, shown top-end ([MaterialTheme.typography.bodySmall]).
 */
data class InfoGridItem(
    val title: String,
    val subtitle: String
)

private val defaultCellSpacing = 12.dp
private val cardCornerRadius = 16.dp
private val cardElevation = 2.dp
private val cardInnerPadding = 12.dp

/**
 * A static 2×2 grid of cards for dashboards and overview sections.
 * Pass up to four [InfoGridItem]s (extras are ignored; missing slots stay empty but spaced).
 */
@Composable
fun InfoGrid(
    items: List<InfoGridItem>,
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = defaultCellSpacing,
    verticalSpacing: Dp = defaultCellSpacing
) {
    val cells = items.take(4)
    val row1 = listOf(cells.getOrNull(0), cells.getOrNull(1))
    val row2 = listOf(cells.getOrNull(2), cells.getOrNull(3))

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        InfoGridRow(
            cells = row1,
            horizontalSpacing = horizontalSpacing,
            modifier = Modifier.fillMaxWidth()
        )
        InfoGridRow(
            cells = row2,
            horizontalSpacing = horizontalSpacing,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun InfoGridRow(
    cells: List<InfoGridItem?>,
    horizontalSpacing: Dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
    ) {
        cells.forEach { item ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    // Shared height within the row without a fixed dp width.
                    .aspectRatio(1f)
            ) {
                if (item != null) {
                    InfoGridCell(item = item, modifier = Modifier.fillMaxHeight())
                }
            }
        }
    }
}

@Composable
private fun InfoGridCell(
    item: InfoGridItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(cardInnerPadding)
        ) {
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}
