package com.example.placementprojectmp.ui.theme.colormap

import androidx.compose.ui.graphics.Color
import com.example.placementprojectmp.ui.theme.NeonBlue

/**
 * Central, enum-driven color entry point for placement domain UI.
 * Kept standalone and backward-compatible with existing screens.
 */
object ColorMapper {

    fun getColor(status: ApplicationStatus): Color = status.toColor()

    fun getColor(role: UserRole): Color = when (role) {
        UserRole.STUDENT -> Color(0xFF00D4FF) // Neon cyan
        UserRole.STAFF -> Color(0xFFFF4FD8) // Neon pink
        UserRole.MANAGEMENT -> Color(0xFF39FF14) // Neon lime
        UserRole.RECRUITER -> Color(0xFFFFA500) // Neon amber/orange
        UserRole.SYSTEM -> Color(0xFFB24BF3) // Neon purple
    }

    /**
     * Explicit override for placed students.
     */
    fun getPlacedStudentColor(): Color = NeonBlue

    fun getColor(status: CompanyStatus): Color = when (status) {
        CompanyStatus.ACTIVE -> Color(0xFF2E7D32)
        CompanyStatus.INACTIVE -> Color(0xFF6B7280)
        CompanyStatus.BLACKLISTED -> Color(0xFFC62828)
        CompanyStatus.VISITING_SOON -> Color(0xFF0288D1)
        CompanyStatus.PENDING_APPROVAL -> Color(0xFFF9A825)
    }

    fun getColor(status: DriveStatus): Color = when (status) {
        DriveStatus.SCHEDULED -> Color(0xFF1976D2)
        DriveStatus.REGISTRATION_OPEN -> Color(0xFF2E7D32)
        DriveStatus.REGISTRATION_CLOSED -> Color(0xFFEF6C00)
        DriveStatus.ONGOING -> Color(0xFF009688)
        DriveStatus.COMPLETED -> Color(0xFF455A64)
        DriveStatus.CANCELLED -> Color(0xFFD32F2F)
        DriveStatus.RESULT_DECLARED -> Color(0xFF6A1B9A)
    }

    fun getColor(state: TeacherAccountState): Color = when (state) {
        TeacherAccountState.ACTIVE -> Color(0xFF2E7D32)
        TeacherAccountState.INACTIVE -> Color(0xFF757575)
        TeacherAccountState.ON_LEAVE -> Color(0xFFFFA000)
    }

    fun getColor(position: FacultyPosition): Color = when (position) {
        FacultyPosition.ASSISTANT_PROFESSOR -> Color(0xFF00897B)
        FacultyPosition.ASSOCIATE_PROFESSOR -> Color(0xFF3949AB)
        FacultyPosition.PROFESSOR -> Color(0xFF5E35B1)
        FacultyPosition.HEAD_OF_DEPARTMENT -> Color(0xFF6D4C41)
        FacultyPosition.VISITING_FACULTY -> Color(0xFF546E7A)
        FacultyPosition.LAB_INSTRUCTOR -> Color(0xFF7CB342)
    }

    fun getColor(role: PlacementRole): Color = when (role) {
        PlacementRole.PLACEMENT_COORDINATOR -> Color(0xFFD58DE7)
        PlacementRole.TRAINING_AND_PLACEMENT_OFFICER -> Color(0xFF1E88E5)
        PlacementRole.DEPARTMENT_PLACEMENT_COORDINATOR -> Color(0xFF3949AB)
        PlacementRole.INTERNSHIP_COORDINATOR -> Color(0xFF00897B)
        PlacementRole.PLACEMENT_ADMIN -> Color(0xFFF4511E)
        PlacementRole.NONE -> Color(0xFF9E9E9E)
    }

    fun getColor(department: Department): Color = when (department) {
        Department.CSE -> Color(0xFF1565C0)
        Department.IT -> Color(0xFF00838F)
        Department.ECE -> Color(0xFF6A1B9A)
        Department.ME -> Color(0xFF2E7D32)
        Department.CE -> Color(0xFFEF6C00)
        Department.EE -> Color(0xFFF9A825)
        Department.MCA -> Color(0xFF5D4037)
        Department.MBA -> Color(0xFFAD1457)
    }
}

/**
 * Uses existing application-status palette values to keep current appearance unchanged.
 */
fun ApplicationStatus.toColor(): Color = when (this) {
    ApplicationStatus.APPLIED -> Color(0xFF2196F3)
    ApplicationStatus.SHORTLISTED -> Color(0xFFFF9800)
    ApplicationStatus.REJECTED -> Color(0xFFD32F2F)
    ApplicationStatus.SELECTED -> Color(0xFF2E7D32)
    ApplicationStatus.INTERVIEW_SCHEDULED -> Color(0xFF009688)
    ApplicationStatus.OFFERED -> Color(0xFF4CAF50)
}
