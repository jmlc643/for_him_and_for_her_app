package com.app.forhimandforher.models.product

data class CreateProductFHAH(
    val code: String,
    val purchasePrice: Double,
    val name: String,
    val model: String,
    val salesPrice: Double,
    val quantity: Int,
    val supplierName: String,
    val size: String,
    val color: String
)
