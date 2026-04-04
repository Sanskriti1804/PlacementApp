package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Floating bottom action bar when multi-select is active.
 * Actions: Delete, Favorite, Move, More. Smooth slide-in/out animation.
 */
@Composable
fun BulkActionBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    selectedCount: Int,
    onDelete: () -> Unit = {},
    onFavorite: () -> Unit = {},
    onMove: () -> Unit = {},
    onMore: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$selectedCount selected",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = onDelete) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
            TextButton(onClick = onFavorite) {
                Text("Favorite", color = MaterialTheme.colorScheme.primary)
            }
            TextButton(onClick = onMove) {
                Text("Move", color = MaterialTheme.colorScheme.primary)
            }
            TextButton(onClick = onMore) {
                Text("More", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
