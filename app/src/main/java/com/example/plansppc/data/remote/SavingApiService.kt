package com.example.plansppc.data.remote

import com.example.plansppc.data.remote.dto.PaymentDTO
import com.example.plansppc.data.remote.dto.PlanDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SavingApiService {
    @GET("api/plans")
    suspend fun getPlans(): List<PlanDTO>

    @GET("api/plans/{id}")
    suspend fun getPlanById(@Path("id") id: String): PlanDTO

    @POST("api/plans")
    suspend fun createPlan(@Body plan: PlanDTO): PlanDTO

    @POST("api/payments")
    suspend fun registerPayment(@Body request: PaymentDTO): PaymentDTO
}