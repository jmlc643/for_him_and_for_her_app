package com.app.forhimandforher.services

import com.app.forhimandforher.models.product.CreateProductFHAH
import com.app.forhimandforher.models.product.CreateProductRH
import com.app.forhimandforher.models.product.CreateProductResponse
import com.app.forhimandforher.models.product.ProductListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApiService {
    @GET("product/list/")
    suspend fun listProducts(): Response<ProductListResponse>

    @GET("product/list/fhah/")
    suspend fun listProductsFHAH(): Response<ProductListResponse>

    @GET("product/list/rh/")
    suspend fun listProductsRH(): Response<ProductListResponse>

    @POST("product/create/fhah/")
    suspend fun createProductFHAH(@Body createProductFHAH: CreateProductFHAH): Response<CreateProductResponse>

    @POST("product/create/rh/")
    suspend fun createProductRH(@Body createProductRH: CreateProductRH): Response<CreateProductResponse>
}