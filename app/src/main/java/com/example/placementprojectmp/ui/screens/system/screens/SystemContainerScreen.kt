package com.example.placementprojectmp.ui.screens.system.screens

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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
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
import com.example.placementprojectmp.ui.components.AppTopBar

@Composable
fun SystemContainerScreen(
    modifier: Modifier = Modifier
) {
    val innerNavController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            SystemBottomNav(
                navController = innerNavController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AppTopBar(title = "System")
            NavHost(
                navController = innerNavController,
                startDestination = Routes.SystemRoutes.SystemManagement,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Routes.SystemRoutes.Start) {
                    StartScreen(
                        onDashboardClick = { innerNavController.navigate(Routes.SystemRoutes.SystemDashboard) },
                        onManagementClick = { innerNavController.navigate(Routes.SystemRoutes.SystemManagement) },
                        onSettingsClick = { innerNavController.navigate(Routes.SystemRoutes.SystemSettings) }
                    )
                }
                composable(Routes.SystemRoutes.SystemManagement) {
                    SystemManagementScreen(
                        modifier = modifier,
                        onJobManagementClick = { innerNavController.navigate(Routes.SystemRoutes.JobManagement) }
                    )
                }
                composable(Routes.SystemRoutes.JobManagement) {
                    JobManagementScreen(modifier = modifier)
                }
                composable(Routes.SystemRoutes.SystemDashboard) {
                    SystemDashboardScreen(modifier = modifier)
                }
                composable(Routes.SystemRoutes.SystemSettings) {
                    SystemSettingsScreen(modifier = modifier)
                }
                composable(Routes.SystemRoutes.SystemProfile) {
                    SystemProfileScreen(modifier = modifier)
                }
            }
        }
    }
}

@Composable
private fun SystemBottomNav(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        SystemNavItem(
            route = Routes.SystemRoutes.SystemManagement,
            label = "Management",
            icon = Icons.Default.Home,
            isCenter = false,
            navigateOnClick = true
        ),
        SystemNavItem(
            route = Routes.SystemRoutes.JobManagement,
            label = "Jobs",
            icon = Icons.Default.Work,
            isCenter = false,
            navigateOnClick = true
        ),
        SystemNavItem(
            route = Routes.SystemRoutes.SystemDashboard,
            label = "Dashboard",
            icon = Icons.Default.Dashboard,
            isCenter = true,
            navigateOnClick = true
        ),
        SystemNavItem(
            route = Routes.SystemRoutes.SystemSettings,
            label = "Settings",
            icon = Icons.Default.Settings,
            isCenter = false,
            navigateOnClick = true
        ),
        SystemNavItem(
            route = Routes.SystemRoutes.SystemProfile,
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
                SystemBottomNavItem(
                    item = item,
                    isSelected = currentDestination?.route == item.route,
                    onClick = {
                        if (!item.navigateOnClick) return@SystemBottomNavItem
                        navController.navigate(item.route) {
                            popUpTo(Routes.SystemRoutes.SystemManagement) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

private data class SystemNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector?,
    val isCenter: Boolean = false,
    val navigateOnClick: Boolean = true
)

@Composable
private fun SystemBottomNavItem(
    item: SystemNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val clickableModifier = if (item.navigateOnClick) {
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
            .then(clickableModifier),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isSelected,
            transitionSpec = {
                (fadeIn(tween(120)) + scaleIn(initialScale = 0.92f))
                    .togetherWith(fadeOut(tween(120)) + scaleOut(targetScale = 0.92f))
            },
            label = "system_tab_selection"
        ) { selected ->
            if (item.isCenter) {
                Surface(
                    modifier = Modifier.size(48.dp),
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
