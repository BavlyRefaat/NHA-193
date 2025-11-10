package com.depi.bookdiscovery.dto


import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("finskyOfferType")
    var finskyOfferType: Int?,
    @SerializedName("listPrice")
    var listPrice: ListPriceX?,
    @SerializedName("retailPrice")
    var retailPrice: RetailPrice?
)