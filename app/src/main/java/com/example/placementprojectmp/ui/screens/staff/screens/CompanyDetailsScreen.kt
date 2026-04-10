package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.screens.shared.component.AppTabSection
import com.example.placementprojectmp.ui.screens.shared.component.CompanyIdCard
import com.example.placementprojectmp.ui.screens.shared.component.ContactSupportCard
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TeacherCompanyDetailsScreen(
    modifier: Modifier = Modifier
) {
    val accent = Color.Black

    var isExpanded by remember { mutableStateOf(true) }
    var dragOffset by remember { mutableStateOf(0f) }
    val targetOffset = if (isExpanded) 0f else 260f
    val animatedOffset by animateFloatAsState(
        targetValue = targetOffset + dragOffset,
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "sheet_offset"
    )

    var showNotePrompt by remember { mutableStateOf(false) }
    var showNoteInput by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf("") }
    var stickyNotes by remember { mutableStateOf(listOf<String>()) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Accent header behind the drawer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(accent)
                .align(Alignment.TopCenter)
        )

        // Collapsible drawer
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .offset { IntOffset(0, animatedOffset.roundToInt()) }
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        dragOffset = (dragOffset + delta).coerceIn(-80f, 320f)
                    },
                    onDragStopped = { _ ->
                        isExpanded = dragOffset < 160f
                        dragOffset = 0f
                    }
                ),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.Black,
            tonalElevation = 4.dp
        ) {
            if (!isExpanded) {
                CollapsedDrawerContent(
                    onCompanyCardClick = { showNotePrompt = true }
                )
            } else {
                ExpandedDrawerContent(
                    stickyNotes = stickyNotes,
                    onAddNoteClick = { showNotePrompt = true }
                )
            }
        }
    }

    // First dialog: ask whether to add note
    if (showNotePrompt) {
        AlertDialog(
            onDismissRequest = { showNotePrompt = false },
            title = {
                Text(
                    text = "Add Note",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = "Do you want to add a note?",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showNotePrompt = false
                        showNoteInput = true
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNotePrompt = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Second dialog: text input
    if (showNoteInput) {
        AlertDialog(
            onDismissRequest = { showNoteInput = false },
            title = {
                Text(
                    text = "Add Note",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Write a quick note about this company.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Remember to discuss internship offers.")
                        },
                        maxLines = 4
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (noteText.isNotBlank()) {
                            stickyNotes = stickyNotes + noteText.trim()
                            noteText = ""
                        }
                        showNoteInput = false
                    }
                ) {
                    Text("Save Note")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNoteInput = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun CollapsedDrawerContent(
    onCompanyCardClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Status dots
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusDot(color = Color(0xFF2E7D32)) // Active
            StatusDot(color = Color(0xFFFFC107)) // Participating
            StatusDot(color = Color(0xFFD32F2F)) // Blacklisted
        }

        // Minimal company card
        val interactionSource = remember { MutableInteractionSource() }
        val pressed by interactionSource.collectIsPressedAsState()
        val scale by animateFloatAsState(
            targetValue = if (pressed) 0.97f else 1f,
            animationSpec = tween(durationMillis = 140, easing = FastOutSlowInEasing),
            label = "collapsed_company_press"
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            interactionSource = interactionSource,
            onClick = onCompanyCardClick
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Google",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Tap to add a note",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun StatusDot(color: Color) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
private fun ExpandedDrawerContent(
    stickyNotes: List<String>,
    onAddNoteClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = Color.Transparent
    ) {}

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        color = Color.Black
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CompanyIdCard(
                    onAddNoteClick = onAddNoteClick,
                    betweenOverviewAndDetails = if (stickyNotes.isNotEmpty()) {
                        { StickyNotesRow(stickyNotes = stickyNotes) }
                    } else {
                        null
                    }
                )
            }

            item {
                AppTabSection(
                    tabTitles = listOf("Description", "Overview", "Sector"),
                    tabContents = listOf(
                        "Nexora Systems is a campus hiring focused technology firm building recruitment automation and employability products. The company collaborates with academic institutions to run outcome-driven hiring pipelines. Teams include backend, mobile, cloud, and data specialists with structured mentoring for early-career talent. Fresh graduates work on real modules from the first quarter with guided reviews and milestone-based growth plans. The organization emphasizes practical engineering, communication, and long-term skill development.",
                        "The company operates across India with hybrid delivery teams supporting both enterprise and education clients. Hiring programs include internships, apprenticeships, and full-time tracks designed for campus transitions. Internal platforms support candidate analytics, drive scheduling, and recruiter collaboration workflows. Leadership invests in upskilling through cohort-based learning sessions and regular technical workshops. Project execution follows agile practices with measurable outcomes and delivery transparency.",
                        "Sector coverage includes SaaS platforms, HR technology, analytics, and AI-assisted workflow tools. Nexora's portfolio spans screening engines, placement dashboards, and institution engagement products. The roadmap prioritizes reliability, security, and scalable cloud-native architecture. The team is currently expanding into talent intelligence and predictive employability insights. Partnerships with campuses and industry mentors remain central to the company's growth strategy."
                    )
                )
            }

            item {
                ContactSupportCard()
            }

            item {
                PlacementDrivesSection()
            }

            item {
                CounterSection()
            }

            item {
                PlacementHistorySection()
            }

            item {
                StatisticsCardsSection()
            }

            item {
                DocumentsSection()
            }
        }
    }
}

@Composable
private fun StickyNotesRow(
    stickyNotes: List<String>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        stickyNotes.take(3).forEachIndexed { index, note ->
            StickyNoteCard(
                text = note,
                rotation = if (index % 2 == 0) -3f else 2f
            )
        }
    }
}

@Composable
private fun StickyNoteCard(
    text: String,
    rotation: Float
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .rotate(rotation)
            .animateContentSize(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9C4)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✍",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = FontStyle.Italic
                ),
                color = Color(0xFF5D4037)
            )
        }
    }
}

