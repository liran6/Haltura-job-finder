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
import com.example.haltura.Utils.Const


object AppNotifications {

    fun toastManager(type:String,activity: Activity,message: String) {//,vararg actionMessage:String){
        when (type) {
            Const.Snack_Bar -> snackBar(activity, message)
            //Const.Action_Snack_Bar-> actionSnackBar(activity, message,actionMessage)
            Const.Success_Snack_Bar -> successSnackBar(activity, message)
            else -> {
                print("snack type is wrong")
            }
        }
    }


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

    fun actionSnackBar(activity: Activity, message: String, actionMessage: String) {
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

    fun successSnackBar(activity: Activity, message: String) {
        Snacky.builder()
            .setActivity(activity)
            .setText(message)
            .setDuration(Snacky.LENGTH_SHORT)
            .success()
            .show()
    }
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