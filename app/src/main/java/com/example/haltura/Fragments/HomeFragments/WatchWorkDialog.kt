package com.example.haltura.Fragments.HomeFragments

import android.app.Activity
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.findFragment
import com.example.haltura.R
import com.example.haltura.Sql.Items.Work
import com.example.haltura.Sql.Items.WorkSerializable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class WatchWorkDialog : DialogFragment , OnMapReadyCallback {

    private lateinit var work: WorkSerializable
    //
    private lateinit var mMap: GoogleMap
    //private lateinit var calendar: Calendar
    private lateinit var Image: ImageView
    private lateinit var TaskAndCompany : TextView
    private lateinit var Salary: TextView
    //private lateinit var etNumberOfWorkers: EditText
    private lateinit var Location: TextView
    private lateinit var Info: TextView
    private lateinit var DateAndTime: TextView
    private lateinit var registerToWork: Button
    private lateinit var rootView: View

    constructor(work: WorkSerializable):super()
    {
        this.work = work
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.watch_work_dialog, container, false)
        initViews()
        initMap()
        initButtons()
        return rootView
    }

    private fun initButtons() {
        registerToWork.setOnClickListener()
        {
            //
        }
    }

    private fun initMap() {
        try
        {
            var fm = getActivity()?.getSupportFragmentManager();/// getChildFragmentManager();
            var supportMapFragment = fm?.findFragmentById(R.id.map) as SupportMapFragment
            if (supportMapFragment == null) {
                supportMapFragment = SupportMapFragment.newInstance()
                if (fm != null) {
                    fm.beginTransaction().replace(R.id.map, supportMapFragment).commit()
                }
            }
            supportMapFragment.getMapAsync(this);
        }catch (e:java.lang.Exception)
        {

        }
    }

    private fun initViews() {
        Image = rootView.findViewById<View>(R.id.itemImage) as ImageView
        TaskAndCompany = rootView.findViewById<View>(R.id.itemTaskAndCompany) as TextView
        Salary = rootView.findViewById<View>(R.id.itemSalary) as TextView
        Location = rootView.findViewById<View>(R.id.itemLocation) as TextView
        Info = rootView.findViewById<View>(R.id.itemInfo) as TextView
        DateAndTime = rootView.findViewById<View>(R.id.itemDateAndTime) as TextView
        registerToWork = rootView.findViewById<View>(R.id.RegisterToWork) as Button
        //todo on click
        //
        var taskAndCompany = work.task
        if (work.company != null) {taskAndCompany += "(" + work.company+ ")"}
        TaskAndCompany.text = taskAndCompany
        Salary.text = "Salary: " + work.salary
        Location.text = getAddress()
        Info.text = work.info
        DateAndTime.text = "FROM: " +work.startTime + " TO " + work.startTime
        var bm = Base64.decode(work.image, Base64.DEFAULT)
        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
        Image.setImageBitmap(data)

//        var mMapFragment = object : SupportMapFragment() {
//            override fun onActivityCreated(savedInstanceState: Bundle?) {
//                super.onActivityCreated(savedInstanceState)
//                mMap = mMapFragment.getMap()
//                if (mMap != null) {
//                    setupMap()
//                }
//            }
//        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        var p1 = getLocationFromAddress("ben tzvi 40 givatayim")
//
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        mMap.setMyLocationEnabled(true)
        //val p = getLocationFromAddress()
        //todo set your location
        var addr = getAddress()
        var p  = getLocationFromAddress(addr)
        if (p != null) {
            mMap.addMarker(MarkerOptions().position(p).title(addr))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p, 15.0f))
        }
    }

    private fun getAddress(): String? {
        return work.address.street + " " + work.address.streetNum + ", " + work.address.city
    }

    fun getLocationFromAddress(strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            return LatLng(location.getLatitude(), location.getLongitude())
            //return GeoPoint(location.getLatitude(), location.getLongitude())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e : Exception)
        {
            e.printStackTrace()
        }
        return null
    }
}