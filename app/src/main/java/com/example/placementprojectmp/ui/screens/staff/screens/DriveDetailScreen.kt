package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppTabSection
import com.example.placementprojectmp.ui.screens.shared.cards.CompanyIdCard
import com.example.placementprojectmp.ui.screens.shared.component.InfoGrid
import com.example.placementprojectmp.ui.screens.shared.component.InfoGridItem
import com.example.placementprojectmp.ui.screens.shared.component.SelectionRoundItem
import com.example.placementprojectmp.ui.screens.shared.component.SelectionRounds
import com.example.placementprojectmp.ui.screens.shared.component.VenueRegDetailsCapsule
import com.example.placementprojectmp.ui.theme.colormap.ApplicationStatus
import com.example.placementprojectmp.ui.theme.colormap.ColorMapper
import kotlin.math.absoluteValue

private data class DriveDetailPresentation(
    val driveTitleLine: String,
    val statusDotColors: List<Color>,
    val regDeadline: String,
    val venue: String,
    val roleTitles: List<String>,
    val resultAnnounced: Boolean,
    val resultDate: String,
    val tabRegistrationRequirements: String,
    val tabInstructions: String,
    val tabDocumentSubmission: String,
    val tabEligibility: String,
    val coordinatorLine: String,
    val coordinatorEmail: String,
    val coordinatorPhone: String,
    val showPreferredTag: Boolean,
    val selectionRounds: List<SelectionRoundItem>
)

private fun presentationForDriveId(driveId: String): DriveDetailPresentation {
    val h = driveId.hashCode().absoluteValue
    val driveNames = listOf(
        "Campus Accelerator Drive",
        "Graduate Talent Drive",
        "Early Career Sprint",
        "Future Engineers Drive",
        "Role Fit Drive"
    )
    val driveTitleLine = driveNames[h % driveNames.size]
    val dotStatuses = listOf(
        ApplicationStatus.APPLIED,
        ApplicationStatus.INTERVIEW_SCHEDULED
    )
    val statusDotColors = dotStatuses.map { ColorMapper.getColor(it) }
    val venues = listOf(
        "Main Auditorium",
        "Seminar Hall 2",
        "Online · MS Teams",
        "Room A-101"
    )
    val venue = venues[(h / 2) % venues.size]
    val deadlines = listOf(
        "22 Apr 2026 · 11:59 PM",
        "05 May 2026 · 6:00 PM",
        "28 Apr 2026 · 5:30 PM"
    )
    val regDeadline = deadlines[h % deadlines.size]
    val resultAnnounced = h % 3 != 0
    val resultDate = listOf("28 May 2026", "02 Jun 2026", "15 May 2026")[h % 3]
    val roles = listOf(
        "Android Developer",
        "DevOps Engineer",
        "Tester",
        "Backend Developer"
    )
    val coordinatorNames = listOf(
        "Placement Coordinator: Priya Natarajan",
        "Placement Coordinator: Arjun Mehta",
        "Placement Coordinator: Sneha Kulkarni"
    )
    val roundsTemplates = listOf(
        listOf(
            SelectionRoundItem("Registration Screening", "12 Apr", true),
            SelectionRoundItem("Aptitude Round", "18 Apr", true),
            SelectionRoundItem("Technical Interview", "25 Apr", false),
            SelectionRoundItem("HR Round", "03 May", false)
        ),
        listOf(
            SelectionRoundItem("Profile Review", "08 May", true),
            SelectionRoundItem("Group Discussion", "14 May", false),
            SelectionRoundItem("Technical Panel", "20 May", false),
            SelectionRoundItem("Final HR", "28 May", false)
        )
    )
    val selectionRounds = roundsTemplates[h % roundsTemplates.size]

    val tabRegistrationRequirements =
        "• Valid institute email and student ID\n• CGPA and backlog declaration up to date\n• No disciplinary holds on placement registration\n• One active registration per drive window"

    val tabInstructions =
        "How to register\n• Open this drive and tap Register before the deadline\n• Confirm eligibility prompts and submit\n\nRequired steps\n• Complete the short registration form\n• Upload institute ID proof (PDF/JPEG)\n• Acknowledge code of conduct\n\nWork mode reminders\n• Keep notifications on for slot updates\n• Join the listed venue or online link on time"

    val tabDocumentSubmission =
        "• Latest resume (PDF, max 2 pages)\n• Semester marksheets (combined PDF preferred)\n• Government ID copy (front and back)\n• Any backlog clearance letter if applicable\n\nNaming: RollNumber_DocumentType.pdf"

    val tabEligibility =
        "CGPA criteria\n• Minimum 7.0 CGPA (or 70% aggregate) at time of registration\n\nBacklog rules\n• No active backlogs at registration; cleared backlogs must be documented\n\nAllowed branches\n• CSE, IT, ECE, EEE (and dual-degree equivalents)\n\nPrior experience\n• Up to 18 months full-time experience allowed for this drive\n• Internships under 6 months do not count as experience"

    return DriveDetailPresentation(
        driveTitleLine = driveTitleLine,
        statusDotColors = statusDotColors,
        regDeadline = regDeadline,
        venue = venue,
        roleTitles = roles,
        resultAnnounced = resultAnnounced,
        resultDate = resultDate,
        tabRegistrationRequirements = tabRegistrationRequirements,
        tabInstructions = tabInstructions,
        tabDocumentSubmission = tabDocumentSubmission,
        tabEligibility = tabEligibility,
        coordinatorLine = coordinatorNames[h % coordinatorNames.size],
        coordinatorEmail = "placement.coord@college.edu",
        coordinatorPhone = "9876543210",
        showPreferredTag = h % 2 == 0,
        selectionRounds = selectionRounds
    )
}

