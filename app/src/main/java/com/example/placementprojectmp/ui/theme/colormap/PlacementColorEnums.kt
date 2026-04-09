package com.example.placementprojectmp.ui.theme.colormap

enum class ApplicationStatus {
    APPLIED,
    SHORTLISTED,
    REJECTED,
    SELECTED,
    INTERVIEW_SCHEDULED,
    OFFERED
}

enum class UserRole {
    STUDENT,
    STAFF,
    MANAGEMENT,
    RECRUITER,
    SYSTEM
}

enum class CompanyStatus {
    ACTIVE,
    INACTIVE,
    BLACKLISTED,
    VISITING_SOON,
    PENDING_APPROVAL
}

enum class DriveStatus {
    SCHEDULED,
    REGISTRATION_OPEN,
    REGISTRATION_CLOSED,
    ONGOING,
    COMPLETED,
    CANCELLED,
    RESULT_DECLARED
}

enum class TeacherAccountState {
    ACTIVE,
    INACTIVE,
    ON_LEAVE
}

enum class FacultyPosition {
    ASSISTANT_PROFESSOR,
    ASSOCIATE_PROFESSOR,
    PROFESSOR,
    HEAD_OF_DEPARTMENT,
    VISITING_FACULTY,
    LAB_INSTRUCTOR
}

enum class PlacementRole {
    PLACEMENT_COORDINATOR,
    TRAINING_AND_PLACEMENT_OFFICER,
    DEPARTMENT_PLACEMENT_COORDINATOR,
    INTERNSHIP_COORDINATOR,
    PLACEMENT_ADMIN,
    NONE
}

enum class Department {
    CSE,
    IT,
    ECE,
    ME,
    CE,
    EE,
    MCA,
    MBA
}
