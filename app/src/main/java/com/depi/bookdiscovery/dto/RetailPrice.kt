package com.depi.bookdiscovery.dto


import com.google.gson.annotations.SerializedName

data class RetailPrice(
    @SerializedName("amountInMicros")
    var amountInMicros: Long?,
    @SerializedName("currencyCode")
    var currencyCode: String?
)