package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WindGustCurrent(
    @SerializedName("Speed")
    @Expose
    val speed: SpeedCurent = SpeedCurent()
)