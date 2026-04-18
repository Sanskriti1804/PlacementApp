package com.example.placementprojectmp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long? = null,
    val email: String? = null,
    val role: String? = null,
    val createdAt: String? = null
)

typealias UserDto = UserResponse

@Serializable
data class UserUpdateRequest(
    val email: String? = null,
    val password: String? = null,
    val role: String? = null
)

@Serializable
data class CompanyContactSupportRequest(
    val name: String,
    val email: String,
    val phone: String? = null,
    val preferredMode: String? = null
)

@Serializable
data class CompanyContactSupportResponse(
    val id: Long? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val preferredMode: String? = null
)

@Serializable
data class CompanyCreateRequest(
    val name: String,
    val tagline: String? = null,
    val location: String? = null,
    val email: String? = null,
    val websiteUrl: String? = null,
    val description: String? = null,
    val overview: String? = null,
    val sector: String? = null,
    val imageUrl: String? = null,
    val documentUrls: List<String>? = null,
    val contactSupports: List<CompanyContactSupportRequest>? = null
)

@Serializable
data class CompanyUpdateRequest(
    val name: String? = null,
    val tagline: String? = null,
    val location: String? = null,
    val email: String? = null,
    val websiteUrl: String? = null,
    val description: String? = null,
    val overview: String? = null,
    val sector: String? = null,
    val imageUrl: String? = null,
    val documentUrls: List<String>? = null,
    val contactSupports: List<CompanyContactSupportRequest>? = null
)

@Serializable
data class CompanyResponse(
    val id: Long? = null,
    val name: String? = null,
    val tagline: String? = null,
    val location: String? = null,
    val email: String? = null,
    val websiteUrl: String? = null,
    val description: String? = null,
    val overview: String? = null,
    val sector: String? = null,
    val imageUrl: String? = null,
    val documentUrls: List<String>? = null,
    val contactSupports: List<CompanyContactSupportResponse>? = null
)

typealias CompanyDto = CompanyResponse
