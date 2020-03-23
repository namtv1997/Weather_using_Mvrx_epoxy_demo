package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TotalLiquid (
    @SerializedName("Value")
    @Expose
    val value: Double? = null,

    @SerializedName("Unit")
    @Expose
    val unit: String? = null,

    @SerializedName("UnitType")
    @Expose
    val unitType: Int? = null
)