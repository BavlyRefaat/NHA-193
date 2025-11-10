package com.depi.bookdiscovery.dto


import com.google.gson.annotations.SerializedName

data class Epub(
    @SerializedName("acsTokenLink")
    var acsTokenLink: String?,
    @SerializedName("downloadLink")
    var downloadLink: String?,
    @SerializedName("isAvailable")
    var isAvailable: Boolean?
)