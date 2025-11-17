package com.depi.bookdiscovery.dto


import com.google.gson.annotations.SerializedName

data class ReadingModes(
    @SerializedName("image")
    var image: Boolean?,
    @SerializedName("text")
    var text: Boolean?
)