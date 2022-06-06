package com.example.haltura.Menagment

import android.content.Context
import android.content.SharedPreferences
import com.example.haltura.Utils.Const

object Preferences {
    private const val NAME = Const.loginPreferences
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }







}