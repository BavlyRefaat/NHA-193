package com.depi.bookdiscovery.dto


import com.google.gson.annotations.SerializedName

data class PanelizationSummary(
    @SerializedName("containsEpubBubbles")
    var containsEpubBubbles: Boolean?,
    @SerializedName("containsImageBubbles")
    var containsImageBubbles: Boolean?
)