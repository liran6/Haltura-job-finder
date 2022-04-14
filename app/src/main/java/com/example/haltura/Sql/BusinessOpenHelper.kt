package com.example.haltura.Sql

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.haltura.Sql.Items.Work
import com.example.haltura.activities.MainActivity
import com.facebook.internal.LockOnGetVariable
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class BusinessOpenHelper {
    private var auth = FirebaseAuth.getInstance()
    private var reference = FirebaseDatabase.getInstance().getReference().child("Works")
    private lateinit var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    fun AddWork(work: Work) {
        //todo: log this to server
        var fireBaseUser = auth.getCurrentUser() // may be Business
        if (fireBaseUser != null) {
            var reference = this.reference
            var activity = this.activity
            reference.child(fireBaseUser.getUid()).child(work.getId().toString()).setValue(work).addOnCompleteListener(
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "The work was added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        AddWorkUpdateUi()
                    } else {
                        Toast.makeText(
                            activity,
                            "Work could not be created",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ).addOnFailureListener(
                OnFailureListener
                {
                        exception -> exception.printStackTrace()
                }
            )
        }
    }
    //todo: add security config to firebase

    fun AddWorkUpdateUi() {
        // todo: check if needs to be here or should be after the calling in login
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }
}
