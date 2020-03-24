package com.play.weather_mvrx.domain.repository

import com.play.weather_mvrx.data.response.GeoPositionSearch
import com.play.weather_mvrx.data.response.WeatherCurent
import com.play.weather_mvrx.data.response.WeatherResult
import io.reactivex.Observable

interface WeatherRepositoryHandle{

    fun getWeatherDataByGeoPositionSearch(q: String?) : Observable<GeoPositionSearch>

    fun getWeatherData5Days(keyRegion: String?,details: Boolean) : Observable<WeatherResult>

    fun getWeatherDataCurrent(keyRegion: String?,details: Boolean) : Observable<ArrayList<WeatherCurent>>

}