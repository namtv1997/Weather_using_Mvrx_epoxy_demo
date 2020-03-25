package com.play.weather_mvrx.presentation.main

import com.play.weather_mvrx.data.entity.MainState
import com.play.weather_mvrx.data.response.GeoPositionSearch
import com.play.weather_mvrx.data.response.WeatherCurent
import com.play.weather_mvrx.data.response.WeatherResult
import com.play.weather_mvrx.di.AssistedViewModelFactory
import com.play.weather_mvrx.di.DaggerMvRxViewModelFactory
import com.play.weather_mvrx.domain.repository.WeatherRepository
import com.play.weather_mvrx.presentation.base.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class MainViewModel @AssistedInject constructor(@Assisted state: MainState, private val repo: WeatherRepository) : BaseViewModel<MainState>(state) {

    fun getDataGeoPositionSearch(latitue: String) {
        repo.getDataGeoPositionSearch(latitue, ::setDataGeoPositionSearch)
    }

    private fun setDataGeoPositionSearch(geoPositionSearch: GeoPositionSearch) {
        repo.getDataGeoPositionObservable(geoPositionSearch).execute { copy(geoPositionSearch = it) }
    }

     fun getDataWeather5days(keyRegion: String) {
        repo.getDataWeather5days(keyRegion, ::setgetDataWeather5days)
    }

    private fun setgetDataWeather5days(weatherResult: WeatherResult) {
        repo.getDataWeather5daysObservable(weatherResult).execute { copy(weatherResult = it) }
    }

     fun getDataWeatherCurrent(keyRegion: String) {
        repo.getDataWeatherCurrent(keyRegion, ::setDataWeatherCurrent)
    }

    private fun setDataWeatherCurrent(listWeatherCurent: ArrayList<WeatherCurent>) {
        repo.getDataWeatherCurrentObservable(listWeatherCurent).execute { copy(listWeatherCurent = it) }
    }

    @AssistedInject.Factory
    interface Factory : AssistedViewModelFactory<MainViewModel, MainState> { override fun create(state: MainState): MainViewModel }

    companion object : DaggerMvRxViewModelFactory<MainViewModel, MainState>(MainViewModel::class.java)
}