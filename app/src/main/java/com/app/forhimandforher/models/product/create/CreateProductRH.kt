package com.app.forhimandforher.models.product.create

data class CreateProductRH(
    val code: String,
    val barcode: String,
    val photo: String,
    val name: String,
    val quantity: Int,
    val purchasePrice: Double,
    val salesPrice: Double,
    val brandName: String
)
