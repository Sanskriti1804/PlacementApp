package com.example.placementprojectmp.data.remote.dto

/**
 * Known server contract gaps for Android integration (minimal backend changes only; do not assume these exist).
 *
 * 1. **AuthResponse lacks userId** — many flows need [UserResponse.id] (e.g. `POST /api/student-profiles?userId=`).
 *    Minimal fix: add `userId` (or full `user`) to AuthResponse, or `GET /api/users/by-email?email=` (authenticated).
 *
 * 2. **No filtered applications list** — only `GET /api/applications` returns all rows.
 *    Minimal fix: `studentId` query param or dedicated resource for the current student.
 */
object BackendIntegrationGaps
