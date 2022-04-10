package com.example.haltura.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.haltura.R
import com.example.haltura.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.GeoPoint
//import com.google.android.gms.vision.barcode.Barcode.GeoPoint
import java.io.IOException


class AddWorkActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    //private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_work)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

//        val mapFragment = SupportMapFragment.newInstance()
//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.map, mapFragment)
//            .commit()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getLocationFromAddress("ben tzvi 40 givatayim")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.setMyLocationEnabled(true)
        //val p = getLocationFromAddress()
        var p = LatLng(32.0589923, 34.8241127)
        mMap.addMarker(MarkerOptions().position(p).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p,15.0f))
    }

    fun getLocationFromAddress(strAddress: String?): GeoPoint? {
        val coder = Geocoder(this)
        val address: List<Address>?
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            return GeoPoint(location.getLatitude(), location.getLongitude())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}