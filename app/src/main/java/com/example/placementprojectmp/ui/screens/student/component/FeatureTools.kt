package com.example.placementprojectmp.ui.screens.student.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.FeatureCard

private val FeatureToolComingSoonColor = Color(0xFF00D4FF)

data class FeatureTool(
    val label: String,
    val imageVector: ImageVector,
    val onClick: () -> Unit = {},
    val showComingSoonLabel: Boolean = false
)

/**
 * "Feature Tools" heading and horizontal [FeatureCard] row, matching the Student Dashboard section.
 * [FeatureTool.showComingSoonLabel] draws an optional bottom-end "COMING SOON" overlay without changing
 * layout when false.
 */
@Composable
fun FeatureTools(
    modifier: Modifier = Modifier,
    title: String = "Feature Tools",
    featureTools: List<FeatureTool>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = featureTools,
                key = { it.label }
            ) { item ->
                if (item.showComingSoonLabel) {
                    Box(modifier = Modifier.width(100.dp)) {
                        FeatureCard(
                            modifier = Modifier.fillMaxWidth(),
                            label = item.label,
                            imageVector = item.imageVector,
                            onClick = item.onClick
                        )
                        Text(
                            text = "COMING SOON",
                            modifier = Modifier.align(Alignment.BottomEnd),
                            style = MaterialTheme.typography.labelSmall,
                            color = FeatureToolComingSoonColor,
                            fontWeight = FontWeight.Light
                        )
                    }
                } else {
                    FeatureCard(
                        modifier = Modifier.width(100.dp),
                        label = item.label,
                        imageVector = item.imageVector,
                        onClick = item.onClick
                    )
                }
            }
        }
    }
}
