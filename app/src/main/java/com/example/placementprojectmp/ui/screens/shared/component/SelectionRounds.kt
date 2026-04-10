package com.example.placementprojectmp.ui.screens.shared.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

data class SelectionRoundItem(
    val title: String,
    val date: String,
    val isCompleted: Boolean
)

private val cardCornerRadius = 12.dp
private val cardMinHeight = 48.dp
private val cardHorizontalPadding = 14.dp
private val cardVerticalPadding = 10.dp
private val rowSpacing = 8.dp
private val titleEndGutterForDate = 64.dp

/**
 * Vertical stack of compact round rows (up to six). Active/upcoming uses [MaterialTheme.colorScheme.primary];
 * completed uses [MaterialTheme.colorScheme.surfaceVariant].
 */
@Composable
fun SelectionRounds(
    rounds: List<SelectionRoundItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(rowSpacing)
    ) {
        rounds.take(6).forEach { item ->
            SelectionRoundRow(item = item)
        }
    }
}

@Composable
private fun SelectionRoundRow(
    item: SelectionRoundItem,
    modifier: Modifier = Modifier
) {
    val completed = item.isCompleted
    val containerColor = if (completed) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.primary
    }
    val titleColor = if (completed) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }
    val dateColor = if (completed) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.78f)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = cardMinHeight)
                .padding(
                    horizontal = cardHorizontalPadding,
                    vertical = cardVerticalPadding
                )
        ) {
            Text(
                text = item.title,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = titleEndGutterForDate),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = titleColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.date,
                modifier = Modifier.align(Alignment.BottomEnd),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                color = dateColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
