package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 data class Wind(

    @SerializedName("Speed")
    @Expose
    val speed: SpeedCurent = SpeedCurent(),

    @SerializedName("Direction")
    @Expose
    val direction: Direction = Direction()
)