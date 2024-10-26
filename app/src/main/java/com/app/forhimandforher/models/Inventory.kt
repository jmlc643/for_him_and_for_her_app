package com.app.forhimandforher.models

import com.google.gson.annotations.SerializedName

data class Inventory(
    @SerializedName("quantity")
    val quantity:Int,
    @SerializedName("supplier")
    val supplier: Supplier,
    @SerializedName("size")
    val size: Size,
    @SerializedName("color")
    val color: Color
)
