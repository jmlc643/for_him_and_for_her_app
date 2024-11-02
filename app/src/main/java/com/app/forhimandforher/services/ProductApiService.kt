package com.app.forhimandforher.services

import com.app.forhimandforher.models.product.DetailsProductResponse
import com.app.forhimandforher.models.product.create.CreateProductFHAH
import com.app.forhimandforher.models.product.create.CreateProductRH
import com.app.forhimandforher.models.product.create.CreateProductResponse
import com.app.forhimandforher.models.product.filter.FilterProductsFHAH
import com.app.forhimandforher.models.product.ProductListResponse
import com.app.forhimandforher.models.product.filter.FilterProductsRH
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @POST("product/filter/fhah/")
    suspend fun filterProductsFHAH(@Body filterProductFHAH: FilterProductsFHAH): Response<DetailsProductResponse>

    @POST("product/filter/rh/")
    suspend fun filterProductsRH(@Body filterProductsRH: FilterProductsRH): Response<DetailsProductResponse>

    @GET("product/search/{product}")
    suspend fun searchProducts(@Path("product") product: String): Response<DetailsProductResponse>
}