package com.app.forhimandforher.models.product.create

import com.app.forhimandforher.models.product.Product

data class CreateProductResponse(
    var code: String,
    var message: String,
    var data: Product
)
