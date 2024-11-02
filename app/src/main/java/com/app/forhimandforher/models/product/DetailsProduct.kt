package com.app.forhimandforher.models.product

import com.app.forhimandforher.models.BrandProduct
import com.app.forhimandforher.models.Inventory
import com.google.gson.annotations.SerializedName

data class DetailsProduct(
    @SerializedName("code")
    var code: String,
    @SerializedName("barcode")
    var barcode: String,
    @SerializedName("photo")
    var photo: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("model")
    var model: String,
    @SerializedName("purchasePrice")
    var purchasePrice: Double,
    @SerializedName("salesPrice")
    var salesPrice: Double,
    @SerializedName("inventory")
    var inventory: Inventory,
    @SerializedName("brand")
    var brand: BrandProduct
)
