package com.play.weather_mvrx.data.repository

import com.play.weather_mvrx.data.response.GeoPositionSearch
import com.play.weather_mvrx.data.response.WeatherCurent
import com.play.weather_mvrx.data.response.WeatherResult
import com.play.weather_mvrx.data.source.ApiService
import com.play.weather_mvrx.domain.repository.WeatherRepositoryHandle
import com.play.weather_mvrx.utils.Constant
import io.reactivex.Observable

class WeatherRepositoryHandleRepositoryImp(private  val apiService: ApiService) : WeatherRepositoryHandle {

    override fun getWeatherDataByGeoPositionSearch(q: String?): Observable<GeoPositionSearch> {
        return apiService.getWeatherDataByGeoPositionSearch(apiKey, q.toString())
    }

    override fun getWeatherData5Days(keyRegion: String?, details: Boolean): Observable<WeatherResult> {
        return apiService.getWeatherData5Days(keyRegion.toString(), apiKey,true)
    }

    override fun getWeatherDataCurrent(keyRegion: String?, details: Boolean): Observable<ArrayList<WeatherCurent>> {
        return apiService.getWeatherDataCurrent(keyRegion.toString(), apiKey,true)
    }

    companion object{
        var apiKey = Constant.API_Key17
    }
}