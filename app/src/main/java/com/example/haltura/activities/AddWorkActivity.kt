package com.example.haltura.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.haltura.R
import com.example.haltura.Sql.BusinessOpenHelper
import com.example.haltura.Sql.Items.Work
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.UserData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import kotlinx.android.synthetic.main.manage_work_item.*
import java.io.IOException
import java.text.SimpleDateFormat


//iv_addItem,Imageed_Company,ed_Task,dp_Date,tp_StartTime,tp_EndTime,et_Salary,et_NumberOfWorkers,et_Address,btn_ShowLocation,map,et_Info
class AddWorkActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var autocompleteSupportFragment : AutocompleteSupportFragment
    private lateinit var edAddress: EditText
    //private lateinit var calendar: Calendar
    private lateinit var ivAddItemImage: ImageView
    private lateinit var etCompany: EditText
    private lateinit var etTask: EditText
    private lateinit var etSalary: EditText
    private lateinit var etNumberOfWorkers: EditText
    private lateinit var etAddress: EditText
    private lateinit var etInfo: EditText
    //private lateinit var map : FrameLayout
    private lateinit var dpDate: DatePicker
    private lateinit var tpStartTime: TimePicker
    private lateinit var tpEndTime: TimePicker
    private lateinit var btnShowLocation: Button
    private lateinit var btnAddWork: Button
    private lateinit var btnPreview: Button
    //private lateinit var binding: ActivityMapsBinding


    //
    private lateinit var city: String
    private lateinit var cities: Array<String>
    private lateinit var spinnerCity: Spinner
    private lateinit var etApartment: EditText
    private lateinit var etStreetName: EditText
    private lateinit var etStreetNumber: EditText
    private lateinit var etFloor: EditText
    //


    private lateinit var tvDate :TextView
    private lateinit var tvStartTime:TextView// : tv_StartTime
    private lateinit var tvEndTime:TextView// : tv_EndTime
    private lateinit var mDateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var mTimeSetListener: TimePickerDialog.OnTimeSetListener
    private lateinit var _layout: LinearLayout
    var bm: Bitmap? = null
    private var isStartTime = true
    //private lateinit var background :Drawable

    var helper = BusinessOpenHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        initViews()
        initTimePickers()
        initMap()
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun initViews(){
        edAddress = findViewById<View>(R.id.et_Address) as EditText
        tvDate = findViewById<View>(R.id.tv_date) as TextView
        tvStartTime = findViewById<View>(R.id.tv_StartTime) as TextView
        tvEndTime = findViewById<View>(R.id.tv_EndTime) as TextView
        _layout = findViewById<View>(R.id.addWorkLayout) as LinearLayout
        ivAddItemImage = findViewById<View>(R.id.iv_AddItemImage) as ImageView
        etCompany = findViewById<View>(R.id.et_Company) as EditText
        etTask = findViewById<View>(R.id.et_Task) as EditText
        etSalary = findViewById<View>(R.id.et_Salary) as EditText
        etNumberOfWorkers = findViewById<View>(R.id.et_NumberOfWorkers) as EditText
        etAddress = findViewById<View>(R.id.et_Address) as EditText
        etInfo = findViewById<View>(R.id.et_Info) as EditText
        btnShowLocation = findViewById<View>(R.id.btn_ShowLocation) as Button
        btnAddWork = findViewById<View>(R.id.btn_AddWork) as Button
        btnPreview = findViewById<View>(R.id.btn_Preview) as Button


        ///


        cities = resources.getStringArray(R.array.cities)
        etStreetName = findViewById<View>(R.id.et_StreetName) as EditText
        etStreetNumber = findViewById<View>(R.id.et_StreetNumber) as EditText
        etFloor = findViewById<View>(R.id.et_Floor) as EditText
        etApartment = findViewById<View>(R.id.et_Apartment) as EditText
        spinnerCity = findViewById<View>(R.id.spinner_City) as Spinner
        spinnerCity!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                city = cities[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun initTimePickers(){
        mDateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var month = month
            month = month + 1
            Log.d("AddWorkActivity", "onDateSet: mm/dd/yyy: $month/$day/$year")
            val date = "Date: $day/$month/$year"
            tvDate!!.text = date
        }

        mTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            Log.d("AddWorkActivity", "onTimeSet: hh:mm: $hourOfDay:$minute")
            val time = "Time: $hourOfDay:$minute"
            //todo: if the time is 18:05 it will be 18:5 (need to add zero)
            if (isStartTime)
            {
                tvStartTime!!.text = "Starting " + time
            }
            else
            {
                tvEndTime!!.text = "Ending " +time
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        var p1 = getLocationFromAddress("ben tzvi 50 givatayim")
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
        var p = LatLng(32.0589923, 34.8241127)
        mMap.addMarker(MarkerOptions().position(p).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p,15.0f))
    }

    fun getLocationFromAddress(strAddress: String?): LatLng? {
        val coder = Geocoder(this)
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

    fun showOnMap(view: View)
    {
        var addr = edAddress.text.toString()
        //todo remove markers
        var point  = getLocationFromAddress(addr)
        if (point != null)
        {
            mMap.addMarker(MarkerOptions().position(point).title(addr))
            //mMap.get
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15.0f))
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun PickDate(view: View)
    {
        //todo: set current time or what we put before (the string)
        // check if == date else split '/' and pass args
        var cal: Calendar = Calendar.getInstance()
        var year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        var day: Int = cal.get(Calendar.DAY_OF_MONTH)
        var date = tvDate.text.toString()
        if (date != "Date")
        {
            var arr = date.split('/')
            day = Integer.parseInt(arr[0].split(' ')[1])
            month = Integer.parseInt(arr[1]) - 1
            year = Integer.parseInt(arr[2])
        }
        val dialog = DatePickerDialog(
            this,
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            mDateSetListener,
            year, month, day
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun PickTime(view: View)
    {
        var cal: Calendar = Calendar.getInstance()
        var hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        var minute: Int = cal.get(Calendar.MINUTE)
        var time = ""
        if (view.getId() == R.id.tv_StartTime)
        {
            isStartTime = true;
            time = tvStartTime.text.toString()
        }
        else
        {
            isStartTime = false;
            time = tvEndTime.text.toString()
        }
        if (!(time == "Start Time" || time == "End Time"))
        {
            var arr = time.split(':')
            hour = Integer.parseInt(arr[1].split(' ')[1])
            minute = Integer.parseInt(arr[2])
        }

        val dialog = TimePickerDialog(
            this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mTimeSetListener,hour,minute,false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addWork(view: View)
    {
        //todo: check validation...
        var arrDate = tvDate.text.toString().split('/')
        var arrStrtingTime = tvStartTime.text.toString().split(':')
        var arrEndingTime = tvEndTime.text.toString().split(':')
        var date = com.example.haltura.Sql.Items.Date(Integer.parseInt(arrDate[0].split(' ')[1]),
            Integer.parseInt(arrDate[1]),
            Integer.parseInt(arrDate[2]))
        var staringTime = com.example.haltura.Sql.Items.Time(Integer.parseInt(arrStrtingTime[1].split(' ')[1]),Integer.parseInt(arrStrtingTime[2]))
        var endingTime = com.example.haltura.Sql.Items.Time(Integer.parseInt(arrEndingTime[1].split(' ')[1]),Integer.parseInt(arrEndingTime[2]))
//        var work = Work(
//            ivAddItemImage.drawable.toBitmap(),
//            etCompany.text.toString(),
//            etTask.text.toString(),
//            Integer.parseInt(etSalary.text.toString()),
//            Integer.parseInt(etNumberOfWorkers.text.toString()),
//            etAddress.text.toString(),
//            etInfo.text.toString(),
//            date,
//            staringTime,
//            endingTime
//        )
        var work = getWorkFromForm()
        //var srt = work.toString()
        var a = 1
        //helper.AddWork(work)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getWorkFromForm()
    {
        var arrDate = tvDate.text.toString().split('/')
        var arrStrtingTime = tvStartTime.text.toString().split(':')
        var arrEndingTime = tvEndTime.text.toString().split(':')
        var date = com.example.haltura.Sql.Items.Date(Integer.parseInt(arrDate[0].split(' ')[1]),
            Integer.parseInt(arrDate[1]),
            Integer.parseInt(arrDate[2]))
        var staringTime = com.example.haltura.Sql.Items.Time(Integer.parseInt(arrStrtingTime[1].split(' ')[1]),Integer.parseInt(arrStrtingTime[2]))
        var endingTime = com.example.haltura.Sql.Items.Time(Integer.parseInt(arrEndingTime[1].split(' ')[1]),Integer.parseInt(arrEndingTime[2]))

        var work = WorkSerializable(UserData.currentUser?.userId!!,
            etCompany.text.toString(),
            etTask.text.toString(),
            Integer.parseInt(etSalary.text.toString()),
            Integer.parseInt(etNumberOfWorkers.text.toString()),
            null!!,//todo:change
            etInfo.text.toString(),
            getTime(date,staringTime,endingTime,true),
            getTime(date,staringTime,endingTime,false),
            ivAddItemImage.drawable.toBitmap().toString())
//        ivAddItemImage.drawable.toBitmap(),
//        etCompany.text.toString(),
//        etTask.text.toString(),
//        Integer.parseInt(etSalary.text.toString()),
//        Integer.parseInt(etNumberOfWorkers.text.toString()),
//        etAddress.text.toString(),
//        etInfo.text.toString(),
//        date,
//        staringTime,
//        endingTime
//        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getTime(date: com.example.haltura.Sql.Items.Date,
                        startTime: com.example.haltura.Sql.Items.Time,
                        endTime: com.example.haltura.Sql.Items.Time,
                        isStart:Boolean
    ): String {
        //"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        var dt = date.toString() // Start date
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        c.time = sdf.parse(dt)
        if (isStart)
        {
            dt = sdf.format(c.time) // dt is now the new date
            return "$dt$startTime:00.000+00:00"
        }
        else
        {
            if(endTime.isBefore(startTime))
            {
                c.add(Calendar.DATE, 1) // number of days to add
            }
            dt = sdf.format(c.time) // dt is now the new date
            return "$dt$endTime:00.000+00:00"
        }
    }

    fun preview(view: View)
    {
        //todo: check validation...
        //todo: this is exactly like addWork - at the end instead of helper.AddWork(work) we show it
        var arrDate = tvDate.text.toString().split('/')
        var arrStrtingTime = tvStartTime.text.toString().split(':')
        var arrEndingTime = tvEndTime.text.toString().split(':')
        var date = com.example.haltura.Sql.Items.Date(Integer.parseInt(arrDate[0].split(' ')[1]), Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[2]))
        var staringTime = com.example.haltura.Sql.Items.Time(Integer.parseInt(arrStrtingTime[1].split(' ')[1]),Integer.parseInt(arrStrtingTime[2]))
        var endingTime = com.example.haltura.Sql.Items.Time(Integer.parseInt(arrEndingTime[1].split(' ')[1]),Integer.parseInt(arrEndingTime[2]))
        var work = Work(
            ivAddItemImage.drawable.toBitmap(),
            etCompany.text.toString(),
            etTask.text.toString(),
            Integer.parseInt(etSalary.text.toString()),
            Integer.parseInt(etNumberOfWorkers.text.toString()),
            etAddress.text.toString(),
            etInfo.text.toString(),
            date,
            staringTime,
            endingTime
        )
        //show work as work_item_list
    }

    fun SetImage(view: View)
    {
        val imagePopup: View = layoutInflater.inflate(R.layout.camera_or_gallery_popup, null)

        val popup = PopupWindow(
            imagePopup,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val camera = imagePopup.findViewById(R.id.camera) as ImageView
        val gallery = imagePopup.findViewById(R.id.gallery) as ImageView


        camera.setOnClickListener {
            setCameraImage()
            popup.dismiss()
            removeBackground(true)
        }

        gallery.setOnClickListener {
            setGalleryImage()
            popup.dismiss()
            removeBackground(true)
        }
        removeBackground(false)
        popup.showAtLocation(_layout, Gravity.CENTER, 0, 0) //popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }

    private fun removeBackground(show: Boolean) {
        if (show) {
            _layout.visibility = View.VISIBLE
            //_layout.setBackgroundDrawable(background)

        } else {
            _layout.visibility = View.GONE
            //_layout.setBackgroundColor(Color.GRAY)
        }
    }

    fun setCameraImage()
    {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }

    fun setGalleryImage()
    {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) {
            if (data != null) {
                bm = data.extras!!["data"] as Bitmap?
                bm.toString()
            }
            ivAddItemImage.setImageBitmap(bm)
        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            //todo:check if it is work
            if (data != null) {
                val uri = data.getData();
                val bm =MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
                ivAddItemImage.setImageBitmap(bm)//sendImageMessage(imageBitMap)
            }
            //todo: toast err
        }
    }

    companion object
    {
        private val REQ_CAMERA = 1
        private  val PICK_IMAGE = 2
    }
}