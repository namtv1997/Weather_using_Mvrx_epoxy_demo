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
import kotlinx.android.synthetic.main.fragment_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainFragment : BaseMvRxFragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLastLocation: Location

    private var latitude: String? = null
    private var longitude: String? = null
    private var locationManager: LocationManager? = null

    val viewModel: MainViewModel by fragmentViewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermission()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun invalidate() = withState(viewModel) { state ->

        messageTextView.text=state.geoPositionSearch()?.englishName
    }

    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(activity!!, *ACCESS_FINE_LOCATION)) {
            // Have permissions, do the thing
            mLocationRequest = LocationRequest()
            locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
       viewModel.getDataGeoPositionSearch("${latitude},${longitude}")
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
