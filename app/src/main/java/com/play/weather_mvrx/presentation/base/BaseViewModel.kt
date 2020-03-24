package com.play.weather_mvrx.presentation.base

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxState

abstract class BaseViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(initialState, debugMode = BuildConfig.DEBUG)