package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppSearchBar
import com.example.placementprojectmp.ui.screens.shared.cards.CompanyCard
import com.example.placementprojectmp.ui.screens.shared.cards.DriveCard
import com.example.placementprojectmp.viewmodel.ApplicationTab
import com.example.placementprojectmp.viewmodel.FilterState
import com.example.placementprojectmp.viewmodel.Industry
import com.example.placementprojectmp.viewmodel.JobType
import com.example.placementprojectmp.viewmodel.StaffDriveViewModel
import com.example.placementprojectmp.viewmodel.Status
import com.example.placementprojectmp.viewmodel.WorkMode
import org.koin.androidx.compose.koinViewModel

/**
 * System Company Management: same structure as [com.example.placementprojectmp.ui.screens.staff.screens.StaffDriveScreen],
 * but only **Company** and **Drive** tabs (AppTabSection-style strip, equal width).
 */
@Composable
fun CompanyManagementScreen(
    modifier: Modifier = Modifier,
    onCompanyClick: (String) -> Unit = {},
    onDriveClick: (String) -> Unit = {},
    viewModel: StaffDriveViewModel = koinViewModel()
) {
    val state by viewModel.uiState
    val uriHandler = LocalUriHandler.current
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.onTabSelected(ApplicationTab.COMPANY)
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
                AppSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Search companies, drives, jobs...",
                    query = state.searchQuery,
                    onQueryChange = viewModel::onSearchQueryChanged,
                    onFilterClick = viewModel::showFilterSheet
                )
            }

            item {
                val stripSelection = when (state.selectedTab) {
                    ApplicationTab.DRIVE -> CompanyDriveStripTab.DRIVE
                    else -> CompanyDriveStripTab.COMPANY
                }
                SystemCompanyDriveTabStrip(
                    selectedTab = stripSelection,
                    onTabSelected = { tab ->
                        viewModel.onTabSelected(
                            when (tab) {
                                CompanyDriveStripTab.COMPANY -> ApplicationTab.COMPANY
                                CompanyDriveStripTab.DRIVE -> ApplicationTab.DRIVE
                            }
                        )
                    }
                )
            }

            when (state.selectedTab) {
                ApplicationTab.DRIVE -> {
                    items(state.filteredDrives, key = { it.id }) { drive ->
                        DriveCard(
                            drive = drive,
                            onRegisterClick = {},
                            onClick = { onDriveClick(drive.id) }
                        )
                    }
                }
                ApplicationTab.COMPANY, ApplicationTab.JOBS -> {
                    items(state.filteredCompanies, key = { it.name }) { company ->
                        CompanyCard(
                            modifier = Modifier.fillMaxWidth(),
                            company = company,
                            onWebsiteClick = { url -> uriHandler.openUri(url) },
                            onClick = { onCompanyClick(company.id) }
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

private enum class CompanyDriveStripTab {
    COMPANY,
    DRIVE
}

/**
 * Same visual pattern as [com.example.placementprojectmp.ui.screens.shared.component.AppTabSection]
 * (underline + divider), two equal-width tabs.
 */
@Composable
private fun SystemCompanyDriveTabStrip(
    selectedTab: CompanyDriveStripTab,
    onTabSelected: (CompanyDriveStripTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabTitles = listOf("Company", "Drive")
    val tabKeys = listOf(CompanyDriveStripTab.COMPANY, CompanyDriveStripTab.DRIVE)
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
        label = "system_company_drive_tab_offset"
    )
    val animatedWidth by animateDpAsState(
        targetValue = targetWidthDp,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "system_company_drive_tab_width"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        rowRootX = coordinates.positionInRoot().x
                    }
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
