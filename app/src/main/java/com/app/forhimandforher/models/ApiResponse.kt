package com.app.forhimandforher.models

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)
