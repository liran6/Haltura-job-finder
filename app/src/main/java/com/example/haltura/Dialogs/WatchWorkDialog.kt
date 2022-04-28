package com.example.haltura.Dialogs

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.haltura.R
import com.example.haltura.Sql.Items.Work
import com.google.android.gms.maps.GoogleMap

class WatchWorkDialog : DialogFragment() {

    //private lateinit var context: Context
    private lateinit var work: Work
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

    constructor(work: Work)
    {
        this.work = work
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.watch_work_dialog, container, false)
        Image = rootView.findViewById<View>(R.id.itemImage) as ImageView
        TaskAndCompany = rootView.findViewById<View>(R.id.itemTaskAndCompany) as TextView
        Salary = rootView.findViewById<View>(R.id.itemSalary) as TextView
        Location = rootView.findViewById<View>(R.id.itemLocation) as TextView
        Info = rootView.findViewById<View>(R.id.itemInfo) as TextView
        DateAndTime = rootView.findViewById<View>(R.id.itemDateAndTime) as TextView
        registerToWork = rootView.findViewById<View>(R.id.RegisterToWork) as Button
        //todo on click
        //
        TaskAndCompany.text = work.getTask() + "(" + work.getCompany()+ ")"
        Salary.text = "Salary: " + work.getSalary()
        Location.text = work.getAddress()
        Info.text = work.getInfo()
        DateAndTime.text = work.getDate().toString() + " FROM " + work.getEndTime().toString() +" TO " + work.getStartTime().toString()
        var bm = Base64.decode(work.getImage(), Base64.DEFAULT)
        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
        Image.setImageBitmap(data)

        //todo add map
        registerToWork.setOnClickListener()
        {
            //
        }
        return rootView
    }


}