@Composable
fun StaffDriveDetailScreen(
    modifier: Modifier = Modifier,
    driveId: String,
    onRegisterClick: () -> Unit = {}
) {
    val p = remember(driveId) { presentationForDriveId(driveId) }
    val roleItems = p.roleTitles.map { title -> InfoGridItem(title = title, subtitle = "") }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DriveStatusIndicatorRow(
                colors = p.statusDotColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        item {
            CompanyIdCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                placementName = p.driveTitleLine,
                onAddNoteClick = { }
            )
        }
        item {
            VenueRegDetailsCapsule(
                regValue = p.regDeadline,
                venue = p.venue,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Roles Offered",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 12.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f)
                )
                InfoGrid(items = roleItems)
            }
        }
        item {
            DriveResultStatusCard(
                resultAnnounced = p.resultAnnounced,
                resultDate = p.resultDate,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
        item {
            AppTabSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                tabTitles = listOf(
                    "Registration Requirements",
                    "Instructions",
                    "Document Submission",
                    "Eligibility"
                ),
                tabContents = listOf(
                    p.tabRegistrationRequirements,
                    p.tabInstructions,
                    p.tabDocumentSubmission,
                    p.tabEligibility
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(34.dp))
        }
        item {
            DriveCoordinatorContactCard(
                coordinatorLine = p.coordinatorLine,
                email = p.coordinatorEmail,
                phone = p.coordinatorPhone,
                showPreferredTag = p.showPreferredTag,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 6.dp)
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Selection Rounds",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 12.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f)
                )
                SelectionRounds(rounds = p.selectionRounds)
            }
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = "Register",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DriveStatusIndicatorRow(
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.take(2).forEach { color ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
private fun DriveResultStatusCard(
    resultAnnounced: Boolean,
    resultDate: String,
    modifier: Modifier = Modifier
) {
    val capsuleShape = RoundedCornerShape(999.dp)
    val dotColor = if (resultAnnounced) Color(0xFF2E7D32) else Color(0xFFC62828)
    val valueText = if (resultAnnounced) resultDate else "Not Announced"

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = capsuleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Result Date",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = ":",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = valueText,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (resultAnnounced) FontWeight.Medium else FontWeight.Normal,
                color = if (resultAnnounced) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )
        }
    }
}

/**
 * Same layout contract as Job Detail [PlacementCoordinatorContactCard] (shared file-local composable there).
 */
@Composable
private fun DriveCoordinatorContactCard(
    coordinatorLine: String,
    email: String,
    phone: String,
    showPreferredTag: Boolean,
    modifier: Modifier = Modifier
) {
    val preferredMode = "Email"
    val preferredColor = when (preferredMode) {
        "Email" -> Color(0xFF2E7D32)
        "Call" -> Color(0xFFFFC107)
        else -> Color(0xFF1976D2)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.align(Alignment.TopEnd),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Contact Support",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = coordinatorLine,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (showPreferredTag) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Preferred Mode",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(preferredColor.copy(alpha = 0.2f))
                                    .border(1.dp, preferredColor.copy(alpha = 0.45f), RoundedCornerShape(999.dp))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = preferredMode,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = preferredColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Email:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "InMail:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "linkedin.com/in/placement-coord",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Phone no.:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        phone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
