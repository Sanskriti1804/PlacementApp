package com.example.placementprojectmp.ui.screens.student.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R

@Composable
fun AiChatButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(end = 20.dp, bottom = 24.dp),
        containerColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp
        )
    ) {
        Image(
            painter = painterResource(R.drawable.ic_aii),
            contentDescription = "AI Chatbot",
            modifier = Modifier.size(24.dp),
            contentScale = ContentScale.Crop
        )
    }
}
