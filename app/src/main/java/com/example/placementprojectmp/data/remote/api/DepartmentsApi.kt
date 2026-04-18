package com.example.placementprojectmp.data.remote.api

import com.example.placementprojectmp.data.remote.dto.DepartmentCreateRequest
import com.example.placementprojectmp.data.remote.dto.DepartmentResponse
import com.example.placementprojectmp.data.remote.dto.DepartmentUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DepartmentsApi {
    @GET("/api/departments")
    suspend fun listDepartments(): Response<List<DepartmentResponse>>

    @GET("/api/departments/{id}")
    suspend fun getDepartment(@Path("id") id: Long): Response<DepartmentResponse>

    @POST("/api/departments")
    suspend fun createDepartment(@Body body: DepartmentCreateRequest): Response<DepartmentResponse>

    @PUT("/api/departments/{id}")
    suspend fun updateDepartment(@Path("id") id: Long, @Body body: DepartmentUpdateRequest): Response<DepartmentResponse>

    @DELETE("/api/departments/{id}")
    suspend fun deleteDepartment(@Path("id") id: Long): Response<ResponseBody>
}
