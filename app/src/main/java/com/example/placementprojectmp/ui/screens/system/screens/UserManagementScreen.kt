package com.example.placementprojectmp.ui.screens.system.screens

import android.util.Log
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppSearchBar
import com.example.placementprojectmp.ui.screens.shared.component.cards.UserIdCard
import com.example.placementprojectmp.ui.screens.staff.StaffStudentPortraitIds
import com.example.placementprojectmp.ui.screens.student.component.CourseDomainMappingFilter
import com.example.placementprojectmp.viewmodel.EducationViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "SystemManagement"

private enum class UserManagementTab {
    STUDENTS,
    FACULTY
}

private data class ManagedIdentity(
    val id: String,
    val name: String,
    val email: String,
    val idCode: String,
    val department: String,
    val course: String,
    val domain: String,
    val secondaryRowLabel: String,
    val secondaryRowValue: String,
    val capsuleTag: String?,
    val portraitResId: Int
)

@Composable
fun SystemManagementScreen(
    modifier: Modifier = Modifier,
    onJobManagementClick: () -> Unit = {}
) {
    val educationViewModel: EducationViewModel = koinViewModel()
    var searchQuery by remember { mutableStateOf("") }
    var selectedDomains by remember { mutableStateOf(setOf<String>()) }
    var selectedTab by remember { mutableStateOf(UserManagementTab.STUDENTS) }
    var activeCourseFilter by remember { mutableStateOf<String?>(null) }

    val selectedIds = remember { mutableStateListOf<String>() }
    val favoriteIds = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        educationViewModel.fetchCourses()
    }

    LaunchedEffect(selectedTab) {
        selectedIds.clear()
    }

    val students = remember {
        listOf(
            ManagedIdentity(
                id = "s1",
                name = "Rahul Sharma",
                email = "rahul.sharma@institute.edu",
                idCode = "CSE-BT-2101",
                department = "Computer Science",
                course = "BTECH",
                domain = "SOFTWARE DEVELOPMENT",
                secondaryRowLabel = "Hired",
                secondaryRowValue = "Google",
                capsuleTag = null,
                portraitResId = StaffStudentPortraitIds.all[0]
            ),
            ManagedIdentity(
                id = "s2",
                name = "Priya Mehta",
                email = "priya.mehta@institute.edu",
                idCode = "ECE-BT-2104",
                department = "Electronics",
                course = "BTECH",
                domain = "EMBEDDED SYSTEMS",
                secondaryRowLabel = "Hired",
                secondaryRowValue = "NA",
                capsuleTag = null,
                portraitResId = StaffStudentPortraitIds.all[1]
            ),
            ManagedIdentity(
                id = "s3",
                name = "Amit Kumar",
                email = "amit.kumar@institute.edu",
                idCode = "MCA-PG-2302",
                department = "Applications",
                course = "MCA",
                domain = "WEB DEVELOPMENT",
                secondaryRowLabel = "Hired",
                secondaryRowValue = "Infosys",
                capsuleTag = null,
                portraitResId = StaffStudentPortraitIds.all[2]
            )
        )
    }
    val faculty = remember {
        listOf(
            ManagedIdentity(
                id = "f1",
                name = "Dr. Neha Kapoor",
                email = "neha.kapoor@institute.edu",
                idCode = "FAC-PLAC-01",
                department = "Placement Cell",
                course = "MBA",
                domain = "HUMAN RESOURCES",
                secondaryRowLabel = "Roles",
                secondaryRowValue = "Placement Head",
                capsuleTag = "Faculty",
                portraitResId = StaffStudentPortraitIds.all[3]
            ),
            ManagedIdentity(
                id = "f2",
                name = "Prof. Vikram Desai",
                email = "vikram.desai@institute.edu",
                idCode = "FAC-CSE-12",
                department = "Computer Science",
                course = "BTECH",
                domain = "DATA SCIENCE",
                secondaryRowLabel = "Roles",
                secondaryRowValue = "Coordinator",
                capsuleTag = "Faculty",
                portraitResId = StaffStudentPortraitIds.all[0]
            ),
            ManagedIdentity(
                id = "f3",
                name = "Ananya Iyer",
                email = "ananya.iyer@institute.edu",
                idCode = "FAC-ECE-08",
                department = "Electronics",
                course = "BTECH",
                domain = "NETWORK ENGINEERING",
                secondaryRowLabel = "Roles",
                secondaryRowValue = "Mentor",
                capsuleTag = "Faculty",
                portraitResId = StaffStudentPortraitIds.all[1]
            )
        )
    }

    val q = searchQuery.trim()
    val baseList = when (selectedTab) {
        UserManagementTab.STUDENTS -> students
        UserManagementTab.FACULTY -> faculty
    }

    val filtered = remember(q, baseList, selectedDomains, activeCourseFilter) {
        baseList.filter { user ->
            val searchOk = q.isBlank() ||
                user.name.contains(q, ignoreCase = true) ||
                user.email.contains(q, ignoreCase = true) ||
                user.idCode.contains(q, ignoreCase = true)
            val courseOk = activeCourseFilter == null || user.course == activeCourseFilter
            val domainOk = selectedDomains.isEmpty() || user.domain in selectedDomains
            searchOk && courseOk && domainOk
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "User Management",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Manage students and faculty in one place",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                AppSearchBar(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    placeholder = "Search by name, email, or ID...",
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onFilterClick = {}
                )
            }
            CourseDomainMappingFilter(
                courses = educationViewModel.courses,
                courseDomains = educationViewModel.domains,
                selectedDomains = selectedDomains,
                isLoading = educationViewModel.isLoading,
                onCourseClick = { course ->
                    runCatching {
                        activeCourseFilter = course
                        educationViewModel.fetchDomains(course)
                        selectedDomains = emptySet()
                    }.onFailure { e ->
                        Log.e(TAG, "fetchDomains failed for course=$course", e)
                        selectedDomains = emptySet()
                    }
                },
                onDomainToggle = { domain ->
                    selectedDomains = if (domain in selectedDomains) {
                        selectedDomains - domain
                    } else {
                        selectedDomains + domain
                    }
                }
            )
            item {
                UserManagementTabStrip(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
            items(items = filtered, key = { it.id }) { user ->
                UserIdCard(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    name = user.name,
                    email = user.email,
                    idText = user.idCode,
                    profileImageResId = user.portraitResId,
                    searchQuery = q,
                    departmentValue = user.department,
                    secondaryAttributeLabel = user.secondaryRowLabel,
                    secondaryAttributeValue = user.secondaryRowValue,
                    optionalEndTag = user.capsuleTag,
                    tags = emptyList(),
                    selected = user.id in selectedIds,
                    onSelectionChange = { checked ->
                        if (checked) selectedIds.add(user.id) else selectedIds.remove(user.id)
                    },
                    isFavorite = user.id in favoriteIds,
                    onFavoriteToggle = {
                        if (user.id in favoriteIds) favoriteIds.remove(user.id) else favoriteIds.add(user.id)
                    }
                )
            }
            item {
                OutlinedButton(
                    onClick = onJobManagementClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text("Open Job Management")
                }
            }
        }
    }
}

/**
 * Visual parity with [com.example.placementprojectmp.ui.screens.shared.component.AppTabSection]
 * (underline + divider); two equal-width tabs [Students] [Faculty].
 */
@Composable
private fun UserManagementTabStrip(
    selectedTab: UserManagementTab,
    onTabSelected: (UserManagementTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabTitles = listOf("Students", "Faculty")
    val tabKeys = listOf(UserManagementTab.STUDENTS, UserManagementTab.FACULTY)
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
        label = "system_user_tab_offset"
    )
    val animatedWidth by animateDpAsState(
        targetValue = targetWidthDp,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "system_user_tab_width"
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
