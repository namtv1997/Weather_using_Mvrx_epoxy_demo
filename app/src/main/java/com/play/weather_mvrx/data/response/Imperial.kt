package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Imperial(
    @SerializedName("Value")
    @Expose
    val value: Int? = null,
    @SerializedName("Unit")
    @Expose
    val unit: String? = null,
    @SerializedName("UnitType")
    @Expose
    val unitType: Int? = null
)