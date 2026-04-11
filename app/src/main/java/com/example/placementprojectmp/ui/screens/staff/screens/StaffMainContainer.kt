package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.placementprojectmp.navigation.Routes
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.ui.screens.shared.screens.TeacherCompanyDetailsScreen

/**
 * Staff shell: same bottom bar pattern as [com.example.placementprojectmp.ui.screens.student.screens.StudentMainContainer].
 * Tabs: Students | Opportunity | Dashboard (center) | Workspace | Profile.
 */
@Composable
fun StaffMainContainer(
    modifier: Modifier = Modifier
) {
    val innerNavController = rememberNavController()
    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showTopBar = currentRoute != Routes.StaffRoutes.TeacherCompanyDetails

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            StaffBottomNav(
                navController = innerNavController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (showTopBar) {
                AppTopBar()
            }
            NavHost(
                navController = innerNavController,
                startDestination = Routes.StaffRoutes.StudentDetails,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Routes.StaffRoutes.StudentDetails) {
                    StudentDetailsScreen(modifier = modifier)
                }
                composable(Routes.StaffRoutes.TeacherCompanyDetails) {
                    TeacherCompanyDetailsScreen(modifier = modifier)
                }
                composable(Routes.StaffRoutes.StaffDashboard) {
                    StaffDashboardScreen(modifier = modifier)
                }
                composable(Routes.StaffRoutes.Drive) {
                    StaffDriveScreen(
                        modifier = modifier,
                        onCompanyClick = {
                            innerNavController.navigate(Routes.StaffRoutes.TeacherCompanyDetails)
                        },
                        onDriveClick = { driveId ->
                            innerNavController.navigate(Routes.StaffRoutes.driveDetail(driveId))
                        },
                        onJobClick = { jobId ->
                            innerNavController.navigate(Routes.StaffRoutes.jobDetail(jobId))
                        }
                    )
                }
                composable(Routes.StaffRoutes.TeacherProfile) {
                    TeacherProfileScreen(modifier = modifier)
                }
                composable(
                    route = Routes.StaffRoutes.DriveDetail,
                    arguments = listOf(navArgument("driveId") {})
                ) { backStackEntry ->
                    StaffDriveDetailScreen(
                        modifier = modifier,
                        driveId = backStackEntry.arguments?.getString("driveId").orEmpty()
                    )
                }
                composable(
                    route = Routes.StaffRoutes.JobDetail,
                    arguments = listOf(navArgument("jobId") {})
                ) { backStackEntry ->
                    StaffJobDetailScreen(
                        modifier = modifier,
                        jobId = backStackEntry.arguments?.getString("jobId").orEmpty()
                    )
                }
                composable(
                    route = Routes.StaffRoutes.CandidateDetail,
                    arguments = listOf(navArgument("sourceId") {})
                ) { backStackEntry ->
                    StaffCandidateDetailScreen(
                        modifier = modifier,
                        sourceId = backStackEntry.arguments?.getString("sourceId").orEmpty()
                    )
                }
                composable(Routes.StaffRoutes.PlacementWorkspace) {
                    PlacementWorkspaceScreen(modifier = modifier)
                }
            }
        }
    }
}

@Composable
private fun StaffBottomNav(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        StaffNavItem(
            route = Routes.StaffRoutes.StudentDetails,
            label = "Students",
            icon = Icons.Default.School,
            isCenter = false,
            navigateOnClick = true
        ),
        StaffNavItem(
            route = Routes.StaffRoutes.Drive,
            label = "Opportunity",
            icon = Icons.Default.Star,
            isCenter = false,
            navigateOnClick = true
        ),
        StaffNavItem(
            route = Routes.StaffRoutes.StaffDashboard,
            label = "Dashboard",
            icon = Icons.Default.Dashboard,
            isCenter = true,
            navigateOnClick = true
        ),
        StaffNavItem(
            route = Routes.StaffRoutes.PlacementWorkspace,
            label = "Workspace",
            icon = Icons.Default.Work,
            isCenter = false,
            navigateOnClick = true
        ),
        StaffNavItem(
            route = Routes.StaffRoutes.TeacherProfile,
            label = "Profile",
            icon = Icons.Default.Person,
            isCenter = false,
            navigateOnClick = true
        )
    )

    Surface(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .shadow(12.dp, RoundedCornerShape(32.dp))
            .clip(RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                StaffBottomNavItem(
                    item = item,
                    isSelected = currentDestination?.route == item.route,
                    onClick = {
                        if (!item.navigateOnClick) return@StaffBottomNavItem
                        navController.navigate(item.route) {
                            popUpTo(Routes.StaffRoutes.StudentDetails) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

private data class StaffNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector?,
    val isCenter: Boolean = false,
    val navigateOnClick: Boolean = true
)

@Composable
private fun StaffBottomNavItem(
    item: StaffNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val edgeTabClickable = if (!item.isCenter && item.navigateOnClick) {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
    } else {
        Modifier
    }
    val centerSurfaceClickable = if (item.isCenter && item.navigateOnClick) {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .padding(4.dp)
            .clip(CircleShape)
            .then(edgeTabClickable),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isSelected,
            transitionSpec = {
                (fadeIn(tween(120)) + scaleIn(initialScale = 0.92f))
                    .togetherWith(fadeOut(tween(120)) + scaleOut(targetScale = 0.92f))
            },
            label = "staff_tab_selection"
        ) { selected ->
            if (item.isCenter) {
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .then(centerSurfaceClickable),
                    shape = CircleShape,
                    color = if (selected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item.icon?.let { icon ->
                            Icon(
                                imageVector = icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(26.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp),
                            tint = if (selected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    }
}
