package com.example.woof.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WoofModel(
    @SerializedName("fileSizeBytes")
    @Expose
    val size: Int,
    @SerializedName("url")
    @Expose
    val url: String
)