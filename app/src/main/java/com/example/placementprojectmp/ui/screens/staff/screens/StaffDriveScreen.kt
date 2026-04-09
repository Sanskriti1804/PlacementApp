package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
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
import com.example.placementprojectmp.ui.theme.colormap.DriveStatus

private data class DriveCompanyUiModel(
    val name: String,
    val location: String,
    val status: CompanyStatus,
    val industry: String,
    val technology: String,
    val companyType: String,
    val website: String,
    val email: String,
    val hrName: String,
    val hrPhone: String,
    val description: String,
    val logoResId: Int,
    val driveName: String,
    val driveDate: String
)

@Composable
fun StaffDriveScreen(
    modifier: Modifier = Modifier
) {
    val companies = remember { sampleCompanies() }
    val uriHandler = LocalUriHandler.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Manage",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Companies and Drives",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Companies",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Count ${companies.size}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Divider(modifier = Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            }

            items(companies) { company ->
                CompanyDriveCarouselItem(
                    company = company,
                    onWebsiteClick = { url -> uriHandler.openUri(url) }
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
        technology = "Kotlin, Cloud",
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
        industry = "AI Infrastructure",
        technology = "Python, Data",
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
        location = "Noida, India",
        status = CompanyStatus.PENDING_APPROVAL,
        industry = "Platform Engineering",
        technology = "Java, React",
        companyType = "Product",
        website = "https://www.asterion.tech",
        email = "careers@asterion.tech",
        hrName = "Ananya Rao",
        hrPhone = "+91 9012345678",
        description = "Asterion Tech hires for full-stack and AI roles with a strong focus on product ownership.",
        logoResId = R.drawable.comp_3,
        driveName = "Asterion Campus Drive",
        driveDate = "01 Sep, 11:30 AM"
    )
)

@Composable
private fun CompanyDriveCarouselItem(
    company: DriveCompanyUiModel,
    onWebsiteClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            CompanyCard(
                modifier = Modifier.fillParentMaxWidth(0.9f),
                company = company,
                onWebsiteClick = onWebsiteClick
            )
        }
        item {
            DriveCard(
                modifier = Modifier.fillParentMaxWidth(0.9f),
                company = company
            )
        }
    }
}

@Composable
private fun CompanyCard(
    modifier: Modifier,
    company: DriveCompanyUiModel,
    onWebsiteClick: (String) -> Unit
) {
    var showOverlay by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val scale by animateFloatAsState(
        targetValue = if (showOverlay) 1.0f else 0.98f,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "company_card_scale"
    )
    Card(
        modifier = modifier.scale(scale),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        interactionSource = interactionSource,
        onClick = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(ColorMapper.getColor(company.status))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = company.logoResId),
                            contentDescription = "Company logo",
                            modifier = Modifier.size(28.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = company.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = company.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                CompanyField("Industry", company.industry)
                CompanyField("Technology", company.technology)
                CompanyField("Company Type", company.companyType)
                Text(
                    text = company.website.removePrefix("https://").removePrefix("http://"),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onWebsiteClick(company.website) }
                )
                Text(
                    text = company.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                Text(
                    text = "HR = ${company.hrName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = company.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = company.hrPhone,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape)
                    .background(NeonBlue)
                    .clickable { showOverlay = !showOverlay }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Show details",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = showOverlay,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                enter = expandVertically(animationSpec = tween(220)),
                exit = shrinkVertically(animationSpec = tween(200))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(NeonBlue)
                        .clickable { showOverlay = false }
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
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
private fun DriveCard(
    modifier: Modifier,
    company: DriveCompanyUiModel
) {
    val driveStatus = if (company.name.length % 2 == 0) DriveStatus.ONGOING else DriveStatus.COMPLETED
    val registrationStatus = if (company.name.length % 3 == 0) DriveStatus.REGISTRATION_CLOSED else DriveStatus.REGISTRATION_OPEN
    val roles = listOf("SDE Intern", "Backend Intern", "QA Engineer", "Web Developer")
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(ColorMapper.getColor(driveStatus))
                )
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(ColorMapper.getColor(registrationStatus))
                )
            }
            Text(
                text = company.driveName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                DriveTag("Onsite")
                DriveTag("8 LPA")
                DriveTag("CGPA 7.0+")
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                roles.take(4).chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        row.forEach { role ->
                            Text(
                                text = "• $role",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = company.driveDate,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Registration closes: 18 Aug",
                    style = MaterialTheme.typography.bodySmall,
                    color = ColorMapper.getColor(registrationStatus)
                )
            }
        }
    }
}

@Composable
private fun DriveTag(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CompanyField(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
