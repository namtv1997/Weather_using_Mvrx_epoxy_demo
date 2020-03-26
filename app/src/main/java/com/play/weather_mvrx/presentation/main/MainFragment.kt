package com.play.weather_mvrx.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.google.android.gms.location.*

import com.play.weather_mvrx.R
import com.play.weather_mvrx.data.response.GeoPositionSearch
import com.play.weather_mvrx.data.response.WeatherCurent
import com.play.weather_mvrx.data.response.WeatherResult
import kotlinx.android.synthetic.main.fragment_main.*
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class MainFragment : BaseMvRxFragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLastLocation: Location

    private var latitude: String? = null
    private var longitude: String? = null
    private var locationManager: LocationManager? = null

    private val viewModel: MainViewModel by fragmentViewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermission()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun invalidate() = withState(viewModel) { state ->

        try {
            getDataPositionSearch(state.geoPositionSearch()!!)
            getDataWeatherCurrent(state.listWeatherCurent()!!)
            getDataWeather5day(state.weatherResult()!!)
        } catch (e: Exception) { }

    }

    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(activity!!, *ACCESS_FINE_LOCATION)) {
            // Have permissions, do the thing
            mLocationRequest = LocationRequest()
            locationManager =
                activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            //Check gps is enable or not

            if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Write Function To enable gps
                onGPS()
            } else {
                //GPS is already On then
                startLocationUpdates()
            }

        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.access_fine_location),
                RC_ACCESS_FINE_LOCATION,
                *ACCESS_FINE_LOCATION
            )
        }
    }

    private fun onGPS() {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(getString(R.string.enable_gps)).setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }

        val alertDialog = builder.create()

        alertDialog.show()
    }

    private fun startLocationUpdates() {
        // Create the location request to start receiving updates
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = INTERVAL
        mLocationRequest.fastestInterval = FASTEST_INTERVAL

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(activity!!)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // do work here
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    private fun onLocationChanged(location: Location) {
        // New location has now been determined
        mLastLocation = location
        latitude = mLastLocation.latitude.toString()
        longitude = mLastLocation.longitude.toString()
        viewModel.getDataGeoPositionSearch("$latitude, $longitude")
    }


    private fun getDataPositionSearch(geoPositionSearch: GeoPositionSearch) {
        viewModel.getDataWeather5days(geoPositionSearch.key!!)
        viewModel.getDataWeatherCurrent(geoPositionSearch.key)
        tvCountry.text = geoPositionSearch.country?.englishName
        tvLatitue.text = geoPositionSearch.geoPosition?.latitude.toString()
        tvLongtitue.text = geoPositionSearch.geoPosition?.longitude.toString()
        tvTimeZone.text = geoPositionSearch.timeZone?.name
        tvRegion.text = geoPositionSearch.region?.englishName
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun getDataWeatherCurrent(listWeatherCurent: ArrayList<WeatherCurent>) {
        tvDegree.text = "${listWeatherCurent[0].temperature.metric.value.toString()} º"
        tvHumidity.text = "${listWeatherCurent[0].relativeHumidity.toString()} %"
        tvWindSpeed.text = "${listWeatherCurent[0].wind.speed.metric.value} km/h"

        val sdf5days = SimpleDateFormat("yyyy-MM-dd")
        val sdfCustom = SimpleDateFormat("yyyy EEEE MMMM-dd")
        val arr = listWeatherCurent[0].localObservationDateTime.toString().split(":")
        val arrResult = arr[0].split("T")
        val result = sdfCustom.format(sdf5days.parse(arrResult[0]))
        val splitString = result.split(" ")
        tvLabelYear.text = splitString[0]
        tvDay.text = splitString[1]
        tvDate.text = splitString[2]

    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun getDataWeather5day(weatherResult: WeatherResult) {
        val sdf5days = SimpleDateFormat("yyyy-MM-dd")
        val sdfCustom = SimpleDateFormat("EEE")
        val arr = weatherResult.DailyForecasts?.get(1)?.date.toString().split(":")
        val arr1 = weatherResult.DailyForecasts?.get(2)?.date.toString().split(":")
        val arr2 = weatherResult.DailyForecasts?.get(3)?.date.toString().split(":")
        val arr3 = weatherResult.DailyForecasts?.get(4)?.date.toString().split(":")
        val arrResult = arr[0].split("T")
        val arrResult1 = arr1[0].split("T")
        val arrResult2 = arr2[0].split("T")
        val arrResult3 = arr3[0].split("T")
        val result = sdfCustom.format(sdf5days.parse(arrResult[0]))
        val result1 = sdfCustom.format(sdf5days.parse(arrResult1[0]))
        val result2 = sdfCustom.format(sdf5days.parse(arrResult2[0]))
        val result3 = sdfCustom.format(sdf5days.parse(arrResult3[0]))
        tvTueDay.text = result.toString()
        tvWednesday.text = result1.toString()
        tvThurday.text = result2.toString()
        tvFriDay.text = result3.toString()

        val resultTemp = weatherResult.DailyForecasts?.get(1)?.temperature?.minimum?.value?.minus(1)?.times(5)?.div(9)?.roundToInt()
        val resultTemp1 = weatherResult.DailyForecasts?.get(2)?.temperature?.minimum?.value?.minus(1)?.times(5)?.div(9)?.roundToInt()
        val resultTemp2 = weatherResult.DailyForecasts?.get(3)?.temperature?.minimum?.value?.minus(1)?.times(5)?.div(9)?.roundToInt()
        val resultTemp3 = weatherResult.DailyForecasts?.get(4)?.temperature?.minimum?.value?.minus(1)?.times(5)?.div(9)?.roundToInt()
        tvTempDay1.text = "${resultTemp}ºC"
        tvTempDay2.text = "${resultTemp1}ºC"
        tvTempDay3.text = "${resultTemp2}ºC"
        tvTempDay4.text = "${resultTemp3}ºC"

    }

    companion object {
        val ACCESS_FINE_LOCATION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        const val RC_ACCESS_FINE_LOCATION = 4466
        const val INTERVAL: Long = 50000
        const val FASTEST_INTERVAL: Long = 11000

    }
}
