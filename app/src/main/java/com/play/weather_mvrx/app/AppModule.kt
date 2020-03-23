package com.play.weather_mvrx.app

import com.play.weather_mvrx.di.AssistedViewModelFactory
import com.play.weather_mvrx.di.ViewModelKey
import com.play.weather_mvrx.presentation.main.MainViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes = [AssistedInject_AppModule::class])
interface AppModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModelFactory(factory: MainViewModel.Factory): AssistedViewModelFactory<*, *>
}