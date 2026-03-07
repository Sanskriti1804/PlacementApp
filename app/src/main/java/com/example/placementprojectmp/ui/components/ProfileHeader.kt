package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R

/**
 * Profile header row: 30% avatar, 70% ProfileIdCard.
 * Reuses ProfileIdCard; avatar uses drawable or placeholder.
 */
@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    userName: String = "Alex Johnson",
    role: String = "Android Developer",
    handle: String = "@alexdev",
    avatarResId: Int = R.drawable.app_logo
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .weight(0.3f)
                .padding(end = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.85f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(avatarResId),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        ProfileIdCard(
            modifier = Modifier.weight(0.7f),
            userName = userName,
            role = role,
            handle = handle
        )
    }
}
