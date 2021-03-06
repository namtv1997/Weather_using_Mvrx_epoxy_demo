package com.play.weather_mvrx.data.responseample.weather.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Metric(
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