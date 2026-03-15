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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.placementprojectmp.ui.screens.shared.screens.AboutAppScreen
import com.example.placementprojectmp.ui.screens.shared.screens.LoadingScreen
import com.example.placementprojectmp.ui.screens.shared.screens.LoginScreen
import com.example.placementprojectmp.ui.screens.shared.screens.RoleSelectionScreen
import com.example.placementprojectmp.ui.screens.shared.screens.SplashScreen
import com.example.placementprojectmp.ui.screens.student.screens.AcademicPerdormanceScreen
import com.example.placementprojectmp.ui.screens.student.screens.ApplicationScreen
import com.example.placementprojectmp.ui.screens.student.screens.ApplicationStatusScreen
import com.example.placementprojectmp.ui.screens.student.screens.AptitudeTestDetailsScreen
import com.example.placementprojectmp.ui.screens.student.screens.AptitudeTestPlayerScreen
import com.example.placementprojectmp.ui.screens.student.screens.AptitudeTestResultScreen
import com.example.placementprojectmp.ui.screens.student.screens.ChatbotScreen
import com.example.placementprojectmp.ui.screens.student.screens.OpportunitiesScreen
import com.example.placementprojectmp.ui.screens.student.screens.PreparationScreen
import com.example.placementprojectmp.ui.screens.student.screens.ProfileScreen
import com.example.placementprojectmp.ui.screens.student.screens.PyqQuestionsScreen
import com.example.placementprojectmp.ui.screens.student.screens.StudentDetailsScreen
import com.example.placementprojectmp.ui.screens.student.screens.StudentDashboardScreen
import com.example.placementprojectmp.ui.screens.student.screens.StudentMainContainer
import com.example.placementprojectmp.ui.screens.student.screens.StudentProfileFormScreen

/**
 * Root navigation host with four graphs: Startup, Student, Staff, System.
 * Start destination is StartupGraph; role-based navigation from startup sends users to Student or Staff graph.
 */
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.GraphRoutes.Startup,
        modifier = modifier
    ) {
        startupGraph(navController, modifier)
        studentGraph(navController, modifier)
        staffGraph(modifier)
        systemGraph(modifier)
    }
}

