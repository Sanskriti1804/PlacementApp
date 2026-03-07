package com.example.placementprojectmp.navigation

/**
 * Navigation routes for the app. Single source of truth for NavGraph.
 * Flow: Splash → About → Login → RoleSelection → Role-based Dashboard
 */
object Routes {
    const val Splash = "splash"
    const val About = "about"
    const val Login = "login"
    const val LoginWithRole = "login?role={role}"
    const val RoleSelection = "role_selection"
    const val Loading = "loading"

    /** Role-based dashboard routes. Add new roles here when adding modules. */
    const val DashboardStudent = "dashboard/student"
    const val DashboardAdmin = "dashboard/admin"
    const val DashboardManagement = "dashboard/management"

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
