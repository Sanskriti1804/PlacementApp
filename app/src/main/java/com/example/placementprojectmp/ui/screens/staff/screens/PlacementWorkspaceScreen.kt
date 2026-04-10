package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.viewmodel.Note
import com.example.placementprojectmp.viewmodel.PlacementTab
import com.example.placementprojectmp.viewmodel.PlacementWorkspaceViewModel
import com.example.placementprojectmp.viewmodel.Resource
import com.example.placementprojectmp.viewmodel.ResourceType
import com.example.placementprojectmp.viewmodel.StudentTag
import com.example.placementprojectmp.viewmodel.WorkspaceStudent
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PlacementWorkspaceScreen(
    modifier: Modifier = Modifier,
    viewModel: PlacementWorkspaceViewModel = koinViewModel()
) {
    val state by viewModel.state
    val listState = rememberLazyListState()

    BackHandler(enabled = state.isSearchExpanded) { viewModel.collapseSearch() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            if (state.selectedTab == PlacementTab.RESOURCES) {
                FloatingActionButton(
                    onClick = {},
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(imageVector = Icons.Default.Folder, contentDescription = "Upload resource")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Placement Workspace",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Resources, student actions, and shared materials",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                ExpandableSearchBar(
                    query = state.searchQuery,
                    expanded = state.isSearchExpanded,
                    placeholder = when (state.selectedTab) {
                        PlacementTab.RESOURCES -> "Search resources, files..."
                        PlacementTab.STUDENTS -> "Search students, roll number..."
                        PlacementTab.NOTES -> "Search notes and materials..."
                    },
                    onExpand = viewModel::expandSearch,
                    onCollapse = viewModel::collapseSearch,
                    onQueryChange = viewModel::onSearchQueryChanged
                )
            }

            item {
                PlacementTabRow(
                    selectedTab = state.selectedTab,
                    onTabSelected = viewModel::selectTab
                )
            }

            item {
                AnimatedContent(
                    targetState = state.selectedTab,
                    transitionSpec = {
                        (fadeIn(tween(160)) togetherWith fadeOut(tween(120))).using(SizeTransform(clip = false))
                    },
                    label = "workspace_tab_content"
                ) { tab ->
                    when (tab) {
                        PlacementTab.RESOURCES -> ResourcesTab(
                            resources = state.resources,
                            showActionsForId = state.showResourceActionsForId,
                            onMenuClick = viewModel::showResourceActions,
                            onDismissMenu = viewModel::hideResourceActions
                        )
                        PlacementTab.STUDENTS -> StudentsTab(
                            students = state.students,
                            expanded = state.isSearchExpanded,
                            selectedIds = state.bulkSelectedStudentIds,
                            isBulkMode = state.isBulkMode,
                            eligibleFilter = state.studentFilterEligible,
                            appliedFilter = state.studentFilterApplied,
                            taggedOnly = state.studentFilterTaggedOnly,
                            onEligibleFilter = viewModel::setStudentEligibleFilter,
                            onAppliedFilter = viewModel::setStudentAppliedFilter,
                            onToggleTaggedOnly = viewModel::toggleTaggedOnly,
                            onLongPressStudent = viewModel::enterBulkMode,
                            onToggleStudent = viewModel::toggleStudentSelected,
                            onExitBulk = viewModel::exitBulkMode,
                            onAddTag = { tag -> viewModel.addTagToSelected(tag) },
                            onRemoveTag = { tag -> viewModel.removeTagFromSelected(tag) },
                            favoriteTag = viewModel.favoriteTag(),
                            priorityTag = viewModel.priorityTag(),
                            taggedTag = viewModel.taggedTag(),
                            onUpdateStudentNote = viewModel::updateStudentNote
                        )
                        PlacementTab.NOTES -> NotesTab(
                            notes = state.notes,
                            showActionsForId = state.showNoteActionsForId,
                            onMenuClick = viewModel::showNoteActions,
                            onDismissMenu = viewModel::hideNoteActions
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlacementTabRow(
    selectedTab: PlacementTab,
    onTabSelected: (PlacementTab) -> Unit
) {
    val tabs = listOf(PlacementTab.RESOURCES, PlacementTab.STUDENTS, PlacementTab.NOTES)
    val selectedIndex = tabs.indexOf(selectedTab).coerceAtLeast(0)
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f))
            .padding(4.dp)
    ) {
        val tabWidth = maxWidth / tabs.size
        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * selectedIndex,
            animationSpec = tween(220, easing = FastOutSlowInEasing),
            label = "workspace_tab_indicator"
        )
        Box(
            modifier = Modifier
                .padding(start = indicatorOffset)
                .width(tabWidth - 4.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            tabs.forEach { tab ->
                val selected = tab == selectedTab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clickable { onTabSelected(tab) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.label(),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

private fun PlacementTab.label(): String = when (this) {
    PlacementTab.RESOURCES -> "Resources"
    PlacementTab.STUDENTS -> "Students"
    PlacementTab.NOTES -> "Materials"
}

@Composable
private fun ExpandableSearchBar(
    query: String,
    expanded: Boolean,
    placeholder: String,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onQueryChange: (String) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val animatedWidth by animateDpAsState(
        targetValue = if (expanded) 360.dp else 182.dp,
        animationSpec = tween(260, easing = FastOutSlowInEasing),
        label = "workspace_search_width"
    )
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val targetWidth = if (animatedWidth > maxWidth) maxWidth else animatedWidth
        Row(
            modifier = Modifier
                .width(targetWidth)
                .height(52.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { onExpand() }
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (expanded) {
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                    decorationBox = { inner ->
                        if (query.isBlank()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        inner()
                    }
                )
            } else {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = "Clear",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    if (query.isNotBlank()) onQueryChange("") else onCollapse()
                    keyboard?.show()
                }
            )
        }
    }
}

@Composable
private fun ResourcesTab(
    resources: List<Resource>,
    showActionsForId: String?,
    onMenuClick: (String) -> Unit,
    onDismissMenu: () -> Unit
) {
    val recents = remember(resources) { resources.filter { it.lastOpenedAt != null }.sortedByDescending { it.lastOpenedAt } }
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeader(title = "Recent Resources")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(recents.take(8), key = { it.id }) { res ->
                    RecentItemCard(resource = res, onClick = {}, onMenuClick = { onMenuClick(res.id) })
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeader(title = "All Resources")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                resources.forEach { res ->
                    ResourceCard(
                        resource = res,
                        menuExpanded = showActionsForId == res.id,
                        onClick = {},
                        onMenuClick = { onMenuClick(res.id) },
                        onDismissMenu = onDismissMenu
                    )
                }
            }
        }
    }
}

@Composable
private fun StudentsTab(
    students: List<WorkspaceStudent>,
    expanded: Boolean,
    selectedIds: Set<String>,
    isBulkMode: Boolean,
    eligibleFilter: Boolean?,
    appliedFilter: Boolean?,
    taggedOnly: Boolean,
    onEligibleFilter: (Boolean?) -> Unit,
    onAppliedFilter: (Boolean?) -> Unit,
    onToggleTaggedOnly: () -> Unit,
    onLongPressStudent: (String) -> Unit,
    onToggleStudent: (String) -> Unit,
    onExitBulk: () -> Unit,
    onAddTag: (StudentTag) -> Unit,
    onRemoveTag: (StudentTag) -> Unit,
    favoriteTag: StudentTag,
    priorityTag: StudentTag,
    taggedTag: StudentTag,
    onUpdateStudentNote: (String, String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SectionHeader(title = "Filters")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterPill(
                    label = "Eligible",
                    active = eligibleFilter == true,
                    onClick = { onEligibleFilter(if (eligibleFilter == true) null else true) }
                )
                FilterPill(
                    label = "Not eligible",
                    active = eligibleFilter == false,
                    onClick = { onEligibleFilter(if (eligibleFilter == false) null else false) }
                )
                FilterPill(
                    label = "Tagged",
                    active = taggedOnly,
                    onClick = onToggleTaggedOnly
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterPill(
                    label = "Applied",
                    active = appliedFilter == true,
                    onClick = { onAppliedFilter(if (appliedFilter == true) null else true) }
                )
                FilterPill(
                    label = "Not applied",
                    active = appliedFilter == false,
                    onClick = { onAppliedFilter(if (appliedFilter == false) null else false) }
                )
            }
        }

        if (isBulkMode) {
            BulkActionBar(
                selectedCount = selectedIds.size,
                onExit = onExitBulk,
                onFavorite = { onAddTag(favoriteTag) },
                onPriority = { onAddTag(priorityTag) },
                onTag = { onAddTag(taggedTag) },
                onRemoveFavorite = { onRemoveTag(favoriteTag) }
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeader(title = "Students")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                students.forEach { s ->
                    StudentCard(
                        student = s,
                        selected = s.id in selectedIds,
                        bulkMode = isBulkMode,
                        onClick = {
                            if (isBulkMode) onToggleStudent(s.id)
                        },
                        onLongPress = { onLongPressStudent(s.id) },
                        onToggleSelected = { onToggleStudent(s.id) },
                        onUpdateNote = { note -> onUpdateStudentNote(s.id, note) },
                        noteEnabled = expanded
                    )
                }
            }
        }
    }
}

@Composable
private fun NotesTab(
    notes: List<Note>,
    showActionsForId: String?,
    onMenuClick: (String) -> Unit,
    onDismissMenu: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            SectionHeader(title = "Shared Materials")
            OutlinedButton(onClick = {}) { Text("Upload") }
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            notes.forEach { note ->
                NoteCard(
                    note = note,
                    menuExpanded = showActionsForId == note.id,
                    onMenuClick = { onMenuClick(note.id) },
                    onDismissMenu = onDismissMenu
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(text = title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
}

@Composable
private fun FileIcon(type: ResourceType) {
    val (icon, tint) = when (type) {
        ResourceType.EXCEL -> Icons.Default.Description to Color(0xFF2E7D32)
        ResourceType.PDF -> Icons.Default.PictureAsPdf to Color(0xFFD32F2F)
        ResourceType.DOC -> Icons.Default.Person to Color(0xFF1976D2)
    }
    Surface(
        modifier = Modifier.size(36.dp),
        shape = RoundedCornerShape(12.dp),
        color = tint.copy(alpha = 0.12f)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(imageVector = icon, contentDescription = null, tint = tint)
        }
    }
}

@Composable
private fun RecentItemCard(
    resource: Resource,
    onClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Card(
        modifier = Modifier.width(220.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                FileIcon(type = resource.fileType)
                ActionMenuAnchor(onClick = onMenuClick)
            }
            Text(
                text = resource.fileName,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Opened ${resource.lastOpenedAt?.format(DateTimeFormatter.ofPattern("dd MMM, HH:mm")) ?: "—"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ResourceCard(
    resource: Resource,
    menuExpanded: Boolean,
    onClick: () -> Unit,
    onMenuClick: () -> Unit,
    onDismissMenu: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FileIcon(type = resource.fileType)
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(resource.fileName, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Uploaded by ${resource.uploadedBy} • ${resource.uploadedAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            ActionMenu(
                expanded = menuExpanded,
                onOpen = onMenuClick,
                onDismiss = onDismissMenu,
                actions = listOf(
                    ActionItem("Download", Icons.Default.Download),
                    ActionItem("Share", Icons.Default.Share),
                    ActionItem("Rename", Icons.Default.Edit),
                    ActionItem("Delete", Icons.Default.Delete),
                    ActionItem("Notify Students", Icons.Default.Notifications)
                )
            )
        }
    }
}

@Composable
private fun NoteCard(
    note: Note,
    menuExpanded: Boolean,
    onMenuClick: () -> Unit,
    onDismissMenu: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(note.title, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(
                        text = note.createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                ActionMenu(
                    expanded = menuExpanded,
                    onOpen = onMenuClick,
                    onDismiss = onDismissMenu,
                    actions = listOf(
                        ActionItem("Share", Icons.Default.Share),
                        ActionItem("Edit", Icons.Default.Edit),
                        ActionItem("Delete", Icons.Default.Delete),
                        ActionItem("Notify Students", Icons.Default.Notifications)
                    )
                )
            }
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            if (note.fileLink != null) {
                AssistChip(onClick = {}, label = { Text("Open link") })
            }
        }
    }
}

@Composable
private fun TagChip(tag: StudentTag) {
    AssistChip(onClick = {}, label = { Text(tag.label) })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StudentCard(
    student: WorkspaceStudent,
    selected: Boolean,
    bulkMode: Boolean,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    onToggleSelected: () -> Unit,
    onUpdateNote: (String) -> Unit,
    noteEnabled: Boolean
) {
    var noteDraft by remember(student.id) { mutableStateOf(student.note) }
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f) else MaterialTheme.colorScheme.surface
        ),
        border = if (selected) CardDefaults.outlinedCardBorder() else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        if (bulkMode) onToggleSelected()
                        onClick()
                    },
                    onLongClick = onLongPress
                )
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(student.profileResId),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(student.name, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(student.rollNumber, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                if (bulkMode) {
                    Crossfade(targetState = selected, label = "bulk_selected") { isSelected ->
                        Surface(
                            modifier = Modifier.size(24.dp),
                            shape = CircleShape,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ) {}
                    }
                }
            }
            if (student.tags.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(student.tags.toList(), key = { it.id }) { tag -> TagChip(tag) }
                }
            }
            if (noteEnabled) {
                OutlinedTextField(
                    value = noteDraft,
                    onValueChange = { noteDraft = it },
                    label = { Text("Quick note") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    trailingIcon = {
                        Text(
                            text = "Save",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .clickable { onUpdateNote(noteDraft.trim()) }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun BulkActionBar(
    selectedCount: Int,
    onExit: () -> Unit,
    onFavorite: () -> Unit,
    onPriority: () -> Unit,
    onTag: () -> Unit,
    onRemoveFavorite: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f))
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("$selectedCount selected", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = onFavorite) { Text("Favorite") }
                Button(onClick = onPriority) { Text("Priority") }
                Button(onClick = onTag) { Text("Tag") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = onRemoveFavorite) { Text("Remove Favorite") }
                OutlinedButton(onClick = onExit) { Text("Done") }
            }
        }
    }
}

@Composable
private fun FilterPill(
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = if (active) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelMedium,
            color = if (active) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private data class ActionItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
private fun ActionMenuAnchor(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.MoreVert,
        contentDescription = "Menu",
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .size(22.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun ActionMenu(
    expanded: Boolean,
    onOpen: () -> Unit,
    onDismiss: () -> Unit,
    actions: List<ActionItem>
) {
    Box {
        ActionMenuAnchor(onClick = onOpen)
        DropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
            actions.forEach { a ->
                DropdownMenuItem(
                    text = { Text(a.label) },
                    leadingIcon = { Icon(a.icon, contentDescription = a.label) },
                    onClick = onDismiss
                )
            }
        }
    }
}

