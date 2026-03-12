package com.example.placementprojectmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.placementprojectmp.ui.screens.AboutAppScreen
import com.example.placementprojectmp.ui.screens.AcademicPerdormanceScreen
import com.example.placementprojectmp.ui.screens.LoadingScreen
import com.example.placementprojectmp.ui.screens.LoginScreen
import com.example.placementprojectmp.ui.screens.RoleSelectionScreen
import com.example.placementprojectmp.ui.screens.SplashScreen
import com.example.placementprojectmp.ui.screens.ProfileScreen
import com.example.placementprojectmp.ui.screens.ChatbotScreen
import com.example.placementprojectmp.ui.screens.PreparationScreen
import com.example.placementprojectmp.ui.screens.OpportunitiesScreen
import com.example.placementprojectmp.ui.screens.StudentDetailsScreen
import com.example.placementprojectmp.ui.screens.StudentDashboardScreen
import com.example.placementprojectmp.ui.screens.StudentProfileFormScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.StudentProfileForm,
        modifier = modifier
    ) {
        // 1. Splash → About (splash is removed from back stack)
        composable(Routes.Splash) {
            var hasNavigated by remember { mutableStateOf(false) }
            SplashScreen(
                modifier = modifier,
                onNavigateToAbout = {
                    if (!hasNavigated) {
                        hasNavigated = true
                        navController.navigate(Routes.About) {
                            popUpTo(Routes.Splash) { inclusive = true }
                        }
                    }
                }
            )
        }

        // 2. About → Login
        composable(Routes.About) {
            AboutAppScreen(
                modifier = modifier,
                onNavigateToRoleSelection = {
                    navController.navigate(Routes.Login) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // 3. Login → Role Selection (after auth)
        composable(Routes.Login) {
            LoginScreen(
                modifier = modifier,
                selectedRole = "user",
                onNavigateToLoading = {
                    navController.navigate(Routes.RoleSelection) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // Optional: deep-link Login with role
        composable(
            route = Routes.LoginWithRole,
            arguments = listOf(
                navArgument("role") { type = NavType.StringType; defaultValue = "user" }
            )
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "user"
            LoginScreen(
                modifier = modifier,
                selectedRole = role,
                onNavigateToLoading = {
                    navController.navigate(Routes.RoleSelection) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // 4. Role Selection → Role-based Dashboard (role-based routing)
        composable(Routes.RoleSelection) {
            RoleSelectionScreen(
                modifier = modifier,
                onNavigateToLogin = { role ->
                    val dashboardRoute = Routes.dashboardForRole(role)
                    if (dashboardRoute != null) {
                        navController.navigate(dashboardRoute) {
                            launchSingleTop = true
                            popUpTo(Routes.RoleSelection) { inclusive = true }
                        }
                    }
                }
            )
        }

        // 5. Role-based dashboards
        composable(Routes.DashboardStudent) {
            StudentDashboardScreen(modifier = modifier)
        }
        composable(Routes.DashboardAdmin) {
            LoadingScreen(
                modifier = modifier,
                isSignUp = false,
                onNavigateToAbout = null
            )
        }
        composable(Routes.DashboardManagement) {
            LoadingScreen(
                modifier = modifier,
                isSignUp = false,
                onNavigateToAbout = null
            )
        }

        composable(Routes.Profile) {
            ProfileScreen(modifier = modifier)
        }

        composable(Routes.AcademicDetails) {
            AcademicPerdormanceScreen(modifier = modifier)
        }

        composable(Routes.Preparation) {
            PreparationScreen(modifier = modifier)
        }

        composable(Routes.Chatbot) {
            ChatbotScreen(modifier = modifier)
        }

        composable(Routes.StudentDetails) {
            StudentDetailsScreen(modifier = modifier)
        }

        composable(Routes.Opportunities) {
            OpportunitiesScreen(modifier = modifier)
        }

        composable(Routes.StudentProfileForm) {
            StudentProfileFormScreen(modifier = modifier)
        }

        composable(Routes.Loading) {
            var hasNavigated by remember { mutableStateOf(false) }
            LoadingScreen(
                modifier = modifier,
                isSignUp = false,
                onNavigateToAbout = {
                    if (!hasNavigated) {
                        hasNavigated = true
                        navController.navigate(Routes.About) {
                            popUpTo(Routes.About) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}
