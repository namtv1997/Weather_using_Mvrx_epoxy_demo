package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Past24HourRange (
    @SerializedName("Minimum")
    @Expose
    val minimum: MinimumCurrent = MinimumCurrent(),

    @SerializedName("Maximum")
    @Expose
    val maximum:MaximumCurrent=MaximumCurrent()
)