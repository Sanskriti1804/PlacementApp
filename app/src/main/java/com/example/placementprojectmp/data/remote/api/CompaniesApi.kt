package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.CompanyCreateRequest
import com.example.placementprojectmp.data.remote.dto.CompanyResponse
import com.example.placementprojectmp.data.remote.dto.CompanyUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CompaniesApi {
    @GET("/api/companies")
    suspend fun listCompanies(): Response<List<CompanyResponse>>

    @GET("/api/companies/{id}")
    suspend fun getCompany(@Path("id") id: Long): Response<CompanyResponse>

    @POST("/api/companies")
    suspend fun createCompany(@Body body: CompanyCreateRequest): Response<CompanyResponse>

    @PUT("/api/companies/{id}")
    suspend fun updateCompany(@Path("id") id: Long, @Body body: CompanyUpdateRequest): Response<CompanyResponse>

    @DELETE("/api/companies/{id}")
    suspend fun deleteCompany(@Path("id") id: Long): Response<ResponseBody>
}