private fun androidx.navigation.NavGraphBuilder.startupGraph(
    navController: NavHostController,
    modifier: androidx.compose.ui.Modifier
) {
    navigation(
        route = Routes.GraphRoutes.Startup,
        startDestination = Routes.StartupRoutes.Splash
    ) {
        composable(Routes.StartupRoutes.Splash) {
            var hasNavigated by remember { mutableStateOf(false) }
            SplashScreen(
                modifier = modifier,
                onNavigateToAbout = {
                    if (!hasNavigated) {
                        hasNavigated = true
                        navController.navigate(Routes.StartupRoutes.About) {
                            popUpTo(Routes.StartupRoutes.Splash) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Routes.StartupRoutes.About) {
            AboutAppScreen(
                modifier = modifier,
                onNavigateToRoleSelection = {
                    navController.navigate(Routes.StartupRoutes.Login) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.StartupRoutes.Login) {
            LoginScreen(
                modifier = modifier,
                selectedRole = "user",
                onNavigateToLoading = {
                    navController.navigate(Routes.StartupRoutes.RoleSelection) {
                        launchSingleTop = true
                    }
                }
            )
        }
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
                    navController.navigate(Routes.StartupRoutes.RoleSelection) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.StartupRoutes.RoleSelection) {
            RoleSelectionScreen(
                modifier = modifier,
                onNavigateToLogin = { role ->
                    when (role.lowercase()) {
                        "student" -> navController.navigate(Routes.GraphRoutes.Student) {
                            popUpTo(Routes.GraphRoutes.Startup) { inclusive = true }
                        }
                        else -> navController.navigate(Routes.GraphRoutes.Staff) {
                            popUpTo(Routes.GraphRoutes.Startup) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Routes.StartupRoutes.Loading) {
            var hasNavigated by remember { mutableStateOf(false) }
            LoadingScreen(
                modifier = modifier,
                isSignUp = false,
                onNavigateToAbout = {
                    if (!hasNavigated) {
                        hasNavigated = true
                        navController.navigate(Routes.StartupRoutes.About) {
                            popUpTo(Routes.StartupRoutes.Loading) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}

private fun androidx.navigation.NavGraphBuilder.studentGraph(
    navController: NavHostController,
    modifier: androidx.compose.ui.Modifier
) {
    navigation(
        route = Routes.GraphRoutes.Student,
        startDestination = Routes.StudentRoutes.Main
    ) {
        composable(Routes.StudentRoutes.Main) {
            StudentMainContainer(modifier = modifier, outerNavController = navController)
        }
        composable(Routes.StudentRoutes.Dashboard) {
            StudentDashboardScreen(modifier = modifier)
        }
        composable(Routes.Profile) {
            ProfileScreen(modifier = modifier)
        }
        composable(Routes.AcademicDetails) {
            AcademicPerdormanceScreen(modifier = modifier)
        }
        composable(Routes.Preparation) {
            PreparationScreen(
                modifier = modifier,
                onNavigateToPyqQuestions = { company ->
                    navController.navigate("${Routes.PyqQuestions}/$company")
                },
                onNavigateToAptitudeTestDetails = { testId ->
                    navController.navigate("${Routes.AptitudeTestDetails}/$testId")
                }
            )
        }
        composable(
            route = Routes.AptitudeTestDetailsWithId,
            arguments = listOf(navArgument("testId") { type = NavType.StringType })
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getString("testId") ?: ""
            AptitudeTestDetailsScreen(
                modifier = modifier,
                testId = testId,
                onStartTest = {
                    navController.navigate("${Routes.AptitudeTestPlayer}/$testId")
                }
            )
        }
        composable(
            route = Routes.AptitudeTestPlayerWithId,
            arguments = listOf(navArgument("testId") { type = NavType.StringType })
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getString("testId") ?: ""
            AptitudeTestPlayerScreen(
                modifier = modifier,
                testId = testId,
                onSubmit = {
                    navController.navigate(Routes.AptitudeTestResult) {
                        popUpTo("${Routes.AptitudeTestPlayer}/$testId") { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.AptitudeTestResult) {
            AptitudeTestResultScreen(modifier = modifier, testId = null)
        }
        composable(
            route = Routes.PyqQuestionsWithCompany,
            arguments = listOf(navArgument("company") { type = NavType.StringType })
        ) { backStackEntry ->
            val company = backStackEntry.arguments?.getString("company") ?: ""
            PyqQuestionsScreen(modifier = modifier, companyName = company)
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
        composable(Routes.PersonalInformationFormScreen) {
            StudentProfileFormScreen(modifier = modifier)
        }
        composable(Routes.ApplicationScreen) {
            ApplicationScreen(modifier = modifier)
        }
        composable(Routes.ApplicationStatusScreen) {
            ApplicationStatusScreen(modifier = modifier)
        }
    }
}

private fun androidx.navigation.NavGraphBuilder.staffGraph(
    modifier: androidx.compose.ui.Modifier
) {
    navigation(
        route = Routes.GraphRoutes.Staff,
        startDestination = Routes.StaffRoutes.Root
    ) {
        composable(Routes.StaffRoutes.Root) {
            // Placeholder – no UI yet
        }
    }
}

private fun androidx.navigation.NavGraphBuilder.systemGraph(
    modifier: androidx.compose.ui.Modifier
) {
    navigation(
        route = Routes.GraphRoutes.System,
        startDestination = Routes.SystemRoutes.Root
    ) {
        composable(Routes.SystemRoutes.Root) {
            // Placeholder – for future error, maintenance, force update screens
        }
    }
}
