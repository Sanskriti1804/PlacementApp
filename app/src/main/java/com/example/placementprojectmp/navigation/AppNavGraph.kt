package com.example.placementprojectmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.java.KoinJavaComponent
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.placementprojectmp.auth.TokenStore
import com.example.placementprojectmp.ui.screens.startup.screens.AboutAppScreen
import com.example.placementprojectmp.ui.screens.startup.screens.LoadingScreen
import com.example.placementprojectmp.ui.screens.startup.screens.LoginScreen
import com.example.placementprojectmp.ui.screens.startup.screens.RoleSelectionScreen
import com.example.placementprojectmp.ui.screens.startup.screens.SplashScreen
import com.example.placementprojectmp.ui.screens.student.screens.AcademicPerdormanceScreen
import com.example.placementprojectmp.ui.screens.student.screens.ApplicationScreen
import com.example.placementprojectmp.ui.screens.student.screens.ApplicationStatusScreen
import com.example.placementprojectmp.ui.screens.student.screens.AptitudeTestDetailsScreen
import com.example.placementprojectmp.ui.screens.student.screens.AptitudeTestPlayerScreen
import com.example.placementprojectmp.ui.screens.student.screens.AptitudeTestResultScreen
import com.example.placementprojectmp.ui.screens.shared.screens.ChatbotScreen
import com.example.placementprojectmp.ui.screens.student.screens.OpportunitiesScreen
import com.example.placementprojectmp.ui.screens.student.screens.PreparationScreen
import com.example.placementprojectmp.ui.screens.student.screens.ProfileScreen
import com.example.placementprojectmp.ui.screens.student.screens.PyqQuestionsScreen
import com.example.placementprojectmp.ui.screens.staff.screens.StudentDetailsScreen
import com.example.placementprojectmp.ui.screens.student.screens.StudentDashboardScreen
import com.example.placementprojectmp.ui.screens.student.screens.StudentMainContainer
import com.example.placementprojectmp.ui.screens.shared.screens.DriveDetailScreen
import com.example.placementprojectmp.ui.screens.shared.screens.JobDetailScreen
import com.example.placementprojectmp.ui.screens.student.screens.StudentProfileFormScreen
import com.example.placementprojectmp.ui.screens.staff.screens.StaffDashboardScreen
import com.example.placementprojectmp.ui.screens.staff.screens.StaffDriveScreen
import com.example.placementprojectmp.ui.screens.staff.screens.StaffCandidateDetailScreen
import com.example.placementprojectmp.ui.screens.staff.screens.StaffDriveDetailScreen
import com.example.placementprojectmp.ui.screens.staff.screens.StaffJobDetailScreen
import com.example.placementprojectmp.ui.screens.staff.screens.StaffMainContainer
import com.example.placementprojectmp.ui.screens.staff.screens.PlacementWorkspaceScreen
import com.example.placementprojectmp.ui.screens.shared.screens.TeacherCompanyDetailsScreen
import com.example.placementprojectmp.ui.screens.staff.screens.TeacherProfileScreen
import com.example.placementprojectmp.ui.screens.system.screens.JobManagementScreen
import com.example.placementprojectmp.ui.screens.system.screens.StartScreen
import com.example.placementprojectmp.ui.screens.system.screens.SystemContainerScreen
import com.example.placementprojectmp.ui.screens.system.screens.SystemDashboardScreen
import com.example.placementprojectmp.ui.screens.system.screens.SystemManagementScreen
import com.example.placementprojectmp.ui.screens.system.screens.SystemProfileScreen
import com.example.placementprojectmp.ui.screens.system.screens.SystemSettingsScreen

/**
 * Root navigation host with four graphs: Startup, Student, Staff, System.
 *
 * [rootStartDestination] must be exactly one of [Routes.GraphRoutes] (`startup`, `student`, `staff`, `system`).
 * It is keyed so the [NavHostController] is recreated when you change the root graph, avoiding a restored
 * back stack that still references another graph (e.g. `startup`) and triggers “not a direct child of this NavGraph”.
 */
