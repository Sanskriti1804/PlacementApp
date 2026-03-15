package com.example.placementprojectmp.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
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
import com.example.placementprojectmp.navigation.Routes
import com.example.placementprojectmp.ui.components.AppLogo

/**
 * Main container for the student module: Scaffold with top content area and custom bottom navigation.
 * Uses an inner NavHost for the five tab destinations (Applications, Opportunities, Dashboard, Prepare, Profile).
 */
@Composable
fun StudentMainContainer(
    modifier: Modifier = Modifier,
    outerNavController: androidx.navigation.NavHostController? = null
) {
    val innerNavController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            StudentBottomNav(
                navController = innerNavController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = innerNavController,
            startDestination = Routes.StudentRoutes.Dashboard,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable(Routes.StudentRoutes.Applications) {
                StudentTabPlaceholderScreen(title = "Applications", modifier = modifier)
            }
            composable(Routes.StudentRoutes.Opportunities) {
                StudentTabPlaceholderScreen(title = "Opportunities", modifier = modifier)
            }
            composable(Routes.StudentRoutes.Dashboard) {
                StudentDashboardScreen(modifier = modifier)
            }
            composable(Routes.StudentRoutes.Prepare) {
                StudentTabPlaceholderScreen(title = "Prepare", modifier = modifier)
            }
            composable(Routes.StudentRoutes.StudentProfile) {
                StudentTabPlaceholderScreen(title = "Profile", modifier = modifier)
            }
        }
    }
}

/**
 * Custom bottom navigation bar: rounded capsule container with five items.
 * Dashboard (center) uses the app logo and is visually elevated/highlighted.
 */
@Composable
private fun StudentBottomNav(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        StudentNavItem(
            route = Routes.StudentRoutes.Applications,
            label = "Applications",
            icon = Icons.Default.Description
        ),
        StudentNavItem(
            route = Routes.StudentRoutes.Opportunities,
            label = "Opportunities",
            icon = Icons.Default.Work
        ),
        StudentNavItem(
            route = Routes.StudentRoutes.Dashboard,
            label = "Dashboard",
            isCenter = true
        ),
        StudentNavItem(
            route = Routes.StudentRoutes.Prepare,
            label = "Prepare",
            icon = Icons.Default.School
        ),
        StudentNavItem(
            route = Routes.StudentRoutes.StudentProfile,
            label = "Profile",
            icon = Icons.Default.Person
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
                StudentBottomNavItem(
                    item = item,
                    isSelected = currentDestination?.route == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

private data class StudentNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector? = null,
    val isCenter: Boolean = false
)

@Composable
private fun StudentBottomNavItem(
    item: StudentNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(
        targetValue = when {
            item.isCenter && isSelected -> 6.dp
            item.isCenter -> 4.dp
            isPressed -> 2.dp
            else -> 0.dp
        },
        label = "elevation"
    )
    Box(
        modifier = modifier
            .padding(4.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isSelected,
            transitionSpec = {
                (fadeIn(tween(120)) + scaleIn(initialScale = 0.92f))
                    .togetherWith(fadeOut(tween(120)) + scaleOut(targetScale = 0.92f))
            },
            label = "tab_selection"
        ) { selected ->
            if (item.isCenter) {
                Surface(
                    modifier = Modifier
                        .size(48.dp + elevation)
                        .shadow(elevation, CircleShape),
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
                        AppLogo(
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
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

@Composable
private fun StudentTabPlaceholderScreen(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
