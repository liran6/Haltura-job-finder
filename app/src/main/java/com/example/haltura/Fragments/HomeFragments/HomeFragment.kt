package com.example.haltura.Fragments.HomeFragments

//import com.example.haltura.Dialogs.WatchWorkDialog
//location


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.WorkAdapter
import com.example.haltura.Fragments.*
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.HorizontalSpaceItemDecoration
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.HomeViewModel
import com.example.haltura.activities.WorkHistoryActivity
import com.example.haltura.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : BaseFragment(R.layout.fragment_home), BackButton ,ProfileSettingsButton,AdvanceSearchButton {

    override val titleRes: String = "Welcome Back "+UserData.currentUser?.username

    private val _viewModel: HomeViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    //All
    private lateinit var _allWorksRecycle: RecyclerView
    private lateinit var _allWorksAdapter: WorkAdapter
    //Close
    private lateinit var _closeWorksRecycle: RecyclerView
    private lateinit var _closeWorksAdapter: WorkAdapter
    //Recommended
    private lateinit var _recommendedWorksRecycle: RecyclerView
    private lateinit var _recommendedWorksAdapter: WorkAdapter
    private var _binding: FragmentHomeBinding? = null
    //EditText
    private lateinit var _searchText : EditText
    //Button
    private lateinit var _searchButton : ImageButton


    //location
    private lateinit var locationManager: LocationManager
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private var latitudeLabel: Double? = null
    private var longitudeLabel: Double? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initBinding()
        initViews()
        initButtons()
        initTextListener()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()
        initLocation()
        //requestPermissions()
        //getLocationInfo()
        //getLastLocation()
        return _fragmentView

    }

        private fun showSnackbar(
        mainTextStringId: String, actionStringId: String,
        listener: View.OnClickListener
    ) {
        Toast.makeText(activity!!, mainTextStringId, Toast.LENGTH_LONG).show()
    }
    private fun initLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)

    }
    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            activity!!.applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }
    private fun getLastLocation() {
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(activity!!) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result
                latitudeLabel = (lastLocation)!!.latitude
                longitudeLabel = (lastLocation)!!.longitude
                var x = 1
            }
            else {
                Log.w(TAG, "getLastLocation:exception", task.exception)
            }
        }
    }
    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }
    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                View.OnClickListener {
                    startLocationPermissionRequest()
                })
        }
        else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {
                    showSnackbar("Permission was denied", "Settings",
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }

    private fun initButtons() {
        _searchButton.setOnClickListener {
        }
    }

    private fun initTextListener() {
        _searchText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filter()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun filter() {
        _viewModel.filter(_searchText.text.toString())//
    }

    private fun initBinding() {
        _fragmentView = binding.root
    }

    private fun initViews() {
        //_allWorksRecycle = binding.allWorksRecyclerView
        _searchText = binding.searchText
        _searchButton = binding.searchButton
    }

    private fun initViewModelData() {
        _viewModel.getAllWorks()
        _viewModel.getCloseWorks()
    }

    private fun initObservers() {
        // All Works
        _viewModel.mutableWorkList.observe(
            viewLifecycleOwner,
            Observer { workList ->
                workList?.let {
                    updateAllWorksRecyclersAndAdapters()
                }
            }
        )
        // Close Works
        _viewModel.mutableCloseWorksList.observe(
            viewLifecycleOwner,
            Observer { workList ->
                workList?.let {
                    updateCloseWorksRecyclersAndAdapters()
                }
            }
        )
        // Recommended Works
        _viewModel.mutableRecommendedWorksList.observe(
            viewLifecycleOwner,
            Observer { workList ->
                workList?.let {
                    updateRecommendedWorksRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateAllWorksRecyclersAndAdapters() {
        _allWorksAdapter.setData(_viewModel.mutableWorkList.value!!)
        _allWorksAdapter.notifyDataSetChanged()
    }

    private fun updateCloseWorksRecyclersAndAdapters() {
        _closeWorksAdapter.setData(_viewModel.mutableCloseWorksList.value!!)
        _closeWorksAdapter.notifyDataSetChanged()
    }

    private fun updateRecommendedWorksRecyclersAndAdapters() {
        _closeWorksAdapter.setData(_viewModel.mutableRecommendedWorksList.value!!)
        _closeWorksAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        initCloseWorks()
        initRecommendedWorks()
        initAllWorks()
    }

    private fun initCloseWorks() {
        // Close Works
        _closeWorksRecycle = binding.closeWorksRecyclerView
        val workList = _viewModel.mutableCloseWorksList.value!!
        val layoutManager = LinearLayoutManager(getContext(),
            LinearLayoutManager.HORIZONTAL,false)
        _closeWorksRecycle.addItemDecoration(HorizontalSpaceItemDecoration(20))
        _closeWorksRecycle.layoutManager = layoutManager
        _closeWorksAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showCloseWorkDetails(it) })
        _closeWorksRecycle.adapter = _closeWorksAdapter
    }

    private fun showCloseWorkDetails(work: WorkSerializable) {
        var dialog = WatchCloseWorkDialog(work)
        activity?.supportFragmentManager?.let {
            dialog.show(it, "WatchWorkDialog")
        }
    }

    private fun initRecommendedWorks() {
        // Recommended Works
        _recommendedWorksRecycle = binding.recommendedWorksRecyclerView
        val workList = _viewModel.mutableRecommendedWorksList.value!!
        val layoutManager = LinearLayoutManager(getContext(),
            LinearLayoutManager.HORIZONTAL,false)
        _recommendedWorksRecycle.addItemDecoration(HorizontalSpaceItemDecoration(20))
        _recommendedWorksRecycle.layoutManager = layoutManager
        _recommendedWorksAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkDetails(it) })
        _recommendedWorksRecycle.adapter = _recommendedWorksAdapter
    }

    private fun initAllWorks() {
        // All Works
        _allWorksRecycle = binding.allWorksRecyclerView
        val workList = _viewModel.mutableWorkList.value!!
        val layoutManager = LinearLayoutManager(getContext(),
            LinearLayoutManager.HORIZONTAL,false)
        _allWorksRecycle.addItemDecoration(HorizontalSpaceItemDecoration(20))
        _allWorksRecycle.layoutManager = layoutManager
        _allWorksAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkDetails(it) })
        _allWorksRecycle.adapter = _allWorksAdapter
    }

    private fun showWorkDetails(work: WorkSerializable) {
        var dialog = WatchWorkDialog(work)
        activity?.supportFragmentManager?.let {
            dialog.show(it, "WatchWorkDialog")
        }
    }


    //

    private fun showDataRangePicker() {

        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()

        dateRangePicker.show(
            activity!!.supportFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->
            val startDateLong = dateSelected.first
            val endDateLong = dateSelected.second
            val startDateString = convertLongToTime(startDateLong)
            val endDateString = convertLongToTime(endDateLong)

        }

    }


    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault())
        return format.format(date)
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("dd.MM.yyyy")
        return df.parse(date).time
    }
    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        }
        else {
            getLastLocation()
        }

        homeActivityToolbar.makeVisible()
        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.calendar_toolbar_color))
        requireActivity().window.statusBarColor =
            requireContext().getColorCompat(R.color.calendar_statusbar_color)
    }

    override fun onStop() {
        super.onStop()
        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.colorPrimary))
        requireActivity().window.statusBarColor = requireContext().getColorCompat(R.color.colorPrimaryDark)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        private val locationPermissionCode = 2
    }

}

