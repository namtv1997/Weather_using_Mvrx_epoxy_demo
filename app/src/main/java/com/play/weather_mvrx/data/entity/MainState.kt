package com.play.weather_mvrx.data.entity

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.play.weather_mvrx.data.response.GeoPositionSearch
import com.play.weather_mvrx.data.response.WeatherCurent
import com.play.weather_mvrx.data.response.WeatherResult

data class MainState(
    val geoPositionSearch: Async<GeoPositionSearch> = Uninitialized,
    val weatherResult: Async<WeatherResult> = Uninitialized,
    val listWeatherCurent: Async<ArrayList<WeatherCurent>> = Uninitialized
) : MvRxState