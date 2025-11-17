package com.depi.bookdiscovery.dto


import com.google.gson.annotations.SerializedName

data class AccessInfo(
    @SerializedName("accessViewStatus")
    var accessViewStatus: String?,
    @SerializedName("country")
    var country: String?,
    @SerializedName("epub")
    var epub: Epub?,
    @SerializedName("pdf")
    var pdf: Pdf?
)