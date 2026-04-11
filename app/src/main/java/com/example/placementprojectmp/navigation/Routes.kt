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
        const val IntroTalent = "app_intro_talent_screen"
        const val IntroOpportunity = "app_intro_opportunity_screen"
        const val RoleSelection = "role_selection_screen"
        const val Login = "login_screen"
        const val Loading = "loading_screen"
    }

    /**
     * Student module – all student UI destinations (bottom-nav shell, stack routes, and parameters).
     */
    object StudentRoutes {
        /** Container with bottom nav; start destination of Student graph. */
        const val Main = "student_main"
        const val Applications = "applications_screen"
        /** Bottom-tab opportunities (path differs from [OpportunitiesOuter]). */
        const val Opportunities = "opportunities_screen"
        const val Dashboard = "student_dashboard_screen"
        const val Prepare = "prepare_screen"
        const val StudentProfile = "student_profile_screen"

        const val Profile = "profile"
        const val AcademicDetails = "academic"
        const val Preparation = "preparation"
        const val PyqQuestions = "pyq_questions"
        const val PyqQuestionsWithCompany = "pyq_questions/{company}"
        const val AptitudeTestDetails = "aptitude_test_details_screen"
        const val AptitudeTestDetailsWithId = "aptitude_test_details_screen/{testId}"
        const val AptitudeTestPlayer = "aptitude_test_player_screen"
        const val AptitudeTestPlayerWithId = "aptitude_test_player_screen/{testId}"
        const val AptitudeTestResult = "aptitude_test_result_screen"
        const val AptitudeTestResultWithId = "aptitude_test_result_screen/{testId}"
        const val Chatbot = "chatbot"
        /** Outer-graph opportunities route (distinct from tab [Opportunities]). */
        const val OpportunitiesOuter = "opportunities"
        const val StudentProfileForm = "dashboard/studentform"
        const val PersonalInformationFormScreen = "personal_information_screen"
        const val ApplicationScreen = "application_screen"
        const val ApplicationStatusScreen = "application_status_screen"
        const val DashboardStudent = "dashboard/student"
        /** Shared [com.example.placementprojectmp.ui.screens.shared.screens.JobDetailScreen] with [jobId] argument. */
        const val JobDetailWithJobId = "job_detail_screen/{jobId}"
        /** Shared [com.example.placementprojectmp.ui.screens.shared.screens.DriveDetailScreen] with [driveId] argument. */
        const val DriveDetailWithDriveId = "drive_detail_screen/{driveId}"

        fun jobDetailScreen(jobId: String): String = "job_detail_screen/$jobId"

        fun driveDetailScreen(driveId: String): String = "drive_detail_screen/$driveId"
    }

    /** Staff module – staff dashboards and management screens. */
    object StaffRoutes {
        const val Root = "staff_root"
        /** Bottom-nav shell; start destination of Staff graph. */
        const val Main = "staff_main"
        const val Drive = "staff_drive_screen"
        const val TeacherCompanyDetails = "teacher_company_details_screen"
        const val TeacherProfile = "teacher_profile_screen"
        const val StudentDetails = "student_details"
        /** Staff home dashboard (center bottom tab; also registered on inner [StaffMainContainer] NavHost). */
        const val StaffDashboard = "staff_dashboard_screen"
        const val DriveDetail = "staff_drive_detail/{driveId}"
        const val JobDetail = "staff_job_detail/{jobId}"
        const val CandidateDetail = "staff_candidate_detail/{sourceId}"
        const val PlacementWorkspace = "staff_placement_workspace"

        fun driveDetail(driveId: String): String = "staff_drive_detail/$driveId"
        fun jobDetail(jobId: String): String = "staff_job_detail/$jobId"
        fun candidateDetail(sourceId: String): String = "staff_candidate_detail/$sourceId"
    }

    /** System module – placeholder for error, maintenance, force update, etc. */
    object SystemRoutes {
        const val Root = "system_root"
        const val SystemContainer = "system_container"
        const val Start = "system_start_screen"
        const val SystemDashboard = "system_dashboard_screen"
        const val SystemManagement = "system_management_screen"
        const val JobManagement = "system_job_management_screen"
        const val SystemProfile = "system_profile_screen"
        const val SystemSettings = "system_settings_screen"
    }

    const val Splash = "splash"
    const val About = "about"
    const val Login = "login"
    const val LoginWithRole = "login?role={role}"
    const val RoleSelection = "role_selection"
    const val Loading = "loading"

    /** Role-based dashboard routes (non-student roles). */
    const val DashboardAdmin = "dashboard/admin"
    const val DashboardManagement = "dashboard/management"

    /** Same strings as [StudentRoutes]; kept for call sites that use `Routes.*`. */
    const val Profile = StudentRoutes.Profile
    const val AcademicDetails = StudentRoutes.AcademicDetails
    const val Preparation = StudentRoutes.Preparation
    const val Chatbot = StudentRoutes.Chatbot
//    const val StudentDetails = StudentRoutes.StudentDetails
    const val Opportunities = StudentRoutes.OpportunitiesOuter
    const val StudentProfileForm = StudentRoutes.StudentProfileForm
    const val PersonalInformationFormScreen = StudentRoutes.PersonalInformationFormScreen
    const val ApplicationScreen = StudentRoutes.ApplicationScreen
    const val ApplicationStatusScreen = StudentRoutes.ApplicationStatusScreen
    const val PyqQuestions = StudentRoutes.PyqQuestions
    const val PyqQuestionsWithCompany = StudentRoutes.PyqQuestionsWithCompany
    const val AptitudeTestDetails = StudentRoutes.AptitudeTestDetails
    const val AptitudeTestDetailsWithId = StudentRoutes.AptitudeTestDetailsWithId
    const val AptitudeTestPlayer = StudentRoutes.AptitudeTestPlayer
    const val AptitudeTestPlayerWithId = StudentRoutes.AptitudeTestPlayerWithId
    const val AptitudeTestResult = StudentRoutes.AptitudeTestResult
    const val AptitudeTestResultWithId = StudentRoutes.AptitudeTestResultWithId
    const val DashboardStudent = StudentRoutes.DashboardStudent

    /**
     * Default root [NavHost] start: use one of [GraphRoutes] only (`startup`, `student`, `staff`, `system`).
     * Do not use leaf routes like [Splash] or [StartupRoutes.Splash] here.
     * Override per build in [com.example.placementprojectmp.navigation.AppNavGraph] via `rootStartDestination` if needed.
     */
    const val StartDestination = GraphRoutes.Student

    /**
     * Returns the dashboard route for the given role, or null if unknown.
     * Used for role-based routing from RoleSelectionScreen.
     */
    fun dashboardForRole(role: String): String? = when (role.lowercase()) {
        "student" -> StudentRoutes.DashboardStudent
        "admin" -> DashboardAdmin
        "management" -> DashboardManagement
        else -> null
    }
}
