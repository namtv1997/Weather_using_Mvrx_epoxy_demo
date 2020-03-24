package com.play.weather_mvrx.domain.usercase.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class UseCase {

    protected var lastDisposable: Disposable? = null
    protected val compositeDisposable = CompositeDisposable()

    fun disposeLast() {
        lastDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}