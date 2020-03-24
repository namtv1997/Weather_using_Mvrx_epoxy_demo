package com.play.weather_mvrx.domain.repository

import com.play.weather_mvrx.data.response.GeoPositionSearch
import com.play.weather_mvrx.data.response.WeatherCurent
import com.play.weather_mvrx.data.response.WeatherResult
import com.play.weather_mvrx.domain.usercase.GetWeatherData5DaysUseCase
import com.play.weather_mvrx.domain.usercase.GetWeatherDataByGeoPositionSearchUseCase
import com.play.weather_mvrx.domain.usercase.GetWeatherDataCurrentUseCase
import io.reactivex.Observable
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val getWeatherDataByGeoPositionSearchUseCase: GetWeatherDataByGeoPositionSearchUseCase,
    private val getWeatherDataCurrentUseCase: GetWeatherDataCurrentUseCase,
    private val getWeatherData5DaysUseCase: GetWeatherData5DaysUseCase
) {


    fun getDataGeoPositionSearch(q: String): Observable<GeoPositionSearch> {
        var geoPositionSearch = GeoPositionSearch()
        getWeatherDataByGeoPositionSearchUseCase.saveLatAndLon(q)
        getWeatherDataByGeoPositionSearchUseCase.execute(
            onSuccess = {
                geoPositionSearch = it
            },
            onError = {
                it.printStackTrace()
            }

        )
        return Observable.just(geoPositionSearch)
    }

    fun getDataWeather5days(keyRegion: String): Observable<WeatherResult> {
        var weatherResult = WeatherResult()
        getWeatherData5DaysUseCase.saveKeyRegion(keyRegion)
        getWeatherData5DaysUseCase.execute(
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
        getWeatherDataCurrentUseCase.saveLatAndLon(keyRegion)
        getWeatherDataCurrentUseCase.execute(
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