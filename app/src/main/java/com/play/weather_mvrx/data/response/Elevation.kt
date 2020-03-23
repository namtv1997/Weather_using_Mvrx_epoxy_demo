package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.play.weather_mvrx.data.responseample.weather.data.response.Metric

data class Elevation(
   @SerializedName("Metric")
    @Expose
    val metric: Metric? = null,
   @SerializedName("Imperial")
    @Expose
    val imperial: Imperial? = null
)