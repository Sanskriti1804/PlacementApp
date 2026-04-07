package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    handle: String = "user@edu.com",
    avatarResId: Int = R.drawable.pfp_user,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(
                    onClick = onClick,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                else Modifier
            ),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .weight(0.3f)
                .height(176.dp)
                .padding(end = 6.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.85f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(avatarResId),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        ProfileIdCard(
            modifier = Modifier
                .weight(0.7f)
                .height(120.dp),
            userName = userName,
            role = role,
            handle = handle
        )
    }
}
