package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.StudentViewMode
import com.example.placementprojectmp.ui.screens.shared.component.AppSearchBar
import com.example.placementprojectmp.ui.screens.staff.components.ResourceFolderRow
import com.example.placementprojectmp.ui.screens.staff.components.StaffViewModeSelector
import com.example.placementprojectmp.viewmodel.Note
import com.example.placementprojectmp.viewmodel.PlacementResourceViewLayout
import com.example.placementprojectmp.viewmodel.PlacementTab
import com.example.placementprojectmp.viewmodel.PlacementWorkspaceViewModel
import com.example.placementprojectmp.viewmodel.Resource
import com.example.placementprojectmp.viewmodel.ResourceType
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun PlacementWorkspaceScreen(
    modifier: Modifier = Modifier,
    viewModel: PlacementWorkspaceViewModel = koinViewModel()
) {
    val state by viewModel.state
    val listState = rememberLazyListState()
    var fabMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.selectedTab) {
        fabMenuExpanded = false
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = { fabMenuExpanded = true },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
                DropdownMenu(
                    expanded = fabMenuExpanded,
                    onDismissRequest = { fabMenuExpanded = false },
                    offset = DpOffset(0.dp, (-8).dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Add Document") },
                        onClick = { fabMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Add Folder") },
                        onClick = { fabMenuExpanded = false }
                    )
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
                        text = "Staff Workspace",
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
                val placeholder = when (state.selectedTab) {
                    PlacementTab.RESOURCES -> "Search resources, files..."
                    PlacementTab.NOTES -> "Search notes and materials..."
                }
                AppSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = placeholder,
                    query = state.searchQuery,
                    onQueryChange = viewModel::onSearchQueryChanged,
                    onFilterClick = {}
                )
            }

            item {
                PlacementWorkspaceAppTabSection(
                    selectedTab = state.selectedTab,
                    onTabSelected = viewModel::selectTab
                ) {
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
                                resourceViewLayout = state.resourceViewLayout,
                                onResourceViewLayoutChange = viewModel::setResourceViewLayout,
                                showActionsForId = state.showResourceActionsForId,
                                onMenuClick = viewModel::showResourceActions,
                                onDismissMenu = viewModel::hideResourceActions
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
}

/**
 * Visual parity with [com.example.placementprojectmp.ui.screens.shared.component.AppTabSection]
 * (underline indicator + divider); drives [PlacementTab] selection for workspace content.
 */
@Composable
private fun PlacementWorkspaceAppTabSection(
    selectedTab: PlacementTab,
    onTabSelected: (PlacementTab) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val tabTitles = listOf("Resources", "Materials")
    val tabKeys = listOf(PlacementTab.RESOURCES, PlacementTab.NOTES)
    val selectedIndex = tabKeys.indexOf(selectedTab).coerceIn(0, tabKeys.lastIndex)
    var rowRootX by remember { mutableFloatStateOf(0f) }
    val tabSegments = remember(tabTitles) { mutableStateMapOf<Int, Pair<Float, Float>>() }
    val density = LocalDensity.current

    val targetSeg = tabSegments[selectedIndex]
    val targetOffsetDp = with(density) { (targetSeg?.first ?: 0f).toDp() }
    val targetWidthDp = with(density) { (targetSeg?.second ?: 0f).toDp() }
    val animatedOffset by animateDpAsState(
        targetValue = targetOffsetDp,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "workspace_tab_indicator_offset"
    )
    val animatedWidth by animateDpAsState(
        targetValue = targetWidthDp,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "workspace_tab_indicator_width"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        rowRootX = coordinates.positionInRoot().x
                    },
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .onGloballyPositioned { coordinates ->
                                val x = coordinates.positionInRoot().x - rowRootX
                                val w = coordinates.size.width.toFloat()
                                val next = x to w
                                if (tabSegments[index] != next) {
                                    tabSegments[index] = next
                                }
                            }
                            .clickable { onTabSelected(tabKeys[index]) }
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedIndex == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .width(animatedWidth)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.primary)
                )
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f)
            )
        }

        content()
    }
}

private val workspaceResourceFolders = listOf(
    "Student Consent Forms",
    "Drive Data",
    "Company Documents (Google Docs)",
    "Nexus Company Documents",
    "Verified Documents",
    "Documents to be Verified"
)

