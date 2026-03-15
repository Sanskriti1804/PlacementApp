package com.example.placementprojectmp.navigation

/**
 * Navigation routes for the app. Single source of truth for NavGraph.
 * Flow: Splash → About → Login → RoleSelection → Role-based Dashboard
 *
 * Modular navigation uses GraphRoutes and per-module route groups (Startup, Student, Staff, System).
 */
object Routes {

    /** Root-level graph route names for modular navigation. */
    object GraphRoutes {
        const val Startup = "startup"
        const val Student = "student"
        const val Staff = "staff"
        const val System = "system"
    }

    /** Startup (shared) module – initial app screens. */
    object StartupRoutes {
        const val Splash = "splash_screen"
        const val About = "about_app_screen"
        const val RoleSelection = "role_selection_screen"
        const val Login = "login_screen"
        const val Loading = "loading_screen"
    }

    /** Student module – student dashboard and student-only screens. */
    object StudentRoutes {
        /** Container with bottom nav; start destination of Student graph. */
        const val Main = "student_main"
        const val Applications = "applications_screen"
        const val Opportunities = "opportunities_screen"
        const val Dashboard = "student_dashboard_screen"
        const val Prepare = "prepare_screen"
        const val StudentProfile = "student_profile_screen"
    }

    /** Staff module – placeholder for future staff screens. */
    object StaffRoutes {
        const val Root = "staff_root"
    }

    /** System module – placeholder for error, maintenance, force update, etc. */
    object SystemRoutes {
        const val Root = "system_root"
    }

    const val Splash = "splash"
    const val About = "about"
    const val Login = "login"
    const val LoginWithRole = "login?role={role}"
    const val RoleSelection = "role_selection"
    const val Loading = "loading"
    const val Profile = "profile"
    const val AcademicDetails = "academic"
    const val Preparation =  "preparation"
    const val Chatbot = "chatbot"
    const val StudentDetails = "student_details"
    const val Opportunities = "opportunities"

    /** Role-based dashboard routes. Add new roles here when adding modules. */
    const val DashboardStudent = "dashboard/student"
    const val DashboardAdmin = "dashboard/admin"
    const val DashboardManagement = "dashboard/management"
    const val StudentProfileForm = "dashboard/studentform"
    const val ApplicationScreen = "application_screen"
    const val ApplicationStatusScreen = "application_status_screen"
    const val PyqQuestions = "pyq_questions"
    const val PyqQuestionsWithCompany = "pyq_questions/{company}"
    const val AptitudeTestDetails = "aptitude_test_details_screen"
    const val AptitudeTestDetailsWithId = "aptitude_test_details_screen/{testId}"
    const val AptitudeTestPlayer = "aptitude_test_player_screen"
    const val AptitudeTestPlayerWithId = "aptitude_test_player_screen/{testId}"
    const val AptitudeTestResult = "aptitude_test_result_screen"
    const val AptitudeTestResultWithId = "aptitude_test_result_screen/{testId}"

    /**
     * Start destination when the app launches.
     * Change this to any route above to open that screen first (e.g. Profile, DashboardStudent).
     */
    const val StartDestination = Splash

    /**
     * Returns the dashboard route for the given role, or null if unknown.
     * Used for role-based routing from RoleSelectionScreen.
     */
    fun dashboardForRole(role: String): String? = when (role.lowercase()) {
        "student" -> DashboardStudent
        "admin" -> DashboardAdmin
        "management" -> DashboardManagement
        else -> null
    }
}
