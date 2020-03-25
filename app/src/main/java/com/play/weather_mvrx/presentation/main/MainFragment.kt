package com.play.weather_mvrx.presentation.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.google.android.gms.location.*

import com.play.weather_mvrx.R
import com.play.weather_mvrx.data.response.GeoPositionSearch
import kotlinx.android.synthetic.main.fragment_main.*
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun invalidate() = withState(viewModel) { state ->

        tvCountry.text = state.geoPositionSearch()?.country?.englishName
        tvLatitue.text = state.geoPositionSearch()?.geoPosition?.latitude.toString()
        tvLongtitue.text = state.geoPositionSearch()?.geoPosition?.longitude.toString()
        tvTimeZone.text = state.geoPositionSearch()?.timeZone?.name
        tvRegion.text = state.geoPositionSearch()?.region?.englishName

//        val dateInStringDay1 = state.weatherResult()?.DailyForecasts!![1].date
//        val dateInStringDay2 = state.weatherResult()?.DailyForecasts!![2].date
//        val dateInStringDay3 = state.weatherResult()?.DailyForecasts!![3].date
//        val dateInStringDay4 = state.weatherResult()?.DailyForecasts!![4].date
//
//        val sdf5days = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        val shortdateInStringDay1 = dateInStringDay1?.substring(0, 19)
//        val shortdateInStringDay2 = dateInStringDay2?.substring(0, 19)
//        val shortdateInStringDay3 = dateInStringDay3?.substring(0, 19)
//        val shortdateInStringDay4 = dateInStringDay4?.substring(0, 19)
//
//        val sdf5days1 = SimpleDateFormat("EEE")
//        val day1 = sdf5days.parse(shortdateInStringDay1)
//        val day2 = sdf5days.parse(shortdateInStringDay2)
//        val day3 = sdf5days.parse(shortdateInStringDay3)
//        val day4 = sdf5days.parse(shortdateInStringDay4)
//
//        val resultday1 = sdf5days1.format(day1)
//        val resultday2 = sdf5days1.format(day2)
//        val resultday3 = sdf5days1.format(day3)
//        val resultday4 = sdf5days1.format(day4)
//
//        tvTueDay.text = resultday1
//        tvWednesday.text = resultday2
//        tvThurday.text = resultday3
//        tvFriDay.text = resultday4

        tvTempDay1.text = state.weatherResult()?.Headline?.category.toString()
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

        fusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
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
        viewModel.getDataGeoPositionSearch("${latitude},${longitude}")
    }

    private fun convertFahrenheitToKelvin(fahrenheit: Float): Double {
        return (5.0 / 9 * (fahrenheit - 32) + 273)
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
