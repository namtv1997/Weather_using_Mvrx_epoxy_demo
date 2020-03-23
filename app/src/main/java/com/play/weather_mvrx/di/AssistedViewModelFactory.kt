package com.play.weather_mvrx.di

import com.airbnb.mvrx.MvRxState
import com.play.weather_mvrx.presentation.base.BaseViewModel

interface AssistedViewModelFactory<VM : BaseViewModel<S>, S : MvRxState> {
    fun create(state: S): VM
}