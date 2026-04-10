package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.theme.NeonBlue
import com.example.placementprojectmp.viewmodel.ApplicationTab
import com.example.placementprojectmp.viewmodel.DriveUiModel
import com.example.placementprojectmp.viewmodel.FilterState
import com.example.placementprojectmp.viewmodel.Industry
import com.example.placementprojectmp.viewmodel.JobDepartment
import com.example.placementprojectmp.viewmodel.JobType
import com.example.placementprojectmp.viewmodel.JobUiModel
import com.example.placementprojectmp.viewmodel.StaffDriveUiState
import com.example.placementprojectmp.viewmodel.StaffDriveViewModel
import com.example.placementprojectmp.viewmodel.Status
import com.example.placementprojectmp.viewmodel.WorkMode
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun StaffDriveScreen(
    modifier: Modifier = Modifier,
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
                            onWebsiteClick = { url -> uriHandler.openUri(url) }
                        )
                    }
                }

                ApplicationTab.DRIVE -> {
                    items(state.filteredDrives, key = { it.id }) { drive ->
                        ApplicationDriveCard(
                            drive = drive,
                            onRegisterClick = {}
                        )
                    }
                }

                ApplicationTab.JOBS -> {
                    items(state.filteredJobs, key = { it.id }) { job ->
                        JobCard(
                            job = job,
                            onApplyClick = {}
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

@Composable
private fun ApplicationDriveCard(
    drive: DriveUiModel,
    onRegisterClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                LogoImage(logoResId = drive.companyLogoResId)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = drive.companyName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = drive.driveName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
                DateText(label = "Start", date = drive.startDate)
            }
            BottomActionRow(
                dateLabel = "Register closes at",
                date = drive.lastDateToRegister,
                buttonText = "Register",
                onButtonClick = onRegisterClick
            )
        }
    }
}

@Composable
private fun JobCard(
    job: JobUiModel,
    onApplyClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                LogoImage(logoResId = job.companyLogoResId)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = job.companyName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = job.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                text = job.jobRole,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            StatusChip(department = job.department)
            BottomActionRow(
                dateLabel = "Last date to apply",
                date = job.lastDate,
                buttonText = "Apply",
                onButtonClick = onApplyClick
            )
        }
    }
}

@Composable
private fun LogoImage(
    logoResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = logoResId),
            contentDescription = "Company logo",
            modifier = Modifier.size(28.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun BottomActionRow(
    dateLabel: String,
    date: LocalDate,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateText(label = dateLabel, date = date)
        OutlinedButton(onClick = onButtonClick, shape = RoundedCornerShape(14.dp)) {
            Text(text = buttonText)
        }
    }
}

@Composable
private fun DateText(
    label: String,
    date: LocalDate
) {
    Text(
        text = "$label: ${date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun StatusChip(
    department: JobDepartment
) {
    val color = when (department) {
        JobDepartment.TECH -> Color(0xFF1E88E5)
        JobDepartment.MANAGEMENT -> Color(0xFF8E24AA)
        JobDepartment.CORE -> Color(0xFFEF6C00)
    }
    AssistChip(
        onClick = {},
        label = {
            Text(
                text = department.name.lowercase().replaceFirstChar { it.uppercase() },
                color = color
            )
        },
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    )
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

@Composable
private fun CompanyCard(
    modifier: Modifier,
    company: com.example.placementprojectmp.viewmodel.CompanyUiModel,
    onWebsiteClick: (String) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                LogoImage(logoResId = company.logoResId)
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
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CompanyField(label = "Industry", value = company.industry.name.lowercase(), modifier = Modifier.weight(1f))
                CompanyField(label = "Type", value = company.companyType, modifier = Modifier.weight(1f))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clickable { onWebsiteClick(company.website) }
            ) {
                Text(
                    text = company.website.removePrefix("https://").removePrefix("http://"),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
                Icon(
                    imageVector = Icons.Default.ArrowOutward,
                    contentDescription = "Open website",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = company.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun CompanyField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
