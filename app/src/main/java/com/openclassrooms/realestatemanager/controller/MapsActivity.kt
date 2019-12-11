package com.openclassrooms.realestatemanager.controller

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.AppDatabase
import com.openclassrooms.realestatemanager.model.Entity.Address
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, AsyncAddress {

    private lateinit var mMap: GoogleMap
    private var mLocationPermissionGranted = false
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLastKnowLocation: Location? = null
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 14


    /**
     * get real estate's address nearby user
     */
    override fun processFinish(addressOutput: ArrayList<Address>) {
        displayMarker(addressOutput)
    }

    /**
     * activity creation function
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * configure google map
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        updateLocationUI()
        getDeviceLocation()
    }

    /**
     * configure map according to location permission
     */
    private fun updateLocationUI() {
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
        val asyncTask = AddressTask(Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build())
        asyncTask.delegate = this
        asyncTask.execute()
    }

    /**
     * get real estate's address nearby user's location and add marker
     */
    private fun displayMarker(pAddress : ArrayList<Address>){

        for(address in pAddress){
            var geoResult : List<android.location.Address>? = null

            try{
                geoResult = Geocoder(this).getFromLocationName("${address.address},${address.city},${address.country}"
                        ,1)
            }catch (e : IOException){
                e.printStackTrace()
            }

            if(geoResult != null && geoResult.isNotEmpty()){
                val latLng = LatLng(geoResult[0].latitude,geoResult[0].longitude)

                mMap.addMarker(MarkerOptions().position(latLng).title(address.address))
            }
        }
    }

    /**
     * task to get real estate's address
     */
    class AddressTask( private val pDatabase: AppDatabase) : AsyncTask<Void, Void, ArrayList<Address>>(){
        var delegate : AsyncAddress? = null

        override fun onPostExecute(result: ArrayList<Address>) {
            delegate!!.processFinish(addressOutput = result)
            //pHolder.addressDisplay(result)
            super.onPostExecute(result)
            this.cancel(true)
        }

        override fun doInBackground(vararg p0: Void?): ArrayList<Address> {
            return pDatabase.realEstateDao().getAllAddress() as ArrayList<Address>
        }
    }
}
interface AsyncAddress{
    fun processFinish(addressOutput : ArrayList<Address>)
}
