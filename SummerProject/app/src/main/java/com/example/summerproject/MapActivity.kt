package com.example.summerproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val lounge = LatLng(37.341773, 126.731874)
        val KPU = LatLng(37.340140,126.733524)
        mMap.addMarker(MarkerOptions().position(lounge).title("TIP Lounge"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lounge))

        var cameraPosition : CameraPosition =CameraPosition.Builder()
            .target(KPU)
            .zoom(16.5f)
            .bearing(38f)
            .tilt(20f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}