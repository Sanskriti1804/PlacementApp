package com.example.placementprojectmp.navigation

/**
 * Navigation routes for the app. Single source of truth for NavGraph.
 * Flow: Splash → About → Login → RoleSelection → Role-based Dashboard
 *
 * START DESTINATION: Change the line below to launch any screen when the app opens.
 * Options: Splash, About, Login, RoleSelection, Loading, Profile,
 *          DashboardStudent, DashboardAdmin, DashboardManagement
 */
object Routes {
    const val Splash = "splash"
    const val About = "about"
    const val Login = "login"
    const val LoginWithRole = "login?role={role}"
    const val RoleSelection = "role_selection"
    const val Loading = "loading"
    const val Profile = "profile"

    /** Role-based dashboard routes. Add new roles here when adding modules. */
    const val DashboardStudent = "dashboard/student"
    const val DashboardAdmin = "dashboard/admin"
    const val DashboardManagement = "dashboard/management"

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
