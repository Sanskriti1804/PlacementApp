package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.theme.colormap.ColorMapper
import com.example.placementprojectmp.ui.theme.colormap.Department
import com.example.placementprojectmp.ui.theme.colormap.FacultyPosition
import com.example.placementprojectmp.ui.theme.colormap.PlacementRole
import com.example.placementprojectmp.ui.theme.colormap.TeacherAccountState
import com.example.placementprojectmp.ui.theme.colormap.UserRole
import com.example.placementprojectmp.ui.theme.NeonBlue
import kotlin.random.Random

@Composable
fun TeacherProfileScreen(
    modifier: Modifier = Modifier,
    onShowMoreCompanies: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
    ) {
        item { TeacherIdCardsSection() }
        item { FacultyInfoStatusCard() }
        item { ContactSection() }
        item { OfficeLocationCard() }
        item { Spacer(modifier = Modifier.height(6.dp)) }
        item { ProfessionalDetailsSection() }
        item { StatsGridSection() }
    }
}

@Composable
fun TeacherIdCardsSection(
    modifier: Modifier = Modifier,
    name: String = "Dr. Ananya Sharma",
    roleText: String = "Professor at Delhi Institute of Technology",
    imageResId: Int = R.drawable.pfp_staff,
    idText: String = "FAC_024"
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Card(
            modifier = Modifier.weight(0.3f),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Teacher profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(142.dp)
                    .clip(RoundedCornerShape(18.dp))
            )
        }

        Card(
            modifier = Modifier.weight(0.7f),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(142.dp)
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val statusColor = ColorMapper.getColor(TeacherAccountState.ACTIVE)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Text(
                        text = "January, 2021 - Present",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Thin
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = roleText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text(text = "Placement In-Charge", style = MaterialTheme.typography.labelSmall) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.Transparent,
                            labelColor = statusColor
                        ),
                        border = AssistChipDefaults.assistChipBorder(
                            enabled = true,
                            borderColor = statusColor
                        )
                    )
                    Text(
                        text = idText,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FacultyInfoStatusCard() {
    val dotColors = listOf(
        ColorMapper.getColor(FacultyPosition.PROFESSOR),
        ColorMapper.getColor(PlacementRole.PLACEMENT_COORDINATOR),
        ColorMapper.getColor(Department.CSE)
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    dotColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatusItem(
                    dotColor = dotColors[0],
                    label = "Faculty Position",
                    value = "Professor"
                )
                StatusItem(
                    dotColor = dotColors[1],
                    label = "Placement Responsibility",
                    value = "Placement Incharge"
                )
                StatusItem(
                    dotColor = dotColors[2],
                    label = "Department",
                    value = "Computer Science"
                )
            }
        }
    }
}

@Composable
private fun StatusItem(
    dotColor: Color,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.width(220.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.width(12.dp), contentAlignment = Alignment.TopCenter) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.alpha(0.78f)
            )
        }
    }
}

@Composable
private fun ContactSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val neonValue = ColorMapper.getColor(UserRole.STUDENT)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ContactValueButton(modifier = Modifier.weight(1f), value = "ananya.sharma@dit.edu", color = neonValue)
            ContactValueButton(modifier = Modifier.weight(1f), value = "+91 9876543210", color = neonValue)
        }
        ContactValueButton(
            modifier = Modifier.fillMaxWidth(),
            value = "linkedin.com/in/ananyasharma",
            color = neonValue
        )
    }
}

@Composable
private fun ContactValueButton(
    modifier: Modifier,
    value: String,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun OfficeLocationCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📍", style = MaterialTheme.typography.bodyMedium)
            }
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(text = "Office Location", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
                Text(text = "Room 302, Placement Cell Building", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "Delhi Institute of Technology", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun ProfessionalDetailsSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Professional Details",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

        DetailField(label = "Qualification", value = "PhD in Artificial Intelligence")
        DetailField(
            label = "Professional Experience",
            value = "12+ years teaching experience\nSoftware Engineer at TCS (2018 - 2021)"
        )
        DetailField(
            label = "Subjects Taught",
            value = "Machine Learning\nData Structures\nArtificial Intelligence"
        )
        DetailField(label = "Current Role", value = "Placement Faculty Coordinator")
        DetailField(label = "Placement Responsibility", value = "Placement Incharge")
    }
}

