package com.example.placementprojectmp.data.remote.dto

import kotlinx.serialization.Serializable

/** GlobalExceptionHandler JSON body: `{"error":"<reason string>"}`. */
@Serializable
data class ApiErrorEnvelope(val error: String)
