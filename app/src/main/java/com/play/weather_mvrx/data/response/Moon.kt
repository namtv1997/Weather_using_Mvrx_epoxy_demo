package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Moon(
    @SerializedName("Rise")
    @Expose
    val rise: String? = null,

    @SerializedName("EpochRise")
    @Expose
    val epochRise: Int? = null,

    @SerializedName("Set")
    @Expose
    val set: String? = null,

    @SerializedName("EpochSet")
    @Expose
    val epochSet: Int? = null,

    @SerializedName("Phase")
    @Expose
    val phase: String? = null,

    @SerializedName("Age")
    @Expose
    val age: Int? = null
)