private val workspaceMaterialFolders = listOf(
    "Resume Checklist",
    "Notes for Interview Prep",
    "Cheat Sheets",
    "PY2 Uploads",
    "Placement Materials",
    "General Resources"
)

private val resourceCardOverflowActions = listOf(
    ActionItem("Download", Icons.Default.Download),
    ActionItem("Share", Icons.Default.Share),
    ActionItem("Rename", Icons.Default.Edit),
    ActionItem("Move to Folder", Icons.Default.Folder),
    ActionItem("Delete", Icons.Default.Delete),
    ActionItem("Notify Students", Icons.Default.Notifications)
)

private val noteCardOverflowActions = listOf(
    ActionItem("Share", Icons.Default.Share),
    ActionItem("Edit", Icons.Default.Edit),
    ActionItem("Move to Folder", Icons.Default.Folder),
    ActionItem("Delete", Icons.Default.Delete),
    ActionItem("Notify Students", Icons.Default.Notifications)
)

@Composable
private fun ResourcesTab(
    resources: List<Resource>,
    resourceViewLayout: PlacementResourceViewLayout,
    onResourceViewLayoutChange: (PlacementResourceViewLayout) -> Unit,
    showActionsForId: String?,
    onMenuClick: (String) -> Unit,
    onDismissMenu: () -> Unit
) {
    val recents = remember(resources) { resources.filter { it.lastOpenedAt != null }.sortedByDescending { it.lastOpenedAt } }
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ResourceFolderRow(
                folders = workspaceResourceFolders,
                onFolderClick = {}
            )
            SectionHeader(title = "Recent Resources")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(recents.take(8), key = { it.id }) { res ->
                    RecentItemCard(
                        resource = res,
                        menuExpanded = showActionsForId == res.id,
                        onClick = {},
                        onMenuClick = { onMenuClick(res.id) },
                        onDismissMenu = onDismissMenu
                    )
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "All Resources",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                StaffViewModeSelector(
                    currentMode = when (resourceViewLayout) {
                        PlacementResourceViewLayout.LIST -> StudentViewMode.List
                        PlacementResourceViewLayout.GRID -> StudentViewMode.Grid
                    },
                    onModeSelected = { mode ->
                        when (mode) {
                            StudentViewMode.List, StudentViewMode.Expanded -> onResourceViewLayoutChange(PlacementResourceViewLayout.LIST)
                            StudentViewMode.Grid -> onResourceViewLayoutChange(PlacementResourceViewLayout.GRID)
                        }
                    }
                )
            }
            when (resourceViewLayout) {
                PlacementResourceViewLayout.LIST -> {
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
                PlacementResourceViewLayout.GRID -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        resources.chunked(2).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowItems.forEach { res ->
                                    ResourceCard(
                                        modifier = Modifier.weight(1f),
                                        resource = res,
                                        menuExpanded = showActionsForId == res.id,
                                        onClick = {},
                                        onMenuClick = { onMenuClick(res.id) },
                                        onDismissMenu = onDismissMenu
                                    )
                                }
                                if (rowItems.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
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
        ResourceFolderRow(
            folders = workspaceMaterialFolders,
            onFolderClick = {}
        )
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

private val recentResourceCardHeight = 118.dp

@Composable
private fun RecentItemCard(
    resource: Resource,
    menuExpanded: Boolean,
    onClick: () -> Unit,
    onMenuClick: () -> Unit,
    onDismissMenu: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(recentResourceCardHeight),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                FileIcon(type = resource.fileType)
                ActionMenu(
                    expanded = menuExpanded,
                    onOpen = onMenuClick,
                    onDismiss = onDismissMenu,
                    actions = resourceCardOverflowActions
                )
            }
            Text(
                text = resource.fileName,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Opened ${resource.lastOpenedAt?.format(DateTimeFormatter.ofPattern("dd MMM, HH:mm")) ?: "—"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ResourceCard(
    modifier: Modifier = Modifier,
    resource: Resource,
    menuExpanded: Boolean,
    onClick: () -> Unit,
    onMenuClick: () -> Unit,
    onDismissMenu: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                FileIcon(type = resource.fileType)
                ActionMenu(
                    expanded = menuExpanded,
                    onOpen = onMenuClick,
                    onDismiss = onDismissMenu,
                    actions = resourceCardOverflowActions
                )
            }
            Text(
                resource.fileName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Uploaded by ${resource.uploadedBy} on ${resource.uploadedAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                    actions = noteCardOverflowActions
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

