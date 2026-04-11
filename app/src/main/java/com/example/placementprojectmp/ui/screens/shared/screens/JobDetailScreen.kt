package com.example.placementprojectmp.ui.screens.shared.screens

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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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

private data class JobDetailPresentation(
    val roleTitle: String,
    val statusDotColors: List<Color>,
    val infoGridItems: List<InfoGridItem>,
    val venue: String,
    val regDeadline: String,
    val tabJobDescription: String,
    val tabResponsibilities: String,
    val tabRequirements: String,
    val tabEligibility: String,
    val coordinatorLine: String,
    val coordinatorEmail: String,
    val coordinatorPhone: String,
    val showPreferredTag: Boolean,
    val selectionRounds: List<SelectionRoundItem>
)

private fun presentationForJobId(jobId: String): JobDetailPresentation {
    val h = jobId.hashCode().absoluteValue
    val roles = listOf(
        "Associate Android Engineer",
        "Web Developer",
        "DevOps Engineer",
        "Backend Engineer",
        "ML Engineer",
        "Full-stack Developer"
    )
    val roleTitle = roles[h % roles.size]
    val internship = h % 3 == 0
    val fullTime = h % 3 == 1
    val jobTypeSubtitle = when {
        internship -> "Internship · 6 months"
        fullTime -> "Full-time"
        else -> "Part-time · 20 hrs/wk"
    }
    val modes = listOf(
        "Hybrid" to "Tue–Thu onsite",
        "Remote" to "Core hours 11–5 IST",
        "Onsite" to "Mon–Fri · Block A"
    )
    val (modeSub, modeExtra) = modes[h % modes.size]
    val ctcTitles = listOf("12 LPA", "6 LPA", "18 LPA", "9.5 LPA")
    val ctcTitle = ctcTitles[h % ctcTitles.size]
    val extras = listOf(
        "PPO · No bond",
        "Bond · 24 mo",
        "Contract · 12 mo",
        "Duration · 8 mo"
    )
    val extraTitle = extras[h % extras.size]
    val venues = listOf(
        "Main Auditorium",
        "Room A-101",
        "Online · MS Teams",
        "Seminar Hall 2"
    )
    val venue = venues[(h / 2) % venues.size]
    val deadlines = listOf(
        "18 Apr 2026 · 11:59 PM",
        "02 May 2026 · 6:00 PM",
        "30 Apr 2026 · 5:30 PM"
    )
    val regDeadline = deadlines[h % deadlines.size]
    val dotStatuses = listOf(
        ApplicationStatus.APPLIED,
        ApplicationStatus.INTERVIEW_SCHEDULED,
        ApplicationStatus.SHORTLISTED
    )
    val statusDotColors = dotStatuses.map { ColorMapper.getColor(it) }
    val coordinatorNames = listOf(
        "Placement Coordinator: Priya Natarajan",
        "Placement Coordinator: Arjun Mehta",
        "Placement Coordinator: Sneha Kulkarni"
    )
    val showPreferred = h % 2 == 0
    val roundsTemplates = listOf(
        listOf(
            SelectionRoundItem("Aptitude Round", "08 Apr", true),
            SelectionRoundItem("Technical Interview", "14 Apr", true),
            SelectionRoundItem("HR Round", "22 Apr", false),
            SelectionRoundItem("Managerial", "28 Apr", false)
        ),
        listOf(
            SelectionRoundItem("Online Assessment", "05 May", true),
            SelectionRoundItem("Technical Round 1", "12 May", false),
            SelectionRoundItem("Technical Round 2", "18 May", false),
            SelectionRoundItem("HR & Culture", "24 May", false),
            SelectionRoundItem("Offer Discussion", "01 Jun", false)
        ),
        listOf(
            SelectionRoundItem("Coding Screen", "10 Apr", true),
            SelectionRoundItem("System Design", "17 Apr", false),
            SelectionRoundItem("HR Round", "25 Apr", false)
        )
    )
    val selectionRounds = roundsTemplates[h % roundsTemplates.size]

    val tabJobDescription =
        "Own delivery of features for the hiring product surface on mobile and web. Collaborate with design and backend on APIs, releases, and quality gates. Participate in code review, sprint planning, and on-call rotations where applicable."

    val tabResponsibilities =
        "Daily tasks\n• Implement and test user-facing flows\n• Fix defects and improve performance\n• Document decisions and handoffs\n\nWork expectations\n• Raise blockers early with options\n• Keep tickets and estimates current\n\nConduct\n• Business-casual on client days · Badge on campus · Report by 9:30 AM on drive days"

    val tabRequirements =
        "Skills\n• Kotlin  ·  Jetpack Compose  ·  Coroutines\n\nCommunication / soft skills\n• Clear written updates  ·  Stakeholder sync\n\nTech stack\n• REST  ·  Git  ·  CI basics\n\nOther\n• Willingness to learn backend tooling  ·  Occasional travel"

    val tabEligibility =
        "• CGPA 7.0+ (or equivalent)\n• No active backlogs at offer\n• Prior internship in product teams preferred for this track\n• DSA: arrays, trees, graphs; system basics for senior rounds"

    return JobDetailPresentation(
        roleTitle = roleTitle,
        statusDotColors = statusDotColors,
        infoGridItems = listOf(
            InfoGridItem(title = jobTypeSubtitle, subtitle = "Job type"),
            InfoGridItem(title = "$modeSub · $modeExtra", subtitle = "Work mode"),
            InfoGridItem(title = ctcTitle, subtitle = "CTC / LPA"),
            InfoGridItem(title = extraTitle, subtitle = "Additional info")
        ),
        venue = venue,
        regDeadline = regDeadline,
        tabJobDescription = tabJobDescription,
        tabResponsibilities = tabResponsibilities,
        tabRequirements = tabRequirements,
        tabEligibility = tabEligibility,
        coordinatorLine = coordinatorNames[h % coordinatorNames.size],
        coordinatorEmail = "placement.coord@college.edu",
        coordinatorPhone = "9876543210",
        showPreferredTag = showPreferred,
        selectionRounds = selectionRounds
    )
}

@Composable
fun JobDetailScreen(
    modifier: Modifier = Modifier,
    jobId: String,
    onApplyClick: () -> Unit = {}
) {
    val p = remember(jobId) { presentationForJobId(jobId) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            JobStatusIndicatorRow(
                colors = p.statusDotColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        item {
            CompanyIdCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                placementName = p.roleTitle,
                onAddNoteClick = { }
            )
        }
        item {
            InfoGrid(
                items = p.infoGridItems,
                modifier = Modifier.padding(horizontal = 20.dp)
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
            AppTabSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                tabTitles = listOf(
                    "Job Description",
                    "Responsibilities",
                    "Requirements",
                    "Eligibility"
                ),
                tabContents = listOf(
                    p.tabJobDescription,
                    p.tabResponsibilities,
                    p.tabRequirements,
                    p.tabEligibility
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(34.dp))
        }
        item {
            PlacementCoordinatorContactCard(
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
                    .padding(bottom = 6.dp)
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
                    onClick = onApplyClick,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = "Apply",
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
private fun JobStatusIndicatorRow(
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.take(3).forEach { color ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

/**
 * Same layout contract as [com.example.placementprojectmp.ui.screens.shared.component.ContactSupportCard],
 * with placement-coordinator copy and optional "Preferred" pill.
 */
@Composable
private fun PlacementCoordinatorContactCard(
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
