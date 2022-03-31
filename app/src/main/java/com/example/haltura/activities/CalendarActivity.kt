package com.example.haltura.activities

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.haltura.R


class CalendarActivity : AppCompatActivity() {
    // Define the variable of CalendarView type
    // and TextView type;

    private lateinit var calendar: CalendarView
    private lateinit var date_view: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // By ID we can use each component
        // which id is assign in xml file
        // use findViewById() to get the
        // CalendarView and TextView
        calendar = findViewById<View>(R.id.calendar) as CalendarView
        date_view = findViewById<View>(R.id.date_view) as TextView

        // Add Listener in calendar
        calendar
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener have one method
                    // and in this method we will
                    // get the value of DAYS, MONTH, YEARS
                    // Store the value of date with
                    // format in String type Variable
                    // Add 1 in month because month
                    // index is start with 0
                    val Date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                    // set this date in TextView for Display
                    date_view!!.text = Date
                })
    }
}