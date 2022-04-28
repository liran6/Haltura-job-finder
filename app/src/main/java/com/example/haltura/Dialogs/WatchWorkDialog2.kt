package com.example.haltura.Dialogs

//class WatchWorkDialog {
//}
import android.R
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.haltura.Sql.Items.Work
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.widget.AutocompleteSupportFragment


class WatchWorkDialog2 : Dialog {

    //private lateinit var context: Context
    private lateinit var work: Work
    //
    private lateinit var mMap: GoogleMap
    //private lateinit var calendar: Calendar
    private lateinit var AddItemImage: ImageView
    private lateinit var TaskAndCompany : TextView
    private lateinit var Salary: TextView
    //private lateinit var etNumberOfWorkers: EditText
    private lateinit var Address: TextView
    private lateinit var Info: TextView
    private lateinit var DateAndTime: TextView
    private lateinit var registerToWork: Button

    //private lateinit var binding: ActivityMapsBinding


    constructor(context: Context, work: Work):super(context)
    {
        this.work = work
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.watch_work_dialog)

        AddItemImage = findViewById<View>(com.example.haltura.R.id.item) as ImageView
        TaskAndCompany = findViewById<View>(com.example.haltura.R.id.et_Task) as TextView







        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}