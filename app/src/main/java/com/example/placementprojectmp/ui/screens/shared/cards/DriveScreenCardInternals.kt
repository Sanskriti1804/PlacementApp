package com.example.placementprojectmp.ui.screens.shared.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.viewmodel.JobDepartment
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun LogoImage(
    logoResId: Int,
    modifier: Modifier = Modifier,
    containerSize: Dp = 42.dp
) {
    Box(
        modifier = modifier
            .size(containerSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = logoResId),
            contentDescription = "Company logo",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

/** Outlined capsule chip shared by Drive and Job cards (same visual as drive branch chips). */
@Composable
internal fun DriveStyleCapsuleChip(label: String) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
internal fun BottomActionRow(
    dateLabel: String,
    date: LocalDate,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateText(label = dateLabel, date = date)
        OutlinedButton(onClick = onButtonClick, shape = RoundedCornerShape(14.dp)) {
            Text(text = buttonText)
        }
    }
}

@Composable
internal fun DateText(
    label: String,
    date: LocalDate
) {
    Text(
        text = "$label: ${date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
internal fun StatusChip(
    department: JobDepartment
) {
    val color = when (department) {
        JobDepartment.TECH -> Color(0xFF1E88E5)
        JobDepartment.MANAGEMENT -> Color(0xFF8E24AA)
        JobDepartment.CORE -> Color(0xFFEF6C00)
    }
    AssistChip(
        onClick = {},
        label = {
            Text(
                text = department.name.lowercase().replaceFirstChar { it.uppercase() },
                color = color
            )
        },
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    )
}
