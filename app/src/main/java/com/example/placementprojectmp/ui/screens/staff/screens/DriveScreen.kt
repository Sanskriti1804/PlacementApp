package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.cards.CompanyCard
import com.example.placementprojectmp.ui.screens.shared.cards.DriveCard
import com.example.placementprojectmp.ui.screens.shared.cards.JobCard
import com.example.placementprojectmp.viewmodel.ApplicationTab
import com.example.placementprojectmp.viewmodel.FilterState
import com.example.placementprojectmp.viewmodel.Industry
import com.example.placementprojectmp.viewmodel.JobType
import com.example.placementprojectmp.viewmodel.JobUiModel
import com.example.placementprojectmp.viewmodel.StaffDriveViewModel
import com.example.placementprojectmp.viewmodel.Status
import com.example.placementprojectmp.viewmodel.WorkMode
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaffDriveScreen(
    modifier: Modifier = Modifier,
    onCompanyClick: (String) -> Unit = {},
    onDriveClick: (String) -> Unit = {},
    onJobClick: (String) -> Unit = {},
    onCandidateDoubleClick: (String) -> Unit = {},
    viewModel: StaffDriveViewModel = koinViewModel()
) {
    val state by viewModel.uiState
    val uriHandler = LocalUriHandler.current
    val listState = rememberLazyListState()
    BackHandler(enabled = state.isSearchExpanded) {
        viewModel.collapseSearch()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::showFabSheet,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add options"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Manage",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Companies, Drives and Jobs",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                ExpandableSearchBar(
                    query = state.searchQuery,
                    isExpanded = state.isSearchExpanded,
                    onExpand = viewModel::expandSearch,
                    onCollapse = viewModel::collapseSearch,
                    onQueryChange = viewModel::onSearchQueryChanged,
                    onFilterClick = viewModel::showFilterSheet
                )
            }

            item {
                ApplicationTabRow(
                    selectedTab = state.selectedTab,
                    onTabSelected = viewModel::onTabSelected
                )
            }

            when (state.selectedTab) {
                ApplicationTab.COMPANY -> {
                    items(state.filteredCompanies, key = { it.name }) { company ->
                        CompanyCard(
                            modifier = Modifier.fillMaxWidth(),
                            company = company,
                            onWebsiteClick = { url -> uriHandler.openUri(url) },
                            onClick = { onCompanyClick(company.id) },
                            onCandidateDoubleClick = { onCandidateDoubleClick(company.id) }
                        )
                    }
                }

                ApplicationTab.DRIVE -> {
                    items(state.filteredDrives, key = { it.id }) { drive ->
                        DriveCard(
                            drive = drive,
                            onRegisterClick = {},
                            onClick = { onDriveClick(drive.id) },
                            onCandidateDoubleClick = { onCandidateDoubleClick(drive.id) }
                        )
                    }
                }

                ApplicationTab.JOBS -> {
                    items(state.filteredJobs, key = { it.id }) { job ->
                        JobCard(
                            job = job,
                            onApplyClick = {},
                            onClick = { onJobClick(job.id) },
                            onCandidateDoubleClick = { onCandidateDoubleClick(job.id) }
                        )
                    }
                }
            }
        }
    }

    if (state.showFabSheet) {
        AddEntityBottomSheet(
            onDismiss = viewModel::hideFabSheet,
            onAddCompany = viewModel::hideFabSheet,
            onAddDrive = viewModel::hideFabSheet,
            onAddJob = viewModel::hideFabSheet
        )
    }

    if (state.showFilterSheet) {
        FilterBottomSheet(
            filterState = state.filterState,
            onDismiss = viewModel::hideFilterSheet,
            onStatusToggle = viewModel::toggleStatus,
            onIndustryToggle = viewModel::toggleIndustry,
            onJobTypeToggle = viewModel::toggleJobType,
            onWorkModeToggle = viewModel::toggleWorkMode,
            onCompanyQueryChanged = viewModel::onCompanyFilterQueryChanged,
            onLocationChanged = viewModel::onLocationFilterChanged,
            onSalaryRangeChanged = viewModel::onSalaryRangeChanged,
            onReset = viewModel::resetFilters,
            onApply = viewModel::applyFilters
        )
    }
}

