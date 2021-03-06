package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WindGust (
    @SerializedName("Speed")
    @Expose
    val speed: SpeedDouble = SpeedDouble(),

    @SerializedName("Direction")
    @Expose
    val direction: Direction = Direction()
)