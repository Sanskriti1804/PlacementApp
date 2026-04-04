package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Horizontally scrollable multi-select domain chips.
 */
@Composable
fun DomainChipRow(
    modifier: Modifier = Modifier,
    domains: List<String> = listOf(
        "Software",
        "Marketing",
        "Design",
        "Data Science",
        "AI",
        "Finance"
    ),
    selectedDomains: Set<String> = emptySet(),
    onDomainToggle: (String) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        items(domains.size) { index ->
            val domain = domains[index]
            val selected = domain in selectedDomains
            Text(
                text = domain,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .then(
                        if (selected)
                            Modifier.background(MaterialTheme.colorScheme.primary)
                        else
                            Modifier
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                    RoundedCornerShape(20.dp)
                                )
                    )
                    .clickable { onDomainToggle(domain) }
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}
