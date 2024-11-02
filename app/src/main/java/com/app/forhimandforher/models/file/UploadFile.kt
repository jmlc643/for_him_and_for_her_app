package com.app.forhimandforher.models.file

import com.google.gson.annotations.SerializedName

data class UploadFile(
    @SerializedName("path")
    var path: String
)
