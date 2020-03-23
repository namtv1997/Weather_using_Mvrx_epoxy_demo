package com.play.weather_mvrx.presentation.main

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.play.weather_mvrx.di.AssistedViewModelFactory
import com.play.weather_mvrx.di.DaggerMvRxViewModelFactory
import com.play.weather_mvrx.domain.WeatherRepository
import com.play.weather_mvrx.presentation.base.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

data class MainState(val message: Async<String> = Uninitialized) : MvRxState

class MainViewModel @AssistedInject constructor(
    @Assisted state: MainState,
    private val repo: WeatherRepository
) : BaseViewModel<MainState>(state) {

    init {
        sayHello()
    }

    fun sayHello() {
        repo.sayHello().execute { copy(message = it) }
    }

    @AssistedInject.Factory
    interface Factory :
        AssistedViewModelFactory<MainViewModel, MainState> {
        override fun create(state: MainState): MainViewModel
    }

    companion object : DaggerMvRxViewModelFactory<MainViewModel, MainState>(
        MainViewModel::class.java)
}