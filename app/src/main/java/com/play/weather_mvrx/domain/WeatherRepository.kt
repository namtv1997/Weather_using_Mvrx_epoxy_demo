package com.play.weather_mvrx.domain

import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepository @Inject constructor() {

    fun sayHello(): Observable<String> {
        return Observable
            .just("Hello, world!")
            .delay(2, TimeUnit.SECONDS)
    }

}