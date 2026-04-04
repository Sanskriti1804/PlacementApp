package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R

/**
 * Reusable top bar: menu (left), app name (center), notification (right).
 * Icons use drawable; replace with your assets later.
 */
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "Opportunne",
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    menuIconResId: Int = 0,
    notificationIconResId: Int = 0
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onMenuClick),
            contentAlignment = Alignment.Center
        ) {
            if (menuIconResId != 0) {
                Icon(
                    painter = painterResource(menuIconResId),
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onNotificationClick),
            contentAlignment = Alignment.Center
        ) {
            if (notificationIconResId != 0) {
                Icon(
                    painter = painterResource(notificationIconResId),
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
