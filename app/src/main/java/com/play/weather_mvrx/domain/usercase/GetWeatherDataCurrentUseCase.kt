package com.play.weather_mvrx.domain.usercase

import com.play.weather_mvrx.data.response.WeatherCurent
import com.play.weather_mvrx.domain.repository.WeatherRepositoryHandle
import com.play.weather_mvrx.domain.usercase.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetWeatherDataCurrentUseCase @Inject constructor(private val repository: WeatherRepositoryHandle) : ObservableUseCase<ArrayList<WeatherCurent>>() {

    private var keyRegion: String? = null

    fun saveLatAndLon(latitue:String) {
        keyRegion=latitue
    }

    override fun buildUseCaseSingle(): Observable<ArrayList<WeatherCurent>> {
        return repository.getWeatherDataCurrent(keyRegion,true)
    }
}