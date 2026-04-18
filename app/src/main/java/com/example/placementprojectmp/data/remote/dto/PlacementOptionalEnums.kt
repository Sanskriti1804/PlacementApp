package com.example.placementprojectmp.data.remote.dto

import kotlinx.serialization.Serializable

/** Mirrored from backend; not referenced by other DTOs in this client yet. */
@Serializable
enum class EducationLevel {
    TENTH,
    TWELFTH,
    GRADUATION,
    POST_GRADUATION,
    OTHER
}

@Serializable
enum class RoundCompletionStatus {
    PENDING,
    COMPLETED,
    SKIPPED,
    CANCELLED
}
