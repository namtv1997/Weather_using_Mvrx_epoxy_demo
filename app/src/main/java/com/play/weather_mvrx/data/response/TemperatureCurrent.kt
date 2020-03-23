package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TemperatureCurrent (
    @SerializedName("Metric")
    @Expose
    val metric: SpeedDouble = SpeedDouble(),
    @SerializedName("Imperial")
    @Expose
    val imperial: SpeedDouble = SpeedDouble()
)