package com.play.weather_mvrx.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AirAndPollen (
    @SerializedName("Name")
    @Expose
    val name: String? = null,

    @SerializedName("Value")
    @Expose
    val value: Int? = null,

    @SerializedName("Category")
    @Expose
    val category: String? = null,

    @SerializedName("CategoryValue")
    @Expose
    val categoryValue: Int? = null,

    @SerializedName("Type")
    @Expose
    val type:String?=null
 )