package com.play.weather_mvrx.domain.repository

import com.play.weather_mvrx.data.response.GeoPositionSearch
import com.play.weather_mvrx.data.response.WeatherCurent
import com.play.weather_mvrx.data.response.WeatherResult
import com.play.weather_mvrx.domain.usercase.GetWeatherData5Days
import com.play.weather_mvrx.domain.usercase.GetWeatherDataByGeoPositionSearchUseCase
import com.play.weather_mvrx.domain.usercase.GetWeatherDataCurrent
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val getWeatherDataByGeoPositionSearchUseCase: GetWeatherDataByGeoPositionSearchUseCase,
    private val getWeatherDataCurrent: GetWeatherDataCurrent,
    private val getWeatherData5Days: GetWeatherData5Days ) {


    fun getDataGeoPositionSearch(q: String): Observable<GeoPositionSearch> {
        var geoPositionSearch = GeoPositionSearch()
        getWeatherDataByGeoPositionSearchUseCase.saveLatAndLon(q)
        getWeatherDataByGeoPositionSearchUseCase.execute(
            onSuccess = {
                geoPositionSearch = it
//                getDataWeather5days(it.key.toString())
//                getDataWeatherCurrent(it.key.toString())
            },
            onError = {
                it.printStackTrace()
            }
        )
        return Observable.just(geoPositionSearch)
    }

    fun getDataWeather5days(keyRegion: String): Observable<WeatherResult> {
        var weatherResult = WeatherResult()
        getWeatherData5Days.saveKeyRegion(keyRegion)
        getWeatherData5Days.execute(
            onSuccess = {
                weatherResult = it
            },
            onError = {
                it.printStackTrace()
            }
        )
        return Observable.just(weatherResult)
    }

    fun getDataWeatherCurrent(keyRegion: String): Observable<ArrayList<WeatherCurent>> {
        var listWeatherCurent = ArrayList<WeatherCurent>()
        getWeatherDataCurrent.saveLatAndLon(keyRegion)
        getWeatherDataCurrent.execute(
            onSuccess = {
                listWeatherCurent = it
            },
            onError = {
                it.printStackTrace()
            }
        )

        return Observable.just(listWeatherCurent)
    }

}