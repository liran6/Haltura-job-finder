package com.example.haltura.Fragments.HomeFragments

import android.graphics.BitmapFactory
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
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.DateTime
import com.example.haltura.ViewModels.WatchWorkViewModel
import com.example.haltura.databinding.WatchWorkDialogBinding
import com.example.haltura.databinding.WatchWorkHistoryEmployeeDialogBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class WatchWorkHistoryEmployeeDialog : DialogFragment {

    private lateinit var _work: WorkSerializable
    private lateinit var _mMap: GoogleMap
    private lateinit var _image: ImageView
    private lateinit var _taskAndCompany : TextView
    private lateinit var _salary: TextView
    private lateinit var _location: TextView
    private lateinit var _info: TextView
    private lateinit var _dateAndTime: TextView
    private lateinit var _report: ImageView
    private val _viewModel: WatchWorkViewModel by activityViewModels()
    private var _binding: WatchWorkHistoryEmployeeDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var _fragmentView: View

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
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    constructor(work: WorkSerializable):super()
    {
        this._work = work
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_edges)
        _binding = WatchWorkHistoryEmployeeDialogBinding.inflate(inflater, container, false)
        _fragmentView = binding.root
        initViews()
        initButtons()
        return _fragmentView
    }

    private fun initMap() {
        var p = LatLng(_work!!.address.latitude, _work!!.address.longitude)
        _mMap.addMarker(MarkerOptions().position(p).title(_work!!.address.address))
        _mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p,15.0f))
    }

    private fun initButtons() {
        _report.setOnClickListener()
        {
            openReportForm()//work?userid? (when you send it toast and exit dialog)
            //_viewModel. //.registerToWork(_work)
        }
    }

    private fun openReportForm() {
        //TODO when you send it toast and exit dialog
        //move to
    }

    private fun initViews() {
        _image = binding.itemImage
        _taskAndCompany = binding.itemTaskAndCompany
        _salary = binding.itemSalary
        _location = binding.itemLocation
        _info = binding.itemInfo
        _dateAndTime = binding.itemDateAndTime
        _report = binding.reportButton
        // BUTTON?
        var taskAndCompany = _work.task
        if (_work.company != null) {taskAndCompany += "(" + _work.company+ ")"}
        _taskAndCompany.text = taskAndCompany
        _salary.text = "Salary: " + _work.salary + "₪ per hour"
        _location.text = _work.address.address
        _info.text = _work.info
        _dateAndTime.text = DateTime.getDate(_work.startTime) + " From " +
                DateTime.getTime(_work.startTime) + " To " +
                DateTime.getTime(_work.endTime)
        var bm = Base64.decode(_work.image, Base64.DEFAULT)
        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
        _image.setImageBitmap(data)
    }
}