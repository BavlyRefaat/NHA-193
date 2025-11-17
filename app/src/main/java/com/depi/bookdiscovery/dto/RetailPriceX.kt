package com.depi.bookdiscovery.dto


import com.google.gson.annotations.SerializedName

data class RetailPriceX(
    @SerializedName("amount")
    var amount: Double?,
    @SerializedName("currencyCode")
    var currencyCode: String?
)