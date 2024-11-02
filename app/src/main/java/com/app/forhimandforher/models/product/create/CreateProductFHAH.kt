package com.app.forhimandforher.models.product.create

data class CreateProductFHAH(
    val code: String,
    val barcode: String,
    val photo: String,
    val purchasePrice: Double,
    val name: String,
    val model: String,
    val salesPrice: Double,
    val quantity: Int,
    val supplierName: String,
    val size: String,
    val color: String
)
