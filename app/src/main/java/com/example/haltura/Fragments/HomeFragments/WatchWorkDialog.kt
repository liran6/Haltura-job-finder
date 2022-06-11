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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import com.example.haltura.R
import com.example.haltura.Sql.Items.Work
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.ViewModels.HomeViewModel
import com.example.haltura.ViewModels.WatchWorkViewModel
import com.example.haltura.databinding.FragmentHomeBinding
import com.example.haltura.databinding.WatchWorkDialogBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class WatchWorkDialog : DialogFragment{// , OnMapReadyCallback {

    private lateinit var _work: WorkSerializable
    //
    private lateinit var _mMap: GoogleMap
    //private lateinit var calendar: Calendar
    private lateinit var _image: ImageView
    private lateinit var _taskAndCompany : TextView
    private lateinit var _salary: TextView
    //private lateinit var etNumberOfWorkers: EditText
    private lateinit var _location: TextView
    private lateinit var _info: TextView
    private lateinit var _dateAndTime: TextView
    private lateinit var _registerToWork: Button
    private lateinit var rootView: View
    //
    private val _viewModel: WatchWorkViewModel by activityViewModels()
    private var _binding: WatchWorkDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var _fragmentView: View

    ///
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        _mMap = googleMap
        initMap()
        //val sydney = LatLng(-34.0, 151.0)
        //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    ///

    constructor(work: WorkSerializable):super()
    {
        this._work = work
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_edges)
        //rootView = inflater.inflate(R.layout.watch_work_dialog, container, false)
        _binding = WatchWorkDialogBinding.inflate(inflater, container, false)
        //val root: View = binding.root
        _fragmentView = binding.root

        initViews()
        //initMap()
        initButtons()

        //return rootView
        return _fragmentView
    }

    private fun initMap() {
        var addr = getAddress()
        //todo remove markers
        var point  = getLocationFromAddress(addr)
        if (point != null)
        {
            _mMap.addMarker(MarkerOptions().position(point).title(addr))
            //mMap.get
            _mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15.0f))
        }
    }

    private fun initButtons() {
        _registerToWork.setOnClickListener()
        {
            _viewModel.registerToWork(_work)
        }
    }

//    private fun initMap() {
//        try
//        {
//            var fm = getActivity()?.getSupportFragmentManager();/// getChildFragmentManager();
//            var supportMapFragment = fm?.findFragmentById(R.id.map) as SupportMapFragment
//            if (supportMapFragment == null) {
//                supportMapFragment = SupportMapFragment.newInstance()
//                if (fm != null) {
//                    fm.beginTransaction().replace(R.id.map, supportMapFragment).commit()
//                }
//            }
//            supportMapFragment.getMapAsync(this);
//        }catch (e:java.lang.Exception)
//        {
//
//        }
//    }

    private fun initViews() {
        _image = binding.itemImage
        _taskAndCompany = binding.itemTaskAndCompany
        _salary = binding.itemSalary
        _location = binding.itemLocation
        _info = binding.itemInfo
        _dateAndTime = binding.itemDateAndTime
        _registerToWork = binding.RegisterToWork
        // BUTTON?
        var taskAndCompany = _work.task
        if (_work.company != null) {taskAndCompany += "(" + _work.company+ ")"}
        _taskAndCompany.text = taskAndCompany
        _salary.text = "Salary: " + _work.salary + "₪ per hour"
        _location.text = getAddress()
        _info.text = _work.info
        _dateAndTime.text = "FROM: " +_work.startTime + " TO " + _work.startTime
        var bm = Base64.decode(_work.image, Base64.DEFAULT)
        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
        _image.setImageBitmap(data)
//        Image = rootView.findViewById<View>(R.id.itemImage) as ImageView
//        TaskAndCompany = rootView.findViewById<View>(R.id.itemTaskAndCompany) as TextView
//        Salary = rootView.findViewById<View>(R.id.itemSalary) as TextView
//        Location = rootView.findViewById<View>(R.id.itemLocation) as TextView
//        Info = rootView.findViewById<View>(R.id.itemInfo) as TextView
//        DateAndTime = rootView.findViewById<View>(R.id.itemDateAndTime) as TextView
//        registerToWork = rootView.findViewById<View>(R.id.RegisterToWork) as Button
//        //todo on click
//        //
//        var taskAndCompany = work.task
//        if (work.company != null) {taskAndCompany += "(" + work.company+ ")"}
//        TaskAndCompany.text = taskAndCompany
//        Salary.text = "Salary: " + work.salary
//        Location.text = getAddress()
//        Info.text = work.info
//        DateAndTime.text = "FROM: " +work.startTime + " TO " + work.startTime
//        var bm = Base64.decode(work.image, Base64.DEFAULT)
//        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
//        Image.setImageBitmap(data)

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


//    override fun onMapReady(googleMap: GoogleMap) {
//        _mMap = googleMap
//
////        var p1 = getLocationFromAddress("ben tzvi 40 givatayim")
////
////        if (ActivityCompat.checkSelfPermission(
////                this,
////                Manifest.permission.ACCESS_FINE_LOCATION
////            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
////                this,
////                Manifest.permission.ACCESS_COARSE_LOCATION
////            ) != PackageManager.PERMISSION_GRANTED
////        ) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////            return
////        }
////        mMap.setMyLocationEnabled(true)
//        //val p = getLocationFromAddress()
//        //todo set your location
//        var addr = getAddress()
//        var p  = getLocationFromAddress(addr)
//        if (p != null) {
//            _mMap.addMarker(MarkerOptions().position(p).title(addr))
//            _mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p, 15.0f))
//        }
//    }

    private fun getAddress(): String? {
        return _work.address.street + " " + _work.address.streetNum + ", " + _work.address.city
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