package com.example.haltura.Sql

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MessageOpenHelper {
    private var auth =  FirebaseAuth.getInstance()
    private var reference = FirebaseDatabase.getInstance().getReference().child("Messages")
    //private lateinit var reference : DatabaseReference
    private lateinit var activity: Activity

    constructor(activity: Activity)
    {
        this.activity = activity
    }
}