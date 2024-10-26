package com.app.forhimandforher.models.product

data class CreateProductRH(
    val code: String,
    val name: String,
    val quantity: Int,
    val purchasePrice: Double,
    val salesPrice: Double,
    val brandName: String
)
