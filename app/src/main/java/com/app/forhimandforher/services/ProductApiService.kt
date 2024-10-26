package com.app.forhimandforher.services

import com.app.forhimandforher.models.ApiResponse
import com.app.forhimandforher.models.product.CreateProductFHAH
import com.app.forhimandforher.models.product.CreateProductRH
import com.app.forhimandforher.models.product.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApiService {
    @GET("product/list/")
    suspend fun listProducts():ApiResponse

    @GET("product/list/fhah/")
    suspend fun listProductsFHAH():ApiResponse

    @GET("product/list/rh/")
    suspend fun listProductsRH():ApiResponse

    @POST("product/create/fhah/")
    suspend fun createProductFHAH(@Body createProductFHAH: CreateProductFHAH): ApiResponse

    @POST("product/create/rh/")
    suspend fun createProductRH(@Body createProductRH: CreateProductRH): ApiResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://fhah-back.onrender.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productApiService: ProductApiService = retrofit.create(ProductApiService::class.java)
}