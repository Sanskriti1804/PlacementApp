package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.ArrowUpward
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.components.AppTopBar
import com.example.placementprojectmp.ui.components.GreetingSection

private data class DriveCompanyUiModel(
    val name: String,
    val location: String,
    val status: DriveStatus,
    val industry: String,
    val companyType: String,
    val website: String,
    val email: String,
    val hrName: String,
    val hrPhone: String,
    val description: String,
    val logoResId: Int = R.drawable.app_logo
)

private enum class DriveStatus(val dotColor: Color, val label: String) {
    Active(Color(0xFF2E7D32), "Active"),
    Participating(Color(0xFFFBC02D), "Participating"),
    Inactive(Color(0xFF9E9E9E), "Inactive")
}

@Composable
fun StaffDriveScreen(
    modifier: Modifier = Modifier
) {
    val companies = remember { sampleCompanies() }
    val uriHandler = LocalUriHandler.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                title = "Company Drives",
                onMenuClick = {},
                onNotificationClick = {}
            )
        }
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
                    GreetingSection(
                        userName = "Dr. Priya Sharma"
                    )
                    Text(
                        text = "Manage companies and recruitment drive",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CompanyCountCard(
                        modifier = Modifier
                            .weight(0.1f)
                            .height(140.dp),
                        totalCompanies = companies.size
                    )
                    CompanyLogoCard(
                        modifier = Modifier
                            .weight(0.2f)
                            .height(140.dp),
                        logoResId = companies.firstOrNull()?.logoResId ?: R.drawable.app_logo
                    )
                    StaffCompanyInfoCard(
                        modifier = Modifier
                            .weight(0.7f)
                            .height(140.dp),
                        company = companies.firstOrNull() ?: return@item,
                        onWebsiteClick = { url -> uriHandler.openUri(url) }
                    )
                }
            }

            item {
                Text(
                    text = "All Companies",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            items(companies) { company ->
                StaffCompanyInfoCard(
                    modifier = Modifier.fillMaxWidth(),
                    company = company,
                    onWebsiteClick = { url -> uriHandler.openUri(url) }
                )
            }
        }
    }
}

private fun sampleCompanies(): List<DriveCompanyUiModel> = listOf(
    DriveCompanyUiModel(
        name = "Google",
        location = "Bangalore, India",
        status = DriveStatus.Active,
        industry = "Technology",
        companyType = "Product Based",
        website = "https://www.google.com",
        email = "hr@google.com",
        hrName = "Sarah Parker",
        hrPhone = "+91 9876543210",
        description = "Google is a multinational technology company specializing in search, cloud computing, AI, and digital products."
    ),
    DriveCompanyUiModel(
        name = "Amazon",
        location = "Hyderabad, India",
        status = DriveStatus.Participating,
        industry = "E-commerce & Cloud",
        companyType = "Product Based",
        website = "https://www.amazon.in",
        email = "hr@amazon.com",
        hrName = "Rahul Mehta",
        hrPhone = "+91 9988776655",
        description = "Amazon focuses on e-commerce, cloud services, and digital streaming with large campus hiring programs."
    ),
    DriveCompanyUiModel(
        name = "Microsoft",
        location = "Hyderabad, India",
        status = DriveStatus.Active,
        industry = "Technology",
        companyType = "Product Based",
        website = "https://www.microsoft.com",
        email = "careers@microsoft.com",
        hrName = "Ananya Rao",
        hrPhone = "+91 9012345678",
        description = "Microsoft builds operating systems, productivity tools, cloud platforms, and hires across multiple engineering roles."
    ),
    DriveCompanyUiModel(
        name = "Adobe",
        location = "Noida, India",
        status = DriveStatus.Participating,
        industry = "Digital Media",
        companyType = "Product Based",
        website = "https://www.adobe.com",
        email = "jobs@adobe.com",
        hrName = "Kunal Singh",
        hrPhone = "+91 9123456780",
        description = "Adobe creates digital media and marketing solutions, focusing on design and content creation tools."
    ),
    DriveCompanyUiModel(
        name = "Infosys",
        location = "Mysore, India",
        status = DriveStatus.Inactive,
        industry = "IT Services",
        companyType = "Service Based",
        website = "https://www.infosys.com",
        email = "campus@infosys.com",
        hrName = "Neha Kulkarni",
        hrPhone = "+91 9345678901",
        description = "Infosys is a global leader in consulting and IT services with strong campus engagement."
    )
)

@Composable
private fun CompanyCountCard(
    modifier: Modifier = Modifier,
    totalCompanies: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy((-10).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(3) {
                    LogoBubble()
                }
            }
            Text(
                text = "+$totalCompanies Companies",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun LogoBubble(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Company logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(20.dp)
        )
    }
}

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
    onWebsiteClick: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1.0f else 0.98f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "card_scale"
    )
    val backgroundColor by androidx.compose.animation.animateColorAsState(
        targetValue = if (isExpanded) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surface,
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
        onClick = { isExpanded = !isExpanded }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(end = 40.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                modifier = Modifier.align(Alignment.Center),
                expanded = isExpanded,
                onClick = { isExpanded = !isExpanded }
            )

            Column(
                modifier = Modifier.align(Alignment.BottomEnd),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AnimatedVisibility(visible = !isExpanded) {
                    HRContactMini(
                        name = company.hrName,
                        phone = company.hrPhone
                    )
                }

                AnimatedVisibility(visible = isExpanded) {
                    Icon(
                        imageVector = Icons.Default.ArrowOutward,
                        contentDescription = "Open website",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onWebsiteClick(company.website) }
                    )
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
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.04f))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
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
            text = "About Company",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = company.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HRContactMini(
    name: String,
    phone: String
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "HR: $name",
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
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "Expand",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun StatusDot(
    status: DriveStatus,
    modifier: Modifier = Modifier
) {
    val color by androidx.compose.animation.animateColorAsState(
        targetValue = status.dotColor,
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "status_color"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = status.label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

