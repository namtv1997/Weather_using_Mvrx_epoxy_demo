package com.play.weather_mvrx.app

import com.play.weather_mvrx.data.source.ApiModule
import com.play.weather_mvrx.di.AssistedViewModelFactory
import com.play.weather_mvrx.presentation.base.BaseViewModel
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ApiModule::class]
)

interface AppComponent {
    fun viewModelFactories(): Map<Class<out BaseViewModel<*>>, AssistedViewModelFactory<*, *>>
}