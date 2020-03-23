package com.play.weather_mvrx.app

import android.app.Application
import androidx.fragment.app.FragmentActivity

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

fun FragmentActivity.appComponent(): AppComponent {
    return (application as App).appComponent
}