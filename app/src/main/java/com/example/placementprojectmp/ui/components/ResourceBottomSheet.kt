package com.example.placementprojectmp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.data.AptitudeTestRepository

/** Dummy data for Notes bottom sheet: (fileName, uploaderDisplayName). */
private val notesDummyItems = listOf(
    "Operating Systems Notes.pdf" to "Prof. Sharma",
    "DBMS Complete Guide.pdf" to "Placement Cell",
    "Computer Networks Notes.pdf" to "Tech Club",
    "Data Structures Handbook.pdf" to "Alumni Resources"
)

/** Dummy data for Cheat Sheet bottom sheet. */
private val cheatSheetDummyItems = listOf(
    "OS Quick Reference.pdf" to "Prof. Sharma",
    "DBMS Cheat Sheet.pdf" to "Placement Cell",
    "Networks Summary.pdf" to "Tech Club",
    "DS & Algo Handbook.pdf" to "Alumni Resources"
)

/** PYQ companies: (companyName, roleSubtitle). */
private val pyqCompanyList = listOf(
    "Google" to "Android Developer PYQ",
    "Amazon" to "Backend Developer PYQ",
    "Infosys" to "Java Developer PYQ",
    "Microsoft" to "SDE PYQ"
)

/**
 * Bottom sheet for resource viewer: folder title header + list of PDF resources.
 * For PYQ: header (PYQ, ER, menu), filter card, company list. For Notes/Cheat Sheet: PDF list.
 * Other folders: original list style. Opens at partial height; user can swipe up to expand.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceBottomSheet(
    folderTitle: String,
    resources: List<Pair<String, String>>,
    onDismissRequest: () -> Unit,
    onPyqCompanyClick: (String) -> Unit = {},
    onAptitudeTestClick: (String) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val isPYQ = folderTitle == "PYQ"
    val isAptitudeTest = folderTitle == "Aptitude Test"
    val isNotesOrCheatSheet = folderTitle == "Notes" || folderTitle == "Cheat Codes"
    val displayTitle = if (folderTitle == "Cheat Codes") "Cheat Sheet" else folderTitle
    val listItems = when (folderTitle) {
        "Notes" -> notesDummyItems
        "Cheat Codes" -> cheatSheetDummyItems
        else -> resources
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null
    ) {
        if (isPYQ) {
            PyqBottomSheetContent(
                screenHeightDp = screenHeightDp,
                onCompanyClick = onPyqCompanyClick
            )
        } else if (isAptitudeTest) {
            AptitudeTestBottomSheetContent(
                screenHeightDp = screenHeightDp,
                onTestClick = onAptitudeTestClick
            )
        } else if (isNotesOrCheatSheet) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = (screenHeightDp * 0.75f).toInt().dp)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = displayTitle,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { /* Options - future use */ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Options",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                items(listItems) { (fileName, uploaderName) ->
                    NotesSheetItem(
                        fileName = fileName,
                        uploaderName = uploaderName,
                        onDownloadClick = { /* Trigger download */ }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = folderTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = (screenHeightDp * 0.75f).toInt().dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(resources.size) { index ->
                        val (fileName, approvedBy) = resources[index]
                        ResourceItem(
                            fileName = fileName,
                            approvedByEmail = approvedBy
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NotesSheetItem(
    fileName: String,
    uploaderName: String,
    onDownloadClick: () -> Unit
) {
    var bookmarked by remember(fileName) { mutableStateOf(false) }
    val bookmarkColor by animateColorAsState(
        targetValue = if (bookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(200),
        label = "bookmark_color"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PictureAsPdf,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = fileName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Uploaded by: $uploaderName",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = { bookmarked = !bookmarked },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = if (bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = if (bookmarked) "Remove bookmark" else "Bookmark",
                tint = bookmarkColor
            )
        }
        IconButton(
            onClick = onDownloadClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Download",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PyqBottomSheetContent(
    screenHeightDp: Int,
    onCompanyClick: (String) -> Unit
) {
    var filterVisible by remember { mutableStateOf(false) }
    var selectedCompanies by remember { mutableStateOf(setOf<String>()) }
    var selectedRoles by remember { mutableStateOf(setOf<String>()) }
    var selectedTopics by remember { mutableStateOf(setOf<String>()) }
    var selectedYears by remember { mutableStateOf(setOf<String>()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = (screenHeightDp * 0.75f).toInt().dp)
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PYQ",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "ER",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                IconButton(
                    onClick = { filterVisible = !filterVisible },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Filter",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        item {
            AnimatedVisibility(
                visible = filterVisible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                PyqFilterCard(
                    selectedCompanies = selectedCompanies,
                    selectedRoles = selectedRoles,
                    selectedTopics = selectedTopics,
                    selectedYears = selectedYears,
                    onCompaniesChange = { selectedCompanies = it },
                    onRolesChange = { selectedRoles = it },
                    onTopicsChange = { selectedTopics = it },
                    onYearsChange = { selectedYears = it },
                    onClose = { filterVisible = false }
                )
            }
        }
        items(pyqCompanyList) { (companyName, roleSubtitle) ->
            PyqCompanyCard(
                companyName = companyName,
                roleSubtitle = roleSubtitle,
                onClick = { onCompanyClick(companyName) }
            )
        }
    }
}

@Composable
private fun AptitudeTestBottomSheetContent(
    screenHeightDp: Int,
    onTestClick: (String) -> Unit
) {
    val tests = remember { AptitudeTestRepository.getAllTests() }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = (screenHeightDp * 0.75f).toInt().dp)
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(
                text = "Aptitude Test",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(tests) { test ->
            AptitudeTestCard(
                testName = test.title,
                description = "Practice test • ${test.totalQuestions} questions • ${test.timeMinutes} min",
                onClick = { onTestClick(test.id) }
            )
        }
    }
}

@Composable
private fun PyqFilterCard(
    selectedCompanies: Set<String>,
    selectedRoles: Set<String>,
    selectedTopics: Set<String>,
    selectedYears: Set<String>,
    onCompaniesChange: (Set<String>) -> Unit,
    onRolesChange: (Set<String>) -> Unit,
    onTopicsChange: (Set<String>) -> Unit,
    onYearsChange: (Set<String>) -> Unit,
    onClose: () -> Unit
) {
    val outlineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, outlineColor, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filter",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onClose, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        PyqFilterChipRow(
            label = "Filter by Company",
            options = listOf("Google", "Amazon", "Microsoft", "Infosys", "TCS", "Adobe"),
            selected = selectedCompanies,
            onSelectionChange = onCompaniesChange
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = outlineColor,
            thickness = 1.dp
        )
        PyqFilterChipRow(
            label = "Filter by Role",
            options = listOf("Java Developer", "Android Developer", "Backend Developer", "SDE", "Frontend"),
            selected = selectedRoles,
            onSelectionChange = onRolesChange
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = outlineColor,
            thickness = 1.dp
        )
        PyqFilterChipRow(
            label = "Filter by Topic",
            options = listOf("Java", "Data Structures", "System Design", "DBMS", "Operating Systems"),
            selected = selectedTopics,
            onSelectionChange = onTopicsChange
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = outlineColor,
            thickness = 1.dp
        )
        PyqFilterChipRow(
            label = "Filter by Year",
            options = listOf("2024", "2023", "2022", "2021"),
            selected = selectedYears,
            onSelectionChange = onYearsChange
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PyqFilterChipRow(
    label: String,
    options: List<String>,
    selected: Set<String>,
    onSelectionChange: (Set<String>) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = option in selected
                val bgColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                    animationSpec = tween(200),
                    label = "chip_bg"
                )
                val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
                Text(
                    text = option,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(bgColor)
                        .border(1.dp, borderColor, RoundedCornerShape(20.dp))
                        .clickable {
                            onSelectionChange(
                                if (isSelected) selected - option else selected + option
                            )
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun PyqCompanyCard(
    companyName: String,
    roleSubtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = companyName.take(1).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = companyName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = roleSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        }
    }
}
