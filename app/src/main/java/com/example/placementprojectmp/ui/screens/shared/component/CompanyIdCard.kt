package com.example.placementprojectmp.ui.screens.shared.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R

/**
 * Company identity block: overview row (logo, name, tagline, optional placement/role), location, email, website link,
 * and metadata rows — pixel-aligned with the former Company Details drawer sections.
 */
@Composable
fun CompanyIdCard(
    modifier: Modifier = Modifier,
    placementName: String? = null,
    betweenOverviewAndDetails: (@Composable () -> Unit)? = null,
    onAddNoteClick: () -> Unit,
    companyLogoResId: Int = R.drawable.comp_1,
    @Suppress("UNUSED_PARAMETER") statusColor: Color = Color(0xFF2E7D32),
    @Suppress("UNUSED_PARAMETER") statusLabel: String = "Active",
    companyName: String = "NEXORA SYSTEMS",
    companyTagline: String = "Campus hiring and talent acceleration",
    primaryLocation: String = "Bangalore, India",
    secondaryLocation: String = "Mountain View, USA",
    email: String = "careers@nexora.systems",
    websiteDisplay: String = "www.nexora.systems",
    industryType: String = "Technology",
    companyType: String = "Product Based",
    globalOps: String = "APAC + North America"
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            val interactionSource = remember { MutableInteractionSource() }
            val pressed by interactionSource.collectIsPressedAsState()
            val scale by animateFloatAsState(
                targetValue = if (pressed) 0.98f else 1f,
                animationSpec = tween(durationMillis = 160, easing = FastOutSlowInEasing),
                label = "overview_scale"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scale),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                val logoSide = 128.dp
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(logoSide),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.22f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    interactionSource = interactionSource,
                    onClick = onAddNoteClick
                ) {
                    Image(
                        painter = painterResource(id = companyLogoResId),
                        contentDescription = "Company",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                val placementStyle = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Normal
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(logoSide)
                        .padding(bottom = 2.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = companyName,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = companyTagline,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    if (!placementName.isNullOrBlank()) {
                        Text(
                            text = placementName,
                            style = placementStyle,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        betweenOverviewAndDetails?.invoke()

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = primaryLocation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
            Text(
                text = secondaryLocation,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = email,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.78f)
                    .clickable { }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = websiteDisplay,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowOutward,
                        contentDescription = "Open website",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(210.dp)
                        .padding(top = 2.dp),
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 2.dp
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            MetadataRow(label = "Industry Type", value = industryType)
            MetadataRow(label = "Company Type", value = companyType)
            MetadataRow(label = "Global Ops", value = globalOps)
        }
    }
}

@Composable
private fun MetadataRow(
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .width(6.dp)
                .height(28.dp)
                .background(Color(0xFF00D4FF), RoundedCornerShape(99.dp))
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}
