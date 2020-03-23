package com.play.weather_mvrx.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.play.weather_mvrx.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .replace(
                        R.id.frameContent,
                        MainFragment()
                )
                .commit()
    }
}
