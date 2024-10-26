package com.app.forhimandforher.models.product

data class ProductListResponse(
    val code: String,
    val message: String,
    val data: ArrayList<Product>
)
