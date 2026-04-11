package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R

/**
 * Staff dashboard only: mirrors [com.example.placementprojectmp.ui.screens.shared.component.TopPerformerCard]
 * shell, padding, expand/collapse toggle, and image rows/grid; title column uses bold header + secondary subheader
 * (no role bullet column).
 */
@Composable
fun StaffDashboardTopPerformerCard(
    modifier: Modifier = Modifier,
    stateKey: Any = Unit,
    header: String,
    subheader: String,
    imageResIds: List<Int>,
    performerNames: List<String>,
    performerSubtitles: List<String>
) {
    val images = imageResIds.paddedToSixStaff(defaultDrawable = R.drawable.std_1)
    val names = performerNames.paddedToSixStaff(default = "—")
    val subtitles = performerSubtitles.paddedToSixStaff(default = " ")
    val gridRows = remember(images, names, subtitles) {
        List(6) { i ->
            Triple(images[i], names[i], subtitles[i])
        }.chunked(3)
    }

    var expanded by remember(stateKey) { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x8000D4FF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = header,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subheader,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black.copy(alpha = 0.72f),
                    fontWeight = FontWeight.Normal
                )
            }
            if (!expanded) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy((-10).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    images.forEach { img ->
                        Image(
                            painter = painterResource(id = img),
                            contentDescription = "Top performer",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            } else {
                gridRows.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowItems.forEach { (resId, name, yearText) ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = "Top performer",
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = yearText,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black.copy(alpha = 0.8f)
                                )
                            }
                        }
                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier.width(0.dp))
                        }
                    }
                }
            }
        }
    }
}

private fun List<Int>.paddedToSixStaff(defaultDrawable: Int): List<Int> {
    if (isEmpty()) return List(6) { defaultDrawable }
    return List(6) { this[it % size] }
}

private fun List<String>.paddedToSixStaff(default: String): List<String> {
    if (isEmpty()) return List(6) { default }
    return List(6) { this[it % size] }
}
