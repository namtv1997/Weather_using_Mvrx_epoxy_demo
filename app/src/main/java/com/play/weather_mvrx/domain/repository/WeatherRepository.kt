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

    fun getDataGeoPositionSearch(latitue: String ,geoPositionSearch : (GeoPositionSearch) -> Unit){
        getWeatherDataByGeoPositionSearchUseCase.saveLatAndLon(latitue)
        getWeatherDataByGeoPositionSearchUseCase.execute(
            onSuccess = {
                geoPositionSearch.invoke(it)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun getDataGeoPositionObservable(geoPositionSearch: GeoPositionSearch): Observable<GeoPositionSearch> {
        return Observable.just(geoPositionSearch)
    }

    fun getDataWeather5days(keyRegion: String ,weatherResult : (WeatherResult) -> Unit) {
        getWeatherData5DaysUseCase.saveKeyRegion(keyRegion)
        getWeatherData5DaysUseCase.execute(
            onSuccess = {
                weatherResult.invoke(it)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun getDataWeather5daysObservable(geoPositionSearch: WeatherResult): Observable<WeatherResult> {
        return Observable.just(geoPositionSearch)
    }

    fun getDataWeatherCurrent(keyRegion: String ,listWeatherCurent :(ArrayList<WeatherCurent>) ->Unit) {
        getWeatherDataCurrentUseCase.saveLatAndLon(keyRegion)
        getWeatherDataCurrentUseCase.execute(
            onSuccess = {
                listWeatherCurent.invoke(it)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun getDataWeatherCurrentObservable(listWeatherCurent: ArrayList<WeatherCurent>): Observable<ArrayList<WeatherCurent>> {
        return Observable.just(listWeatherCurent)
    }
}