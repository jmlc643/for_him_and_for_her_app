package com.app.forhimandforher.models.product

data class DetailsProductResponse(
    var code: String,
    var message: String,
    var data: ArrayList<DetailsProduct>
)