@Composable
private fun ExpandableSearchBar(
    query: String,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val animatedWidth by animateDpAsState(
        targetValue = if (isExpanded) 360.dp else 182.dp,
        animationSpec = tween(260),
        label = "search_width_animation"
    )

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val targetWidth = if (animatedWidth > maxWidth) maxWidth else animatedWidth
        Row(
            modifier = Modifier
                .width(targetWidth)
                .height(52.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onExpand() }
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            AnimatedVisibility(visible = isExpanded) {
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    decorationBox = { inner ->
                        if (query.isBlank()) {
                            Text(
                                text = "Search companies, drives, jobs...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        inner()
                    }
                )
            }
            if (!isExpanded && query.isBlank()) {
                Text(
                    text = "Search companies, drives, jobs...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filters",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable(onClick = onFilterClick)
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Clear search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable {
                    if (query.isNotBlank()) onQueryChange("")
                    else onCollapse()
                }
            )
        }
    }
}

@Composable
private fun ApplicationTabRow(
    selectedTab: ApplicationTab,
    onTabSelected: (ApplicationTab) -> Unit
) {
    val tabs = listOf(ApplicationTab.COMPANY, ApplicationTab.DRIVE, ApplicationTab.JOBS)
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
            animationSpec = tween(220),
            label = "tab_indicator_offset"
        )
        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
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
                        text = tab.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEntityBottomSheet(
    onDismiss: () -> Unit,
    onAddCompany: () -> Unit,
    onAddDrive: () -> Unit,
    onAddJob: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Quick Add", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Button(onClick = onAddCompany, modifier = Modifier.fillMaxWidth()) { Text("Add Company") }
            Button(onClick = onAddDrive, modifier = Modifier.fillMaxWidth()) { Text("Add Drive") }
            Button(onClick = onAddJob, modifier = Modifier.fillMaxWidth()) { Text("Add Job") }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterBottomSheet(
    filterState: FilterState,
    onDismiss: () -> Unit,
    onStatusToggle: (Status) -> Unit,
    onIndustryToggle: (Industry) -> Unit,
    onJobTypeToggle: (JobType) -> Unit,
    onWorkModeToggle: (WorkMode) -> Unit,
    onCompanyQueryChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onSalaryRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Filter Jobs, Drives, and Companies",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            FilterChipRow(
                title = "Status",
                values = Status.entries,
                selected = filterState.status,
                label = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
                onToggle = onStatusToggle
            )
            FilterChipRow(
                title = "Industry Type",
                values = Industry.entries,
                selected = filterState.industry,
                label = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
                onToggle = onIndustryToggle
            )
            FilterChipRow(
                title = "Job Type",
                values = JobType.entries,
                selected = filterState.jobType,
                label = { it.label },
                onToggle = onJobTypeToggle
            )
            FilterChipRow(
                title = "Work Mode",
                values = WorkMode.entries,
                selected = filterState.workMode,
                label = { it.label },
                onToggle = onWorkModeToggle
            )
            OutlinedTextField(
                value = filterState.companyQuery,
                onValueChange = onCompanyQueryChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Company Name") }
            )
            OutlinedTextField(
                value = filterState.location,
                onValueChange = onLocationChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Location") }
            )
            Text(
                text = "Salary Range (LPA): ${filterState.salaryRange.start.toInt()} - ${filterState.salaryRange.endInclusive.toInt()}",
                style = MaterialTheme.typography.labelMedium
            )
            RangeSlider(
                value = filterState.salaryRange,
                onValueChange = onSalaryRangeChanged,
                valueRange = 2f..40f
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = onReset, modifier = Modifier.weight(1f)) { Text("Reset") }
                Button(
                    onClick = {
                        onApply()
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Apply Filters") }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun <T> FilterChipRow(
    title: String,
    values: List<T>,
    selected: Set<T>,
    label: (T) -> String,
    onToggle: (T) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(text = title, style = MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            values.take(3).forEach { item ->
                FilterChip(
                    selected = item in selected,
                    onClick = { onToggle(item) },
                    label = { Text(label(item)) }
                )
            }
        }
        if (values.size > 3) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                values.drop(3).forEach { item ->
                    FilterChip(
                        selected = item in selected,
                        onClick = { onToggle(item) },
                        label = { Text(label(item)) }
                    )
                }
            }
        }
    }
}
