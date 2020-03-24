package com.play.weather_mvrx.domain.usercase

import com.play.weather_mvrx.data.response.WeatherResult
import com.play.weather_mvrx.domain.repository.WeatherRepositoryHandle
import com.play.weather_mvrx.domain.usercase.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetWeatherData5DaysUseCase @Inject constructor(private val repository: WeatherRepositoryHandle) : ObservableUseCase<WeatherResult>() {

    private var keyRegion: String? = null

    fun saveKeyRegion(regionKey: String) {
        keyRegion = regionKey
    }

    override fun buildUseCaseSingle(): Observable<WeatherResult> {
        return repository.getWeatherData5Days(keyRegion,true)
    }
}