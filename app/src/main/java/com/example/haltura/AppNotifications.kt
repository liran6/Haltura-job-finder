package com.example.haltura

import android.app.Activity
import de.mateware.snacky.Snacky
import android.content.Context

import com.google.android.material.snackbar.Snackbar
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


object AppNotifications {

    fun toastBar(activity: Activity,message: String){
        Toast.makeText(
            activity, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun snackBar(activity: Activity, message: String) {
        Snacky.builder()
            .setActivity(activity)
            .setText(message)
            .setDuration(Snacky.LENGTH_SHORT)
            .build()
            .show()
    }

    fun actionSnackBar(activity: Activity, message: String, actionMessage:String,) {
        Snacky.builder()
            .setActivity(activity)
            .setActionText(actionMessage)
            .setActionClickListener(View.OnClickListener() {
                fun onClick(v:View) {
                    var x = 1
                }
            })
            .setText(message)
            .setDuration(Snacky.LENGTH_LONG)
            .build()
            .show()
    }

//    fun showSnackBar(activity: Activity, message: String) {
//        Snacky.builder()
//            .setActivity(activity)
//            .setText(message)
//            .setDuration(Snacky.LENGTH_SHORT)
//            .build()
//            .show()
//    }
//
//    fun showSnackBar(activity: Activity, message: String) {
//        Snacky.builder()
//            .setActivity(activity)
//            .setText(message)
//            .setDuration(Snacky.LENGTH_SHORT)
//            .build()
//            .show()
//    }
//
//    fun showSnackBar(activity: Activity, message: String) {
//        Snacky.builder()
//            .setActivity(activity)
//            .setText(message)
//            .setDuration(Snacky.LENGTH_SHORT)
//            .build()
//            .show()
//    }
//
//    fun showSnackBar(activity: Activity, message: String) {
//        Snacky.builder()
//            .setActivity(activity)
//            .setText(message)
//            .setDuration(Snacky.LENGTH_SHORT)
//            .build()
//            .show()
//    }
//
//    fun showSnackBar(activity: Activity, message: String) {
//        Snacky.builder()
//            .setActivity(activity)
//            .setText(message)
//            .setDuration(Snacky.LENGTH_SHORT)
//            .build()
//            .show()
//    }
}