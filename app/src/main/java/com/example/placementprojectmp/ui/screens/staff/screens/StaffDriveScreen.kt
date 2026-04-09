package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.theme.NeonBlue
import com.example.placementprojectmp.ui.theme.colormap.ColorMapper
import com.example.placementprojectmp.ui.theme.colormap.CompanyStatus
import kotlin.random.Random

private data class DriveCompanyUiModel(
    val name: String,
    val location: String,
    val status: CompanyStatus,
    val industry: String,
    val companyType: String,
    val website: String,
    val email: String,
    val hrName: String,
    val hrPhone: String,
    val description: String,
    val logoResId: Int = R.drawable.comp_1,
    val driveName: String = "Winter Internship Drive",
    val driveDate: String = "22 Aug, 10:00 AM"
)

@Composable
fun StaffDriveScreen(
    modifier: Modifier = Modifier
) {
    val companies = remember { sampleCompanies() }
    val uriHandler = LocalUriHandler.current
    var headerMode by remember { mutableStateOf(0) } // 0: all companies, 1: company name, 2: drive view

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Manage",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Companies and Drives",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CompanyLogoCard(
                        modifier = Modifier
                            .weight(0.3f)
                            .height(140.dp),
                        logoResId = companies.firstOrNull()?.logoResId ?: R.drawable.comp_1
                    )
                    StaffCompanyInfoCard(
                        modifier = Modifier
                            .weight(0.7f)
                            .height(140.dp),
                        company = companies.firstOrNull() ?: return@item,
                        onWebsiteClick = { url -> uriHandler.openUri(url) },
                        onCycle = { headerMode = (headerMode + 1) % 3 }
                    )
                }
            }

            item {
                val title = when (headerMode) {
                    1 -> companies.firstOrNull()?.name ?: "All Companies"
                    2 -> companies.firstOrNull()?.driveName ?: "All Companies"
                    else -> "All Companies"
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = if (headerMode == 2) FontWeight.Bold else FontWeight.Medium
                    )
                    if (headerMode == 0) {
                        Text(
                            text = "Account",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Divider(modifier = Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            }

            items(companies) { company ->
                StaffCompanyInfoCard(
                    modifier = Modifier.fillMaxWidth(),
                    company = company,
                    onWebsiteClick = { url -> uriHandler.openUri(url) },
                    onCycle = { headerMode = (headerMode + 1) % 3 }
                )
            }
        }
    }
}

private fun sampleCompanies(): List<DriveCompanyUiModel> = listOf(
    DriveCompanyUiModel(
        name = "Nexora Systems",
        location = "Bangalore, India",
        status = CompanyStatus.ACTIVE,
        industry = "Technology",
        companyType = "Product",
        website = "https://www.nexora.systems",
        email = "hr@nexora.systems",
        hrName = "Sarah Parker",
        hrPhone = "+91 9876543210",
        description = "Nexora Systems focuses on campus technology hiring, scalable backend systems, and product engineering.",
        logoResId = R.drawable.comp_1,
        driveName = "Nexora Hiring Drive",
        driveDate = "22 Aug, 10:00 AM"
    ),
    DriveCompanyUiModel(
        name = "Vertex Labs",
        location = "Hyderabad, India",
        status = CompanyStatus.VISITING_SOON,
        industry = "E-commerce & Cloud",
        companyType = "Product",
        website = "https://www.vertexlabs.ai",
        email = "hr@vertexlabs.ai",
        hrName = "Rahul Mehta",
        hrPhone = "+91 9988776655",
        description = "Vertex Labs runs internship and full-time drives for cloud engineering and data-focused roles.",
        logoResId = R.drawable.comp_2,
        driveName = "Vertex SDE Drive",
        driveDate = "26 Aug, 02:00 PM"
    ),
    DriveCompanyUiModel(
        name = "Asterion Tech",
        location = "Hyderabad, India",
        status = CompanyStatus.ACTIVE,
        industry = "Technology",
        companyType = "Product",
        website = "https://www.asterion.tech",
        email = "careers@asterion.tech",
        hrName = "Ananya Rao",
        hrPhone = "+91 9012345678",
        description = "Asterion Tech hires for full-stack and AI roles with a strong focus on product ownership.",
        logoResId = R.drawable.comp_3,
        driveName = "Asterion Campus Drive",
        driveDate = "01 Sep, 11:30 AM"
    ),
    DriveCompanyUiModel(
        name = "BlueOrbit Digital",
        location = "Noida, India",
        status = CompanyStatus.PENDING_APPROVAL,
        industry = "Digital Media",
        companyType = "Product",
        website = "https://www.blueorbit.digital",
        email = "jobs@blueorbit.digital",
        hrName = "Kunal Singh",
        hrPhone = "+91 9123456780",
        description = "BlueOrbit Digital builds design and analytics platforms with regular graduate intake.",
        logoResId = R.drawable.comp_1,
        driveName = "BlueOrbit Drive",
        driveDate = "07 Sep, 09:30 AM"
    ),
    DriveCompanyUiModel(
        name = "CoreNova Services",
        location = "Mysore, India",
        status = CompanyStatus.INACTIVE,
        industry = "IT Services",
        companyType = "Service Based",
        website = "https://www.corenova.services",
        email = "campus@corenova.services",
        hrName = "Neha Kulkarni",
        hrPhone = "+91 9345678901",
        description = "CoreNova Services offers structured onboarding and placement opportunities across service teams.",
        logoResId = R.drawable.comp_2,
        driveName = "CoreNova Graduate Drive",
        driveDate = "14 Sep, 01:00 PM"
    )
)

