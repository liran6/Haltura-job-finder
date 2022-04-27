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
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.haltura.R
import com.example.haltura.Sql.BusinessOpenHelper
import com.example.haltura.Sql.Items.Work
import com.example.haltura.Sql.UserOpenHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.widget.AutocompleteSupportFragment

import java.io.IOException
import java.sql.Date
import java.sql.Time

//iv_addItem,Imageed_Company,ed_Task,dp_Date,tp_StartTime,tp_EndTime,et_Salary,et_NumberOfWorkers,et_Address,btn_ShowLocation,map,et_Info
class AddWorkActivity : AppCompatActivity(), OnMapReadyCallback {
    private val REQ_CAMERA = 1
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

    private lateinit var tvDate :TextView
    private lateinit var tvStartTime:TextView// : tv_StartTime
    private lateinit var tvEndTime:TextView// : tv_EndTime
    private lateinit var mDateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var mTimeSetListener: TimePickerDialog.OnTimeSetListener
    var bm: Bitmap? = null
    private var isStartTime = true

    var helper = BusinessOpenHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        edAddress = findViewById<View>(R.id.et_Address) as EditText
        tvDate = findViewById<View>(R.id.tv_date) as TextView
        tvStartTime = findViewById<View>(R.id.tv_StartTime) as TextView
        tvEndTime = findViewById<View>(R.id.tv_EndTime) as TextView

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

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

    fun addWork(view: View)
    {
        //todo: check validation...
        var arrDate = tvDate.text.toString().split('/')
        var arrStrtingTime = tvStartTime.text.toString().split(':')
        var arrEndingTime = tvEndTime.text.toString().split(':')

//        var date = Date(Integer.parseInt(arrDate[0].split(' ')[1]),
//            Integer.parseInt(arrDate[1]) - 1,
//            Integer.parseInt(arrDate[2]))
        var date = com.example.haltura.Sql.Items.Date(Integer.parseInt(arrDate[0].split(' ')[1]),
            Integer.parseInt(arrDate[1]),
            Integer.parseInt(arrDate[2]))

        var staringTime = com.example.haltura.Sql.Items.Time(Integer.parseInt(arrStrtingTime[1].split(' ')[1]),Integer.parseInt(arrStrtingTime[2]))
        var endingTime = com.example.haltura.Sql.Items.Time(Integer.parseInt(arrEndingTime[1].split(' ')[1]),Integer.parseInt(arrEndingTime[2]))
//        var staringTime = Time(Integer.parseInt(arrStrtingTime[1].split(' ')[1]),Integer.parseInt(arrStrtingTime[2]),0)
//        var endingTime = Time(Integer.parseInt(arrEndingTime[1].split(' ')[1]),Integer.parseInt(arrEndingTime[2]),0)
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
        //var srt = work.toString()
        helper.AddWork(work)
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
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
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
    }


}