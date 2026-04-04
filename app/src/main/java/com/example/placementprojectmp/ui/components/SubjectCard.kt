package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Subject card: icon, subject name, professor, percentage. Expands on tap to show detail rows.
 * Expanded state uses accent background; smooth expand/collapse animation.
 */
@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    subjectName: String,
    professorName: String,
    displayValue: String,
    iconResId: Int = R.drawable.app_logo,
    details: List<Pair<String, String>> = emptyList(),
    expanded: Boolean = false,
    onToggle: () -> Unit = {}
) {
    val backgroundColor = if (expanded)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.surfaceVariant
    val onBackgroundColor = if (expanded)
        MaterialTheme.colorScheme.onPrimaryContainer
    else
        MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(onClick = onToggle)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    if (iconResId != 0) {
                        androidx.compose.material3.Icon(
                            painter = painterResource(iconResId),
                            contentDescription = null,
                            tint = onBackgroundColor,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = subjectName.take(1).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = onBackgroundColor
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = subjectName,
                        style = MaterialTheme.typography.titleMedium,
                        color = onBackgroundColor
                    )
                    Text(
                        text = professorName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = onBackgroundColor.copy(alpha = 0.85f)
                    )
                }
            }
            Text(
                text = displayValue,
                style = MaterialTheme.typography.titleMedium,
                color = onBackgroundColor
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                details.forEach { (label, value) ->
                    SubjectDetailItem(
                        label = label,
                        value = value,
                        textColor = onBackgroundColor
                    )
                }
            }
        }
    }
}
