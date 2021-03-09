package com.geekbrains.kotlin_lessons.activity

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.utils.Constants

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val MAX_ADDRESSES = 1
    private val ZOOM = 8.0f
    private val coder: Geocoder by lazy { Geocoder(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.apply {
            val location =
                getLocationFromAddress(intent.getStringExtra(Constants.ACTOR_PLACE_OF_BIRTH))
            addMarker(
                MarkerOptions()
                    .position(location)
                    .title(intent.getStringExtra(Constants.ACTOR_NAME))
            )
            moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM))
        }
    }

    private fun getLocationFromAddress(addressStr: String?): LatLng {
        coder.getFromLocationName(addressStr, MAX_ADDRESSES).also {
            return LatLng(it[0].latitude, it[0].longitude)
        }
    }
}