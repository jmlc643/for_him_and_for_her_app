package com.app.forhimandforher.models

import com.google.gson.annotations.SerializedName

data class BrandProduct(
    @SerializedName("quantity")
    val quantity:Int,
    @SerializedName("brand")
    val brand:Brand
)