@Composable
private fun DetailField(
    label: String,
    value: String
) {
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

@Composable
private fun StatsGridSection() {
    val companyPool = remember {
        listOf(
            R.drawable.comp_1,
            R.drawable.comp_2,
            R.drawable.comp_3
        )
    }
    val studentPool = remember {
        listOf(
            R.drawable.std_1,
            R.drawable.std_2
        )
    }
    val drivesImages = remember {
        val seeded = Random(401)
        List(5) { companyPool[seeded.nextInt(companyPool.size)] }
    }
    val companiesImages = remember {
        val seeded = Random(511)
        List(5) { companyPool[seeded.nextInt(companyPool.size)] }
    }
    val studentsImages = remember {
        val seeded = Random(629)
        List(5) { studentPool[seeded.nextInt(studentPool.size)] }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Assigned Departments",
            style = MaterialTheme.typography.titleLarge,
            color = NeonBlue,
            fontWeight = FontWeight.SemiBold
        )
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ExpandableCounterCard(
                modifier = Modifier.weight(1f),
                title = "Assigned Departments",
                countText = "+4",
                colorDots = listOf(
                    ColorMapper.getColor(Department.CSE),
                    ColorMapper.getColor(Department.IT),
                    ColorMapper.getColor(Department.ECE),
                    ColorMapper.getColor(Department.MCA)
                ),
                items = listOf("Computer Science", "Information Technology", "AI & Data Science")
            )
            ExpandableCounterCard(
                modifier = Modifier.weight(1f),
                title = "Drives Coordinated",
                countText = "+23",
                imageDots = drivesImages,
                items = listOf("Google Hiring Drive", "Amazon SDE Drive", "Infosys Campus Drive")
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ExpandableCounterCard(
                modifier = Modifier.weight(1f),
                title = "Assigned Companies",
                countText = "+15",
                imageDots = companiesImages,
                items = listOf("Google", "Microsoft", "TCS")
            )
            ExpandableCounterCard(
                modifier = Modifier.weight(1f),
                title = "Assigned Students",
                countText = "+320",
                imageDots = studentsImages,
                items = listOf("Aditi Sharma", "Rahul Verma", "Sneha Gupta")
            )
        }
    }
}

@Composable
fun ExpandableCounterCard(
    modifier: Modifier,
    title: String,
    countText: String,
    imageDots: List<Int> = emptyList(),
    colorDots: List<Color> = emptyList(),
    items: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = !expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                OverlapCounterStrip(
                    imageDots = imageDots,
                    colorDots = colorDots,
                    countText = countText
                )
            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                CounterList(
                imageDots = imageDots,
                colorDots = colorDots,
                items = items
            )
            }
        }
    }
}

@Composable
fun CounterList(
    imageDots: List<Int>,
    colorDots: List<Color>,
    items: List<String>
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        items.take(3).forEachIndexed { index, label ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (imageDots.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = imageDots[index % imageDots.size]),
                        contentDescription = "item visual",
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(colorDots[index % colorDots.size])
                    )
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        if (items.size > 3) {
            Text(
                text = "+${items.size - 3}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun OverlapCounterStrip(
    imageDots: List<Int>,
    colorDots: List<Color>,
    countText: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy((-10).dp), verticalAlignment = Alignment.CenterVertically) {
            if (imageDots.isNotEmpty()) {
                imageDots.take(5).forEach { res ->
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = "counter item",
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.55f), CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                colorDots.take(5).forEach { dotColor ->
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                            .border(1.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    )
                }
            }
        }
        Text(
            text = countText,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
