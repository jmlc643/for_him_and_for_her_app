package com.app.forhimandforher.models.product

import com.app.forhimandforher.models.BrandProduct
import com.app.forhimandforher.models.Inventory
import com.google.gson.annotations.SerializedName

data class Product (
    @SerializedName("code")
    var code:String,
    @SerializedName("barcode")
    var barcode: String,
    @SerializedName("name")
    var name:String,
    @SerializedName("model")
    var model:String,
    @SerializedName("purchasePrice")
    var purchasePrice:Double,
    @SerializedName("salesPrice")
    var salesPrice:Double,
    @SerializedName("photo")
    var photo: String,
    @SerializedName("inventories")
    var inventories:List<Inventory>,
    @SerializedName("brands")
    var brands:List<BrandProduct>
)