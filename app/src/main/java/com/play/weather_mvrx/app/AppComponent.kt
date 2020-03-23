package com.play.weather_mvrx.app

import com.play.weather_mvrx.di.AssistedViewModelFactory
import com.play.weather_mvrx.presentation.base.BaseViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun viewModelFactories(): Map<Class<out BaseViewModel<*>>, AssistedViewModelFactory<*, *>>
}