@Composable
fun AppNavGraph(
    rootStartDestination: String = Routes.StartDestination,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    val tokenStore: TokenStore = KoinJavaComponent.get(TokenStore::class.java)
    val token by tokenStore.tokenFlow.collectAsState(initial = null)
    val role by tokenStore.roleFlow.collectAsState(initial = null)
    // TESTING: staff graph as root start — delete next line and uncomment block below when done.
    val safeRootStartDestination = Routes.GraphRoutes.System




    /*
    val safeRootStartDestination = when {
        token.isNullOrBlank() -> Routes.GraphRoutes.Startup
        role.equals("STUDENT", ignoreCase = true) -> Routes.GraphRoutes.Student
        else -> Routes.GraphRoutes.Staff
    }
    */

    key(safeRootStartDestination) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = safeRootStartDestination,
            modifier = modifier
        ) {
            startupGraph(navController, modifier)
            studentGraph(navController, modifier)
            staffGraph(modifier)
            systemGraph(modifier)
        }
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
                    navController.navigate(Routes.StartupRoutes.RoleSelection) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.StartupRoutes.Login) {
            LoginScreen(
                modifier = modifier,
                selectedRole = "user",
                onNavigateToLoading = { authRole ->
                    val graphRoute = if (authRole.name == "STUDENT") {
                        Routes.GraphRoutes.Student
                    } else {
                        Routes.GraphRoutes.Staff
                    }
                    navController.navigate(graphRoute) {
                        launchSingleTop = true
                        popUpTo(Routes.StartupRoutes.Login) { inclusive = true }
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
                onNavigateToLoading = { authRole ->
                    val graphRoute = if (authRole.name == "STUDENT") {
                        Routes.GraphRoutes.Student
                    } else {
                        Routes.GraphRoutes.Staff
                    }
                    navController.navigate(graphRoute) {
                        launchSingleTop = true
                        popUpTo(Routes.StartupRoutes.Login) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.StartupRoutes.RoleSelection) {
            RoleSelectionScreen(
                modifier = modifier,
                onNavigateToLogin = { role ->
                    navController.navigate("login?role=$role") {
                        launchSingleTop = true
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
        composable(Routes.StudentRoutes.Profile) {
            ProfileScreen(
                modifier = modifier,
                onNavigateToAcademic = { navController.navigate(Routes.StudentRoutes.AcademicDetails) },
                onNavigateToApplication = { navController.navigate(Routes.StudentRoutes.ApplicationScreen) }
            )
        }
        composable(Routes.StudentRoutes.AcademicDetails) {
            AcademicPerdormanceScreen(modifier = modifier)
        }
        composable(Routes.StudentRoutes.Preparation) {
            PreparationScreen(
                modifier = modifier,
                onNavigateToPyqQuestions = { company ->
                    navController.navigate("${Routes.StudentRoutes.PyqQuestions}/$company")
                },
                onNavigateToAptitudeTestDetails = { testId ->
                    navController.navigate("${Routes.StudentRoutes.AptitudeTestDetails}/$testId")
                }
            )
        }
        composable(
            route = Routes.StudentRoutes.AptitudeTestDetailsWithId,
            arguments = listOf(navArgument("testId") { type = NavType.StringType })
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getString("testId") ?: ""
            AptitudeTestDetailsScreen(
                modifier = modifier,
                testId = testId,
                onStartTest = {
                    navController.navigate("${Routes.StudentRoutes.AptitudeTestPlayer}/$testId")
                }
            )
        }
        composable(
            route = Routes.StudentRoutes.AptitudeTestPlayerWithId,
            arguments = listOf(navArgument("testId") { type = NavType.StringType })
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getString("testId") ?: ""
            AptitudeTestPlayerScreen(
                modifier = modifier,
                testId = testId,
                onSubmit = {
                    navController.navigate(Routes.StudentRoutes.AptitudeTestResult) {
                        popUpTo("${Routes.StudentRoutes.AptitudeTestPlayer}/$testId") { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.StudentRoutes.AptitudeTestResult) {
            AptitudeTestResultScreen(modifier = modifier, testId = null)
        }
        composable(
            route = Routes.StudentRoutes.PyqQuestionsWithCompany,
            arguments = listOf(navArgument("company") { type = NavType.StringType })
        ) { backStackEntry ->
            val company = backStackEntry.arguments?.getString("company") ?: ""
            PyqQuestionsScreen(modifier = modifier, companyName = company)
        }
        composable(Routes.StudentRoutes.Chatbot) {
            ChatbotScreen(modifier = modifier)
        }
        composable(Routes.StudentRoutes.OpportunitiesOuter) {
            OpportunitiesScreen(
                modifier = modifier,
                onJobClick = { jobId ->
                    navController.navigate(Routes.StudentRoutes.jobDetailScreen(jobId))
                },
                onDriveClick = { driveId ->
                    navController.navigate(Routes.StudentRoutes.driveDetailScreen(driveId))
                }
            )
        }
        composable(
            route = Routes.StudentRoutes.JobDetailWithJobId,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId").orEmpty()
            JobDetailScreen(modifier = modifier, jobId = jobId)
        }
        composable(
            route = Routes.StudentRoutes.DriveDetailWithDriveId,
            arguments = listOf(navArgument("driveId") { type = NavType.StringType })
        ) { backStackEntry ->
            val driveId = backStackEntry.arguments?.getString("driveId").orEmpty()
            DriveDetailScreen(modifier = modifier, driveId = driveId)
        }
        composable(Routes.StudentRoutes.StudentProfileForm) {
            StudentProfileFormScreen(modifier = modifier)
        }
        composable(Routes.StudentRoutes.PersonalInformationFormScreen) {
            StudentProfileFormScreen(modifier = modifier)
        }
        composable(Routes.StudentRoutes.ApplicationScreen) {
            ApplicationScreen(modifier = modifier)
        }
        composable(Routes.StudentRoutes.ApplicationStatusScreen) {
            ApplicationStatusScreen(modifier = modifier)
        }
    }
}

private fun androidx.navigation.NavGraphBuilder.staffGraph(
    modifier: androidx.compose.ui.Modifier
) {
    navigation(
        route = Routes.GraphRoutes.Staff,
        startDestination = Routes.StaffRoutes.Main
    ) {
        composable(Routes.StaffRoutes.Main) {
            StaffMainContainer(modifier = modifier)
        }
        composable(Routes.StaffRoutes.StaffDashboard) {
            StaffDashboardScreen(modifier = modifier)
        }
        composable(Routes.StaffRoutes.Drive) {
            StaffDriveScreen(modifier = modifier)
        }
        composable(
            route = Routes.StaffRoutes.DriveDetail,
            arguments = listOf(navArgument("driveId") { type = NavType.StringType })
        ) { backStackEntry ->
            StaffDriveDetailScreen(modifier = modifier, driveId = backStackEntry.arguments?.getString("driveId").orEmpty())
        }
        composable(
            route = Routes.StaffRoutes.JobDetail,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            StaffJobDetailScreen(modifier = modifier, jobId = backStackEntry.arguments?.getString("jobId").orEmpty())
        }
        composable(
            route = Routes.StaffRoutes.CandidateDetail,
            arguments = listOf(navArgument("sourceId") { type = NavType.StringType })
        ) { backStackEntry ->
            StaffCandidateDetailScreen(modifier = modifier, sourceId = backStackEntry.arguments?.getString("sourceId").orEmpty())
        }
        composable(Routes.StaffRoutes.PlacementWorkspace) {
            PlacementWorkspaceScreen(modifier = modifier)
        }
        composable(Routes.StaffRoutes.TeacherCompanyDetails) {
            TeacherCompanyDetailsScreen(modifier = modifier)
        }
        composable(Routes.StaffRoutes.TeacherProfile) {
            TeacherProfileScreen(modifier = modifier)
        }
        composable(Routes.StaffRoutes.StudentDetails) {
            StudentDetailsScreen(modifier = modifier)
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
            SystemContainerScreen(modifier = modifier)
        }
        composable(Routes.SystemRoutes.SystemContainer) {
            SystemContainerScreen(modifier = modifier)
        }
        composable(Routes.SystemRoutes.SystemDashboard) {
            SystemDashboardScreen(modifier = modifier)
        }
        composable(Routes.SystemRoutes.SystemManagement) {
            SystemManagementScreen(modifier = modifier)
        }
        composable(Routes.SystemRoutes.Start) {
            StartScreen(modifier = modifier)
        }
        composable(Routes.SystemRoutes.SystemProfile) {
            SystemProfileScreen(modifier = modifier)
        }
        composable(Routes.SystemRoutes.SystemSettings) {
            SystemSettingsScreen(modifier = modifier)
        }
        composable(Routes.SystemRoutes.JobManagement) {
            JobManagementScreen(modifier = modifier)
        }
    }
}
