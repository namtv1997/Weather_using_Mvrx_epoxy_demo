package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Past12Hours (
    @SerializedName("Metric")
    @Expose
    val metric: SpeedCurent = SpeedCurent(),
    @SerializedName("Imperial")
    @Expose
    val imperial: SpeedCurent = SpeedCurent()
)