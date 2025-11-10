package com.depi.bookdiscovery.dto


import com.google.gson.annotations.SerializedName

data class SaleInfo(
    @SerializedName("buyLink")
    var buyLink: String?,
    @SerializedName("country")
    var country: String?,
    @SerializedName("listPrice")
    var listPrice: ListPrice?,
    @SerializedName("offers")
    var offers: List<Offer>?,
    @SerializedName("retailPrice")
    var retailPrice: RetailPriceX?
)