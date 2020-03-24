package com.play.weather_mvrx.domain.usercase

import com.play.weather_mvrx.data.response.GeoPositionSearch
import com.play.weather_mvrx.domain.repository.WeatherRepositoryHandle
import com.play.weather_mvrx.domain.usercase.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetWeatherDataByGeoPositionSearchUseCase @Inject constructor(private val repository: WeatherRepositoryHandle) : ObservableUseCase<GeoPositionSearch>() {

    private var q: String? = null

    fun saveLatAndLon(latitue:String) {
        q=latitue
    }

    override fun buildUseCaseSingle(): Observable<GeoPositionSearch> {
        return repository.getWeatherDataByGeoPositionSearch(q)
    }
}