@Composable
private fun DescriptionBlock(
    title: String,
    content: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = Color(0xFF00D4FF),
        fontWeight = FontWeight.Bold
    )
    Text(
        text = content,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun PlacementDrivesSection() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "Placement Drives & Job Roles",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Divider(Modifier.padding(2.dp))

        val drives = listOf(
            Triple("ONGOING", "SUMMER INTERNSHIP DRIVE", "SDE INTERN"),
            Triple("UPCOMING", "CAMPUS HIRING DRIVE", "SOFTWARE ENGINEER")
        )

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val cardPairWidth = maxWidth - 2.dp
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(end = 38.dp)
            ) {
                items(drives) { item ->
                    val (status, driveName, _) = item
                    val statusColor = when (status) {
                        "ONGOING" -> Color(0xFF2E7D32)
                        "COMPLETED" -> Color(0xFF1976D2)
                        else -> Color(0xFFFFC107)
                    }
                    val statusMessage = when (status) {
                        "ONGOING" -> "Drive currently in progress: active evaluation phase."
                        "COMPLETED" -> "Drive lifecycle closed: final selections published."
                        else -> "Drive queued: scheduled to start soon."
                    }

                    Row(
                        modifier = Modifier.width(cardPairWidth),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(0.4f)
                                .height(152.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF00D4FF).copy(alpha = 0.8f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row(
                                        modifier = Modifier.clickable {
                                            scope.launch { snackbarHostState.showSnackbar(statusMessage) }
                                        },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(9.dp)
                                                .clip(CircleShape)
                                                .background(statusColor)
                                        )
                                        Text(
                                            text = status,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = driveName,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Black,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (driveName.contains("SUMMER")) "03 Aug - 22 Aug" else "05 Sep - 26 Sep",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black
                                    )
                                }
                            }
                        }

                        Card(
                            modifier = Modifier
                                .weight(0.6f)
                                .height(152.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Software Engineer Intern - Web Applications",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                BulletDetailRow(keyText = "Responsibility", valueText = "Worked on web modules")
                                BulletDetailRow(keyText = "Tech Stack", valueText = "Kotlin, REST APIs")
                                BulletDetailRow(keyText = "CTC / LPA", valueText = "12 LPA")
                                BulletDetailRow(keyText = "Interview Rounds", valueText = "4")
                                BulletDetailRow(keyText = "Eligibility", valueText = "7.0+ CGPA")
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(32.dp)
                    .clickable {
                        val next = (listState.firstVisibleItemIndex + 1).coerceAtMost(drives.lastIndex)
                        if (next != listState.firstVisibleItemIndex) {
                            scope.launch {
                                val visible = listState.layoutInfo.visibleItemsInfo.firstOrNull()
                                val fallback = 320f
                                val distance = ((visible?.size ?: fallback.toInt()) + 10).toFloat()
                                val steps = 24
                                var progressed = 0f
                                repeat(steps) { step ->
                                    val t = (step + 1) / steps.toFloat()
                                    val eased = 1f - (1f - t) * (1f - t)
                                    val target = distance * eased
                                    listState.scrollBy(target - progressed)
                                    progressed = target
                                    delay(14)
                                }
                                listState.animateScrollToItem(next)
                            }
                        }
                    },
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.58f)
                )
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Next card",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
private fun BulletDetailRow(
    keyText: String,
    valueText: String
) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "\u2022 $keyText:",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = valueText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CounterSection() {
    val imagePool = remember {
        listOf(
            R.drawable.std_1,
            R.drawable.std_2,
            R.drawable.std_3,
            R.drawable.std_4
        )
    }
    val stableRandomImages = remember {
        val seeded = Random(1007)
        List(5) { imagePool[seeded.nextInt(imagePool.size)] }
    }
    val studentNames = remember { listOf("Aarav", "Meera", "Riya", "Kabir", "Dev") }
    val highlightedIndices = remember {
        val seeded = Random(2026)
        setOf(seeded.nextInt(5), seeded.nextInt(5))
    }
    var expanded by remember { mutableStateOf(false) }
    val overlapSpacing by animateDpAsState(
        targetValue = if (expanded) 10.dp else (-14).dp,
        animationSpec = tween(260),
        label = "participants_spacing"
    )
    val avatarSize by animateDpAsState(
        targetValue = if (expanded) 34.dp else 30.dp,
        animationSpec = tween(260),
        label = "participants_size"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .clickable { expanded = !expanded },
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            if (!expanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(overlapSpacing),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        stableRandomImages.forEachIndexed { index, imageRes ->
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = "Participant",
                                modifier = Modifier
                                    .size(avatarSize)
                                    .clip(CircleShape)
                                    .border(
                                        width = if (index in highlightedIndices) 2.dp else 1.dp,
                                        color = if (index in highlightedIndices) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                        shape = CircleShape
                                    ),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Text(
                        text = "+43",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val gridItems = (
                        stableRandomImages.mapIndexed { idx, it -> it to (studentNames[idx] to "2025") } +
                            listOf(0 to ("+43" to ""))
                        )
                    gridItems.chunked(3).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowItems.forEach { (resId, meta) ->
                                if (resId == 0) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(86.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = meta.first,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                } else {
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = resId),
                                            contentDescription = "Participant",
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .border(
                                                    width = if ((stableRandomImages.indexOf(resId)) in highlightedIndices) 2.dp else 1.dp,
                                                    color = if ((stableRandomImages.indexOf(resId)) in highlightedIndices) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                                    shape = CircleShape
                                                ),
                                            contentScale = ContentScale.Crop
                                        )
                                        Text(meta.first, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold)
                                        Text(meta.second, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                            repeat(3 - rowItems.size) { Spacer(modifier = Modifier.width(0.dp)) }
                        }
                    }
                }
            }
        }
        if (!expanded) {
            Text(
                text = "CANDIDATES",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
                ,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun PlacementHistorySection() {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "Placement History",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Divider(Modifier.padding(2.dp))
        val items = listOf(
            "SUMMER INTERNSHIP DRIVE" to "2025",
            "CAMPUS HIRING DRIVE" to "2024"
        )
        val historyImages = remember {
            val seeded = Random(3012)
            val pool = listOf(R.drawable.std_1, R.drawable.std_2, R.drawable.std_3, R.drawable.std_4)
            List(5) { pool[seeded.nextInt(pool.size)] }
        }
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val historyCardWidth = maxWidth - 2.dp
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items) { pair ->
                    val (driveName, yearText) = pair
                var expanded by remember(driveName) { mutableStateOf(false) }
                Card(
                    modifier = Modifier.width(historyCardWidth),
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = driveName, style = MaterialTheme.typography.bodyLarge, color = Color.Black, fontWeight = FontWeight.Black)
                                Text(text = yearText, style = MaterialTheme.typography.bodySmall, color = Color.Black.copy(alpha = 0.8f))
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(text = "\u2022 SDE Intern", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                                Text(text = "\u2022 Web Developer", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                                Text(text = "\u2022 QA Tester", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                                Text(text = "\u2022 Backend Intern", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                            }
                        }
                        if (!expanded) {
                            Row(horizontalArrangement = Arrangement.spacedBy((-10).dp), verticalAlignment = Alignment.CenterVertically) {
                                historyImages.forEach { img ->
                                    Image(
                                        painter = painterResource(id = img),
                                        contentDescription = "History participant",
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        } else {
                            val names = listOf("Aarav", "Meera", "Riya", "Kabir", "Dev")
                            val gridItems = (historyImages.mapIndexed { idx, it -> it to (names[idx] to "2025") } + listOf(0 to ("+8" to "")))
                            gridItems.chunked(3).forEach { rowItems ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    rowItems.forEach { (resId, meta) ->
                                        if (resId == 0) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(84.dp)
                                                    .clip(RoundedCornerShape(12.dp))
                                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(meta.first, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                                            }
                                        } else {
                                            Column(
                                                modifier = Modifier.weight(1f),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(2.dp)
                                            ) {
                                                Image(
                                                    painter = painterResource(id = resId),
                                                    contentDescription = "History participant",
                                                    modifier = Modifier
                                                        .size(36.dp)
                                                        .clip(CircleShape)
                                                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Text(meta.first, style = MaterialTheme.typography.bodyMedium, color = Color.Black, fontWeight = FontWeight.SemiBold)
                                                Text(meta.second, style = MaterialTheme.typography.labelSmall, color = Color.Black.copy(alpha = 0.8f))
                                            }
                                        }
                                    }
                                    repeat(3 - rowItems.size) { Spacer(modifier = Modifier.width(0.dp)) }
                                }
                            }
                        }
                    }
                }
            }
            }

            Card(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(32.dp)
                    .clickable {
                        val next = (listState.firstVisibleItemIndex + 1).coerceAtMost(items.lastIndex)
                        if (next != listState.firstVisibleItemIndex) {
                            scope.launch {
                                val visible = listState.layoutInfo.visibleItemsInfo.firstOrNull()
                                val fallback = 320f
                                val distance = ((visible?.size ?: fallback.toInt()) + 10).toFloat()
                                val steps = 24
                                var progressed = 0f
                                repeat(steps) { step ->
                                    val t = (step + 1) / steps.toFloat()
                                    val eased = 1f - (1f - t) * (1f - t)
                                    val target = distance * eased
                                    listState.scrollBy(target - progressed)
                                    progressed = target
                                    delay(14)
                                }
                                listState.animateScrollToItem(next)
                            }
                        }
                    },
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.58f)
                )
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Next history card",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun StatisticsCardsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatisticCard(modifier = Modifier.weight(1f), value = "12", label = "Drives Conducted")
        StatisticCard(modifier = Modifier.weight(1f), value = "48", label = "Total Selections")
    }
}

@Composable
private fun StatisticCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF00D4FF),
                modifier = Modifier.align(Alignment.End),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DocumentsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Documents",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Card(
                modifier = Modifier.size(36.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = {}
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
        Divider(Modifier.padding(2.dp))

        val docs = listOf("CDC", "Job Description", "Offer Letter", "Drive Guidelines")
        docs.chunked(2).forEach { rowDocs ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowDocs.forEach { title ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .animateContentSize(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        onClick = {}
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "📁",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

