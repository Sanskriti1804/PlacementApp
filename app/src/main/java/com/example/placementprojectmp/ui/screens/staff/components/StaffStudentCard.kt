package com.example.placementprojectmp.ui.screens.staff.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.theme.NeonBlue

@Composable
fun StaffStudentCardList(
    modifier: Modifier = Modifier,
    studentName: String,
    studentEmail: String,
    studentRollNumber: String,
    profileImageResId: Int,
    searchQuery: String = "",
    selected: Boolean = false,
    isFavorite: Boolean = false,
    onSelectionChange: (Boolean) -> Unit = {},
    onFavoriteToggle: () -> Unit = {},
    tags: List<Pair<String, androidx.compose.ui.graphics.Color>> = emptyList()
) {
    var tagsExpanded by remember { mutableStateOf(false) }
    val collapseInteraction = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (selected) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                else Modifier
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selected,
                onCheckedChange = onSelectionChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(profileImageResId),
                    contentDescription = "Profile",
                    modifier = Modifier.size(56.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = collapseInteraction,
                        indication = null
                    ) { tagsExpanded = false }
            ) {
                HighlightedText(
                    text = studentName,
                    query = searchQuery,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                HighlightedText(
                    text = studentEmail,
                    query = searchQuery,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                HighlightedText(
                    text = studentRollNumber,
                    query = searchQuery,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
            }
            IconButton(
                onClick = onFavoriteToggle,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(26.dp),
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (tags.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { tagsExpanded = !tagsExpanded },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        tags.take(3).forEach { (_, c) ->
                            Box(
                                modifier = Modifier
                                    .size(width = 16.dp, height = 6.dp)
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(c.copy(alpha = 0.9f))
                            )
                        }
                    }
                }
                if (tagsExpanded) {
                    Text(
                        text = tags.take(3).joinToString(" | ") { it.first },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StaffStudentCardGrid(
    modifier: Modifier = Modifier,
    studentName: String,
    studentEmail: String,
    studentRollNumber: String,
    profileImageResId: Int,
    searchQuery: String = "",
    selected: Boolean = false,
    isFavorite: Boolean = false,
    onSelectionChange: (Boolean) -> Unit = {},
    onFavoriteToggle: () -> Unit = {},
    tags: List<Pair<String, androidx.compose.ui.graphics.Color>> = emptyList()
) {
    var tagsExpanded by remember { mutableStateOf(false) }
    val collapseInteraction = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (selected) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                else Modifier
            )
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.padding(top = 0.dp)
            ) {
                Checkbox(
                    checked = selected,
                    onCheckedChange = onSelectionChange,
                    modifier = Modifier.size(20.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.outline
                    )
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onFavoriteToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Favorite",
                        modifier = Modifier.size(22.dp),
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.size(22.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(profileImageResId),
                    contentDescription = "Profile",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = collapseInteraction,
                    indication = null
                ) { tagsExpanded = false },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HighlightedText(
                text = studentName,
                query = searchQuery,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            HighlightedText(
                text = studentEmail,
                query = searchQuery,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            HighlightedText(
                text = studentRollNumber,
                query = searchQuery,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
        if (tags.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { tagsExpanded = !tagsExpanded },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        tags.take(3).forEach { (_, c) ->
                            Box(
                                modifier = Modifier
                                    .size(width = 14.dp, height = 5.dp)
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(c.copy(alpha = 0.9f))
                            )
                        }
                    }
                }
                if (tagsExpanded) {
                    Text(
                        text = tags.take(3).joinToString(" | ") { it.first },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HighlightedText(
    text: String,
    query: String,
    style: androidx.compose.ui.text.TextStyle,
    color: androidx.compose.ui.graphics.Color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val annotated = rememberHighlightedText(text, query)
    Text(
        text = annotated,
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

private fun rememberHighlightedText(text: String, query: String): AnnotatedString {
    if (query.isBlank()) return AnnotatedString(text)
    val source = text.lowercase()
    val target = query.lowercase().trim()
    val first = source.indexOf(target)
    if (first < 0) return AnnotatedString(text)
    return buildAnnotatedString {
        append(text)
        addStyle(
            style = SpanStyle(background = NeonBlue.copy(alpha = 0.16f)),
            start = first,
            end = first + target.length
        )
    }
}
