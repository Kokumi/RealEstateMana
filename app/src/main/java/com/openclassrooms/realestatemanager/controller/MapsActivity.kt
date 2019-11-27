package com.openclassrooms.realestatemanager.controller

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.openclassrooms.realestatemanager.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var mLocationPermissionGranted = false
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLastKnowLocation: Location? = null
    //private val mContext : Context? = null
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 14

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        updateLocationUI()
        getDeviceLocation()
        /*// Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.addMarker(MarkerOptions().po)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }

    /**
     * configure map according to location permission
     */
    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    /**
     * check if the location permission is granted
     */
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
            updateLocationUI()
        } else {
            ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    /**
     * execute when the location permission request is finish
     * @param requestCode code of the request
     * @param permissions permission requested
     * @param grantResults result
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mLocationPermissionGranted = false
        if (requestCode ==PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) mLocationPermissionGranted = true
        updateLocationUI()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    /**
     * move the map according to user location and set zoom preferences
     */
    private fun getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                val locationResult: Task<*> = mFusedLocationProviderClient!!.lastLocation
                locationResult.addOnCompleteListener {
                        if (it.isSuccessful && it.result != null) {
                            mLastKnowLocation = it.result as Location?
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(
                                    LatLng(mLastKnowLocation!!.latitude, mLastKnowLocation!!.longitude)
                            ))
                            mMap.setMinZoomPreference(16f)
                        } else {
                            if (it.exception != null) Log.e("TASKERROR", it.exception!!.message)
                            mMap.uiSettings.isMyLocationButtonEnabled = false
                        }
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        getRealEstate()
    }

    /**
     * get real estate nearby user's location and add marker
     */
    private fun getRealEstate(){
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        //step 1: get address
        //step 2: get position from address
        //step 3: add marker to position
    }
}
