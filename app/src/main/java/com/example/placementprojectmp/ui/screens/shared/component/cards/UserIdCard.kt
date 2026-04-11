package com.example.placementprojectmp.ui.screens.shared.component.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.theme.NeonBlue

/**
 * Staff/student identity row card: portrait, identity lines, optional role capsule,
 * optional key–value rows, favorite control, and tag strip — list layout only.
 */
@Composable
fun UserIdCard(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    idText: String,
    profileImageResId: Int,
    searchQuery: String = "",
    departmentValue: String,
    secondaryAttributeLabel: String,
    secondaryAttributeValue: String,
    optionalEndTag: String? = null,
    tags: List<Pair<String, androidx.compose.ui.graphics.Color>> = emptyList(),
    selected: Boolean = false,
    onSelectionChange: (Boolean) -> Unit = {},
    isFavorite: Boolean = false,
    onFavoriteToggle: () -> Unit = {}
) {
    var tagsExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (selected) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                else Modifier
            )
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (tags.isEmpty()) 0.dp else 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { onSelectionChange(!selected) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(profileImageResId),
                        contentDescription = "Profile",
                        modifier = Modifier.size(56.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelectionChange(!selected) }
                ) {
                    HighlightedText(
                        text = name,
                        query = searchQuery,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HighlightedText(
                        text = email,
                        query = searchQuery,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HighlightedText(
                            text = idText,
                            query = searchQuery,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )
                        if (optionalEndTag != null) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = optionalEndTag,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                                        shape = RoundedCornerShape(999.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                IconButton(
                    onClick = onFavoriteToggle,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            UserIdKeyValueRow(
                label = "Department",
                value = departmentValue
            )
            Spacer(modifier = Modifier.height(6.dp))
            UserIdKeyValueRow(
                label = secondaryAttributeLabel,
                value = secondaryAttributeValue
            )
        }
        if (tags.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickable { tagsExpanded = !tagsExpanded }
                    .animateContentSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                tags.take(3).forEach { (_, c) ->
                    Box(
                        modifier = Modifier
                            .size(width = 16.dp, height = 6.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(c.copy(alpha = 0.9f))
                    )
                }
                AnimatedVisibility(visible = tagsExpanded) {
                    Text(
                        text = tags.take(3).joinToString(" | ") { it.first },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun UserIdKeyValueRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
        )
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
