package com.example.placementprojectmp.ui.screens.shared.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @param title Value line (second row, more prominent).
 * @param subtitle Key line (first row, less prominent).
 */
data class InfoGridItem(
    val title: String,
    val subtitle: String
)

private val defaultCellSpacing = 12.dp
private val cardCornerRadius = 16.dp
private val cardElevation = 2.dp
private val cardPaddingHorizontal = 12.dp
private val cardPaddingVertical = 14.dp
private val cellMinHeight = 48.dp
private val cellMaxHeight = 76.dp

/**
 * A static 2×2 grid of compact rectangular cards for dashboards and overview sections.
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
                    .then(
                        if (item == null) {
                            Modifier.heightIn(min = cellMinHeight, max = cellMaxHeight)
                        } else {
                            Modifier
                        }
                    )
            ) {
                if (item != null) {
                    InfoGridCell(item = item, modifier = Modifier.fillMaxWidth())
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
    var expanded by remember(item.title, item.subtitle) { mutableStateOf(false) }
    var subtitleOverflow by remember(item.title, item.subtitle) { mutableStateOf(false) }
    var titleOverflow by remember(item.title, item.subtitle) { mutableStateOf(false) }
    var singleLineOverflow by remember(item.title, item.subtitle) { mutableStateOf(false) }
    val hasOverflow = subtitleOverflow || titleOverflow || singleLineOverflow

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (expanded) {
                    Modifier.heightIn(min = cellMinHeight)
                } else {
                    Modifier.heightIn(min = cellMinHeight, max = cellMaxHeight)
                }
            )
            .animateContentSize(animationSpec = tween(durationMillis = 280))
            .clickable(enabled = hasOverflow) { expanded = !expanded },
        shape = RoundedCornerShape(cardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = cardPaddingHorizontal,
                    vertical = cardPaddingVertical
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                when {
                    item.subtitle.isNotBlank() && item.title.isNotBlank() -> {
                        Text(
                            text = item.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            maxLines = if (expanded) Int.MAX_VALUE else 1,
                            overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                            onTextLayout = { layoutResult ->
                                if (!expanded && layoutResult.hasVisualOverflow != subtitleOverflow) {
                                    subtitleOverflow = layoutResult.hasVisualOverflow
                                }
                            }
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            maxLines = if (expanded) Int.MAX_VALUE else 2,
                            overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                            onTextLayout = { layoutResult ->
                                if (!expanded && layoutResult.hasVisualOverflow != titleOverflow) {
                                    titleOverflow = layoutResult.hasVisualOverflow
                                }
                            }
                        )
                    }
                    item.title.isNotBlank() -> {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            maxLines = if (expanded) Int.MAX_VALUE else 2,
                            overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                            onTextLayout = { layoutResult ->
                                if (!expanded && layoutResult.hasVisualOverflow != singleLineOverflow) {
                                    singleLineOverflow = layoutResult.hasVisualOverflow
                                }
                            }
                        )
                    }
                    else -> {
                        Text(
                            text = item.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            maxLines = if (expanded) Int.MAX_VALUE else 2,
                            overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                            onTextLayout = { layoutResult ->
                                if (!expanded && layoutResult.hasVisualOverflow != singleLineOverflow) {
                                    singleLineOverflow = layoutResult.hasVisualOverflow
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