@Composable
private fun CompanyLogoCard(
    modifier: Modifier = Modifier,
    logoResId: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = logoResId),
                contentDescription = "Company logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
    }
}

@Composable
private fun StaffCompanyInfoCard(
    modifier: Modifier = Modifier,
    company: DriveCompanyUiModel,
    onWebsiteClick: (String) -> Unit,
    onCycle: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1.0f else 0.98f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "card_scale"
    )
    val backgroundColor by androidx.compose.animation.animateColorAsState(
        targetValue = MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "card_bg"
    )

    Card(
        modifier = modifier
            .scale(scale),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        interactionSource = interactionSource,
        onClick = { onCycle() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(end = 26.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CompanyHeaderSection(company = company, isExpanded = isExpanded)

                Crossfade(
                    targetState = isExpanded,
                    animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
                    label = "company_content"
                ) { expanded ->
                    if (expanded) {
                        CompanyDescriptionSection(company = company)
                    } else {
                        CompanyContactSection(
                            company = company,
                            onWebsiteClick = onWebsiteClick
                        )
                    }
                }
            }

            GlassInteractionButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                expanded = isExpanded,
                onClick = { isExpanded = !isExpanded }
            )

            Column(
                modifier = Modifier.align(Alignment.BottomEnd),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                androidx.compose.animation.AnimatedVisibility(visible = !isExpanded) {
                    HRContactMini(
                        name = company.hrName,
                        phone = company.hrPhone,
                        email = company.email
                    )
                }

                androidx.compose.animation.AnimatedVisibility(visible = isExpanded) {
                    Text(
                        text = company.website.removePrefix("https://").removePrefix("http://"),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { onWebsiteClick(company.website) }
                    )
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(240)),
                exit = shrinkVertically(animationSpec = tween(200)),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(NeonBlue)
                        .padding(12.dp)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.TopStart),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(ColorMapper.getColor(company.status))
                        )
                        Text(
                            text = "Company Details",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = company.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CompanyHeaderSection(
    company: DriveCompanyUiModel,
    isExpanded: Boolean
) {
    val borderColor by androidx.compose.animation.animateColorAsState(
        targetValue = if (isExpanded) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
        },
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "header_border"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = company.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "\uD83D\uDCCD",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = company.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            StatusDot(
                status = company.status
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Industry",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = company.industry,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Company Type",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = company.companyType,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "HR Name",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = company.hrName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = company.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = company.website.removePrefix("https://").removePrefix("http://"),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
private fun CompanyContactSection(
    company: DriveCompanyUiModel,
    onWebsiteClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.04f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Website:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onWebsiteClick(company.website) }
            ) {
                Text(
                    text = company.website.removePrefix("https://").removePrefix("http://"),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(4.dp))
                Icon(
                    imageVector = Icons.Default.ArrowOutward,
                    contentDescription = "Open website",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Email:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = company.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Divider(
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    }
}

@Composable
private fun CompanyDescriptionSection(
    company: DriveCompanyUiModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.04f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = company.driveName,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = company.driveDate,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HRContactMini(
    name: String,
    phone: String,
    email: String
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = email,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = phone,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun GlassInteractionButton(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (expanded) 1.0f else 1.05f,
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "glass_scale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(999.dp))
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Expand",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun StatusDot(
    status: CompanyStatus,
    modifier: Modifier = Modifier
) {
    val color by androidx.compose.animation.animateColorAsState(
        targetValue = ColorMapper.getColor(status),
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "status_color"
    )
    Box(
        modifier = modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(color)
    )
}

