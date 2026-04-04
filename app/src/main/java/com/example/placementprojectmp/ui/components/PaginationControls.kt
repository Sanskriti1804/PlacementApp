package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Pagination: ← 1 2 3 ... N →
 * Left/right arrows, page numbers; onPageSelected(page).
 */
@Composable
fun PaginationControls(
    modifier: Modifier = Modifier,
    currentPage: Int,
    totalPages: Int,
    onPageSelected: (Int) -> Unit = {}
) {
    if (totalPages <= 0) return

    Row(
        modifier = modifier.padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(enabled = currentPage > 1) { onPageSelected((currentPage - 1).coerceAtLeast(1)) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Previous",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val pagesToShow = buildList {
                add(1)
                if (currentPage > 2) add(-1)
                if (currentPage in 2..totalPages - 1) add(currentPage)
                if (currentPage < totalPages - 1) add(-1)
                if (totalPages > 1) add(totalPages)
            }.distinct()

            pagesToShow.forEach { page ->
                if (page == -1) {
                    Text(
                        text = "...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    val isCurrent = page == currentPage
                    Text(
                        text = "$page",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isCurrent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { onPageSelected(page) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(enabled = currentPage < totalPages) { onPageSelected((currentPage + 1).coerceAtMost(totalPages)) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Next",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
